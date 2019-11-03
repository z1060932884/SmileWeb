package com.zzj.learn.service;

import com.zzj.learn.domain.User;
import com.zzj.learn.netty.ChatMsg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User updateUserInfo(User updateUser);

    /**
     * 保存消息
     * @param chatMsg
     * @return 消息id
     */
    public String saveMsg (ChatMsg chatMsg);


    User queryUserInfoByUserId(long senderId);

    /**
     * 更新消息的签收状态
     * @param msgIdList
     */
    void updateMsgSigned(List<String> msgIdList);
}
