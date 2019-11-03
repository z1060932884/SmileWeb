package com.zzj.learn.netty;


import com.zzj.learn.domain.User;
import com.zzj.learn.enums.MsgActionEnum;
import com.zzj.learn.service.UserService;
import com.zzj.learn.utils.JsonUtils;
import com.zzj.learn.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 记录管理所有客户端的channel
     */
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame)
            throws Exception {

        //获取客户端出来的消息
        String message= textWebSocketFrame.text();
        System.out.println("接收到消息--->"+message);
        //当前的channel
        Channel currentChannel = channelHandlerContext.channel();
        //1.获取客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(message,DataContent.class);
        //2.判断消息类型，根据不同的类型处理不同的业务
        Integer action = dataContent.getAction();

        if(action == MsgActionEnum.CONNECT.type){
            //  2.1 当websocket 第一次open的时候，初始化channel,把channel和userId关联起来
            UserChannelRel.put(dataContent.getChatMsg().getSenderId(),currentChannel);
        }else if(action == MsgActionEnum.CHAT.type){
            //  2.2 聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态（未签收）
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgText = chatMsg.getMsg();
            long receiverId = chatMsg.getReceiverId();
            long senderId = chatMsg.getSenderId();
            chatMsg.setTime(chatMsg.getTime());
            //标记消息已发送成功
            chatMsg.setSend(true);
            chatMsg.setRead(false);
            //单聊
            UserService userService = (UserService) SpringUtil.getBean("userServiceImp");
            userService.saveMsg(chatMsg);
            User senderUser = userService.queryUserInfoByUserId(senderId);
            chatMsg.setFaceImage(senderUser.getFaceImage());
            chatMsg.setUserName(senderUser.getNickname());
            //发送消息
            //从全局用户Channel关系中获取接收方channel
            Channel receiverChannel =  UserChannelRel.get(receiverId);
            //发送给接收者的消息
            String sendMessage = JsonUtils.objectToJson(dataContent);

            //通知发送者已收到消息并保存数据库
            DataContent notifyData = new DataContent();
            // 6 代表通知发送者已后台收到消息并保存数据库
            notifyData.setAction(MsgActionEnum.MSG_Received.type);

            notifyData.setExtand(chatMsg.getMsgId());
            Channel senderChannel = UserChannelRel.get(senderId);
            if(senderChannel!=null){
                senderChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(notifyData)));
            }
            if(receiverChannel == null){
                // TODO channel为空  代表用户离线
            }else {

                receiverChannel.writeAndFlush(new TextWebSocketFrame(sendMessage));
//                //当receiverChannel 不为空，从userGroup中查找channel
//                Channel findChannel = users.find(receiverChannel.id());
//                if(findChannel!=null){
//                    //用户在线
//                    findChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(chatMsg)));
//                }else {
//                    // TODO 用户离线
//                }
            }

        }else if(action == MsgActionEnum.SIGNED.type){
            //  2.3 签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态（已签收）
            UserService userService = (UserService) SpringUtil.getBean("userServiceImp");
            String msgIdsStr = dataContent.getExtand();
            String[] msgIds = msgIdsStr.split(",");
            List<String> msgIdList = new ArrayList<>();
            for(String mid : msgIds){
                if(StringUtils.isNotBlank(mid)){
                    msgIdList.add(mid);
                }
            }
            if(msgIdList!=null && !msgIdList.isEmpty() && msgIdList.size()>0){
                //批量签收

                userService.updateMsgSigned(msgIdList);
            }

        }else if(action == MsgActionEnum.PULL_FRIEND.type){

        }else if(action == MsgActionEnum.KEEPALIVE.type){
            //  2.4 心跳类型的消息

        }else if(action == MsgActionEnum.GROUP_MSG.type){
            //群组消息
            ChatMsg chatMsg = dataContent.getChatMsg();
            long senderId = chatMsg.getSenderId();
            chatMsg.setTime(chatMsg.getTime());
            //标记消息已发送成功
            chatMsg.setSend(true);
            chatMsg.setRead(false);
            UserService userService = (UserService) SpringUtil.getBean("userServiceImp");
            userService.saveMsg(chatMsg);

            //通知发送者已收到消息并保存数据库
            DataContent notifyData = new DataContent();
            // 6 代表通知发送者已后台收到消息并保存数据库
            notifyData.setAction(MsgActionEnum.MSG_Received.type);

            notifyData.setExtand(chatMsg.getMsgId());
            UserChannelRel.get(senderId).writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(notifyData)));

            //1、获取群的id
            long groupId = chatMsg.getReceiverId();
//            PushFactory.pushGroupMessage(senderId,groupId,chatMsg);

        }





    }


    /**
     * 当客户端连接服务端后，获取客户端的channel，并且放到channelGroup中去进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        System.out.println("handlerRemoved--->"+ctx.channel().id());
        //当触发handlerRemoved，channelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
     
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("exceptionCaught--->"+ctx.channel().id());

        users.remove(ctx.channel());
    }
}
