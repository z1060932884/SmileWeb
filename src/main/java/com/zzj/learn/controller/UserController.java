package com.zzj.learn.controller;

import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper mapper;

    @GetMapping("/query")
    public void query() {
        //查询所有
        List<User> users = mapper.selectList(null);

        users.forEach(System.out::println);
        System.out.println("--------------------");
        //条件查询 根据指定主键查询记录
        User user = mapper.selectById(1);
        System.out.println(user);
    }
    @GetMapping("/add")
    public void add () {
        User user = new User("1","Jack",25,"Jack123@163.com");

        //返回影响条数
        int insert = mapper.insert(user);
        System.out.println(insert+"条数据受到影响");
    }
    @GetMapping("/update")
    public void update() {
        User user = new User("1","Jimmy",19,"Jimmy123@163.com");
        //根据主键对记录进行更改 返回影响条数
        int number = mapper.updateById(user);
        System.out.println(number+"条数据受到影响");
    }


}
