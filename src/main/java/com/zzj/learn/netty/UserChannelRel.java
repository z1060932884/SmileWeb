package com.zzj.learn.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @Description： 用户id 和 Channer的关联关系处理
 *
 */
public class UserChannelRel {


    private static HashMap<String, Channel> manager = new HashMap<>();


    public static void put(String senderId,Channel channel){
        manager.put(senderId,channel);

    }

    public static Channel get(String senderId){

        return manager.get(senderId);
    }

}
