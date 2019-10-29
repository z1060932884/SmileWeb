package com.zzj.learn.serviceimp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzj.learn.constant.UserConstants;
import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.domain.User;
import com.zzj.learn.service.LoginService;
import com.zzj.learn.utils.TokenUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    Sid sid;

    @Override
    public User login(User user) {
        //生成Token
        String accessToken = TokenUtil.createJwtToken(user.getUsername());
        user.setToken(accessToken);
        //登录成功,将Token最为键，用户信息作为值存入Redis
        stringRedisTemplate.opsForValue().set(UserConstants.REDIS_USER + accessToken, JSON.toJSONString(user), UserConstants.REDIS_USER_TIME, TimeUnit.MILLISECONDS);
//        mapper.insert(user);
        System.out.println("存入的信息---》" + stringRedisTemplate.opsForValue().get(UserConstants.REDIS_USER + accessToken));
        return user;
    }

    @Override
    public User findUserByUname(User user) {
        //创建条件构造器
        QueryWrapper<User> queryWrapper01 = new QueryWrapper<>();
        queryWrapper01.likeRight("username", user.getUsername()).select();
        return mapper.selectOne(queryWrapper01);
    }

    @Override
    public User findUserByPhone(String phone) {
        //创建条件构造器
        QueryWrapper<User> queryWrapper01 = new QueryWrapper<>();
        queryWrapper01.likeRight("phone", phone).select();
        return mapper.selectOne(queryWrapper01);
    }

    @Override
    public User checkPassword(User user) {
        User user1 = findUserByPhone(user.getPhone());
        if (user1 != null && user != null) {
            if (user1.getPassword().equals(user.getPassword())) {
                return user1;
            }
        }
        return null;
    }

    @Override
    public User register(String phone, String password) {
        User user = new User();
        user.setUsername(uuid());
        user.setPhone(phone);
        user.setPassword(password);
        int index = mapper.insert(user);
        if (index > 0) {
            return user;
        }
        return null;
    }

    /**
     *      * 生成随机账号
     *      * @return
     *      
     */
    public static String uuid() {
        int machineId = 1; //最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        return machineId + String.format("%011d", hashCodeV);
    }
}
