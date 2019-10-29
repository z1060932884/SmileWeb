package com.zzj.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("s_publish")
public class PublishModel {

    @TableId(value = "id",type = IdType.AUTO)
    private long id;
    private long userId;
    private String content;
    private String pictureUrlList;
    private String location;
    private Date createAt;
    private Date updateAt;
}
