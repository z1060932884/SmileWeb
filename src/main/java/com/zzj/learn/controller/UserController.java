package com.zzj.learn.controller;

import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.domain.User;
import com.zzj.learn.utils.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private FastDFSClient fastDFSClient;

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

    @LoginRequired
    @PostMapping("/updateUserInfo")
    public JSONResult updateUserInfo(@CurrentUser User currentUser,  @RequestBody User users) throws Exception{

        if(Strings.isBlank(users.getFaceImage())
                ||Strings.isBlank(users.getNickname())){

            return JSONResult.errorMsg("信息不能为空");
        }


        currentUser.setFaceImage(users.getFaceImage());
        currentUser.setNickname(users.getNickname());
        currentUser.setDescription(users.getDescription());
        currentUser.setGender(users.getGender());
        int index = mapper.updateById(currentUser);
        if(index == 0){
            return JSONResult.errorMsg("服务器异常");
        }
        return JSONResult.ok(currentUser);
    }
    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload/file")
    @ResponseBody
    public JSONResult uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        String url = null;
        try {
            url = fastDFSClient.uploadFile(file);
            System.out.println("上传文件的路径-->"+url);
           return JSONResult.ok(url);
        } catch (Exception e) {
            // TODO: handle exception
            return JSONResult.errorMsg(e.getMessage());
        }
    }


    /**
     * 上传图片接口
     * @param base64Image
     * @return
     * @throws Exception
     */
    @LoginRequired
    @PostMapping("/uploadImage")
    private JSONResult uploadImage(@CurrentUser User user,String base64Image) throws Exception{
        //获取前端传过来的base64字符串，然后转换成文件对象再上传
        String userFacePath = "D:\\"+ System.currentTimeMillis()+".png";
//        String userFacePath = "/fastdfs/tmp/"+ usersBo.getUserId()+"userFace64.png";

        FileUtils.base64ToFile(userFacePath,base64Image);
        //上传文件到fastdfs
        MultipartFile file = FileUtils.fileToMultipart(userFacePath);

        String url = fastDFSClient.uploadBase64(file);
        System.out.println(url);

        //获取缩略图的url
        String thump = "_80x80.";
        String arr[]  = url.split("\\.");
        String thumpImageUrl = arr[0] + thump+arr[1];
        return JSONResult.ok(url);
    }

}
