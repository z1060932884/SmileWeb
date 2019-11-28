package com.zzj.learn.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishCard {

    private long id;
    private long userId;
    private String content;
    private String pictureUrlList;
    private String location;
    private Date createAt;
    private Date updateAt;
    private String nickName;
    private String faceImage;
    private int gender;
    private List<CommentCard> commentList;
}
