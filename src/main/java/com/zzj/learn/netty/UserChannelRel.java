package com.zzj.learn.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @Description： 用户id 和 Channer的关联关系处理
 *
 */
public class UserChannelRel {


    private static HashMap<Long, Channel> manager = new HashMap<>();


    public static void put(long senderId,Channel channel){
        manager.put(senderId,channel);

    }

    public static Channel get(long senderId){

        return manager.get(senderId);
    }

}
