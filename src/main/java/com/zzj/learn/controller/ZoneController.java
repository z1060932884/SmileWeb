package com.zzj.learn.controller;

import com.zzj.learn.dao.ImageManagerMapper;
import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.domain.PublishModel;
import com.zzj.learn.domain.User;
import com.zzj.learn.service.ZoneService;
import com.zzj.learn.utils.*;
import com.zzj.learn.vo.CommentCard;
import com.zzj.learn.vo.PublishCard;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
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
    public JSONResult getDynamicById(@CurrentUser User user, long dynamicId){

        PublishCard publishModel =  zoneService.getDynamicById(user.getId(),dynamicId);
        if(publishModel == null){
            return JSONResult.errorMsg("服务器异常");
        }
        return JSONResult.ok(publishModel);
    }
    /**
     * 获取动态列表
     * @return
     */
    @LoginRequired
    @GetMapping("/getDynamicList")
    public JSONResult getDynamicListById(@CurrentUser User user, long dynamicId,int page,int pagesize){

        List<PublishCard> publishModel =  zoneService.getDynamicListById(user.getId(),dynamicId,page,pagesize);
        if(publishModel == null){
            return JSONResult.errorMsg("服务器异常");
        }
        return JSONResult.ok(publishModel);
    }
    /**
     * 获取关注的动态列表
     * @return
     */
    @LoginRequired
    @GetMapping("/attentionDynamicList")
    public JSONResult attentionDynamicList(@CurrentUser User user,int page,int pagesize){
        List<PublishCard> cardList = zoneService.attentionDynamicList(user.getId(),page,pagesize);
        if(cardList == null){
            return JSONResult.errorMsg("服务器异常");
        }
        return JSONResult.ok(cardList);
    }

    /**
     * 发送评论
     * @param commentCard
     * @return
     */
    @LoginRequired
    @PostMapping("/sendComment")
    public JSONResult sendComment(@RequestBody CommentCard commentCard){
        if(StringUtils.isBlank(commentCard.getCommentContent())){
            return JSONResult.errorMsg("评论内容不能为空");
        }
        CommentCard commentCard1 = null;
       if(commentCard.getReplyUserId()!=0){
          commentCard1 = zoneService.sendReplyComment(commentCard);
       }else {
          commentCard1 = zoneService.sendComment(commentCard);
       }
        if(commentCard1 == null){
            return JSONResult.errorMsg("评论发送失败");
        }
        return JSONResult.ok();
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

    /**
     * 关注人
     *attentionUserId 关注人的id
     * @return
     */
    @LoginRequired
    @PostMapping("/attention")
    public JSONResult attention(@CurrentUser User user,long attentionUserId){
        if(attentionUserId == 0){
            return JSONResult.errorMsg("关注人id不能为空");
        }
        int index = zoneService.attention(user.getId(),attentionUserId);
        if(index == 0){
            return JSONResult.errorMsg("执行失败");
        }
        return JSONResult.ok("");
    }
    /**
     * 点赞动态
     *
     * @return
     */
    @LoginRequired
    @PostMapping("/favoriteDynamic")
    public JSONResult favoriteDynamic(@CurrentUser User user,long favoriteDynamicId){
        if(favoriteDynamicId == 0){
            return JSONResult.errorMsg("动态id不能为空");
        }
        int index = zoneService.favoriteDynamic(user.getId(),favoriteDynamicId);
        if(index == 0){
            return JSONResult.errorMsg("执行失败");
        }
        return JSONResult.ok("");
    }

    /**
     * 获取相册
     *
     * @return
     */
    @LoginRequired
    @GetMapping("/getAlbum")
    public JSONResult getAlbum(@CurrentUser User user,long userId,int page,int pagesize){
        if(userId == 0){
            return JSONResult.errorMsg("用户不能为空");
        }
        return JSONResult.ok(zoneService.getAlbum(userId,page,pagesize));
    }


}
