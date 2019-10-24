package com.zzj.learn.controller;

import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.domain.User;
import com.zzj.learn.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/query")
    public JSONResult query() {
        //查询所有
        List<User> users = mapper.selectList(null);

        users.forEach(System.out::println);
        System.out.println("--------------------");
        //条件查询 根据指定主键查询记录
        User user = mapper.selectById(1);
        System.out.println(user);
        return JSONResult.ok(users);
    }
    @GetMapping("/add")
    public void add () {
//        User user = new User("1","Jack",25,"Jack123@163.com");

        //返回影响条数
//        int insert = mapper.insert(user);
        System.out.println(1+"条数据受到影响");
    }
    @GetMapping("/update")
    public JSONResult update() {
//        User user = new User("1","22222","123456","Jimmy",19,"Jimmyadasdasdasda" +
//                "sdsadasdsadsadsadsdasdsadafsdsaf士大夫金坷");
//        //根据主键对记录进行更改 返回影响条数
//        int number = mapper.updateById(user);
//        stringRedisTemplate.opsForValue().set("a","test");
//        System.out.println(number+"条数据受到影响"+ stringRedisTemplate.opsForValue().get("a"));


        return JSONResult.ok();
    }


}
