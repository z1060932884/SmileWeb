package com.zzj.learn.service;

import com.zzj.learn.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    User login(User user);

    User findUserByUname(User user);

    /**
     * 根据手机号获取用户
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);

    User checkPassword(User user);

    User register(String phone, String password);
}
