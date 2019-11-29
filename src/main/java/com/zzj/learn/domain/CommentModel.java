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
@TableName("s_comment")
public class CommentModel {

    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    private Date createAt;
    private Date updateAt;
    /**
     * 评论动态的id
     */
    private long dynamicId;
    /**
     * 评论的用户id
     */
    private long commentUserId;

    /**
     * 评论的内容
     */
    private String commentContent;

    /**
     *  被回复评论昵称
     */
    private String nickName;
    /**
     *  被回复评论人头像
     */
    private String faceImage;
}
