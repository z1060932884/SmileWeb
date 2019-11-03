package com.zzj.learn.serviceimp;

import cn.hutool.core.date.DateUtil;
import com.zzj.learn.dao.ChatMsgMapper;
import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.domain.ChatMsgModel;
import com.zzj.learn.domain.User;
import com.zzj.learn.enums.MsgSignFlagEnum;
import com.zzj.learn.netty.ChatMsg;
import com.zzj.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private ChatMsgMapper msgMapper;

    @Override
    public User updateUserInfo(User updateUser) {
        return null;
    }

    @Override
    public String saveMsg(ChatMsg chatMsg) {
        ChatMsgModel msg = new ChatMsgModel();
//        String msgId = sid.nextShort();
        msg.setId(chatMsg.getMsgId());
        msg.setMsg(chatMsg.getMsg());
        msg.setAcceptUserId(chatMsg.getReceiverId());
        msg.setSendUserId(chatMsg.getSenderId());
        msg.setCreateTime(DateUtil.parse(chatMsg.getTime()));
        msg.setSignFlag(MsgSignFlagEnum.unsign.type);
        msg.setItemType(chatMsg.getItemType());
        msg.setType(chatMsg.getType());
        msg.setChatType(chatMsg.getChatType());
        msgMapper.insert(msg);
        return chatMsg.getMsgId();
    }

    @Override
    public User queryUserInfoByUserId(long userId) {
        return mapper.selectById(userId);
    }

    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        if (msgIdList != null && msgIdList.size() > 0) {

            for (String msgId : msgIdList) {
                ChatMsgModel chatMsgModel = msgMapper.selectById(msgId);
                chatMsgModel.setSignFlag(1);
                msgMapper.updateById(chatMsgModel);
            }
        }
    }
}

