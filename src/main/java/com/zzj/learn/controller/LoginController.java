package com.zzj.learn.controller;

import com.zzj.learn.domain.User;
import com.zzj.learn.service.LoginService;
import com.zzj.learn.utils.CurrentUser;
import com.zzj.learn.utils.JSONResult;
import com.zzj.learn.utils.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("account")
public class LoginController {

    @Autowired
    LoginService loginService;
    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONResult userLogin(@RequestBody User user) {

        // 根据用户名查找用户方法
        User userByUname = loginService.findUserByPhone(user.getPhone());
        if (userByUname == null) {
            return JSONResult.errorMsg("用户不存在");
        }else {
            User result ;
            // 根据用户名和密码查找用户方法
            result = loginService.checkPassword(user);
            if (result == null) {

                return JSONResult.errorMsg("密码错误");
            } else {
                // 登录方法
                User login = loginService.login(result);

                return JSONResult.ok(login);
            }
        }
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONResult register(@FormParam("phone")String phone, @FormParam("password")String password){
        //首先检查用户是否存在
        User user = loginService.findUserByPhone(phone);
        if(user!=null){
            return JSONResult.errorMsg("用户已存在");
        }
        User user1 = loginService.register(phone,password);
        if(user1 == null){
            return JSONResult.errorMsg("注册失败");
        }
        return JSONResult.ok(loginService.login(user1));
    }
    /**
     * Token测试接口
  * @param user
  * @return
          */
    @LoginRequired
    @GetMapping("/getUserInfo")
    public JSONResult getUserInfo(@CurrentUser User user){
        return JSONResult.ok(user);
    }
}
