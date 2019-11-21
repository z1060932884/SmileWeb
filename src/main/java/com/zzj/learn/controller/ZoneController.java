package com.zzj.learn.controller;

import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.domain.PublishModel;
import com.zzj.learn.domain.User;
import com.zzj.learn.service.ZoneService;
import com.zzj.learn.utils.*;
import com.zzj.learn.vo.PublishCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("zone")
public class ZoneController {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    ZoneService zoneService;
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

    /**
     * 发布动态 信息
     * @param user
     * @param imageUrlList
     * @param content
     * @param location
     * @return
     */
    @LoginRequired
    @PostMapping("/publish")
    public JSONResult publish(@CurrentUser User user,String imageUrlList
            , String content, String location){
        if(imageUrlList.isEmpty()&&content.isEmpty()){
            return JSONResult.errorMsg("内容不能为空");
        }

        if(user == null){
            return JSONResult.errorMsg("服务器异常");
        }
        PublishModel publishModel = zoneService.publish(user.getId(),imageUrlList,content,location);
        if(publishModel==null){
            return JSONResult.errorMsg("服务器异常");
        }
        return JSONResult.ok(publishModel);
    }

    /**
     * 获取动态列表
     * @return
     */
    @LoginRequired
    @GetMapping("/publishList")
    public JSONResult getPublishList(){

        List<PublishCard> publishModels =  zoneService.getPublishList();
        if(publishModels == null){
            return JSONResult.errorMsg("服务器异常");
        }
        return JSONResult.ok(publishModels);
    }

    /**
     * 获取动态详情
     * @return
     */
    @LoginRequired
    @GetMapping("/getDynamic")
    public JSONResult getDynamicById(long dynamicId){

        PublishCard publishModel =  zoneService.getDynamicById(dynamicId);
        if(publishModel == null){
            return JSONResult.errorMsg("服务器异常");
        }
        return JSONResult.ok(publishModel);
    }

    /**
     * replyTime : 2017-06-24 17:23:40
     * replayUserId : b2f1f00a615941588e85f927dc8620a0
     * replayUserName : heyanmin
     * id : 8ab8c3cb0e2248f08aded1ed97959014
     * replyContent : 你好
     * userName : 砳砳007
     * userId : 70c09944c0544a8c98ab5e6e94e3d452
     */
}
