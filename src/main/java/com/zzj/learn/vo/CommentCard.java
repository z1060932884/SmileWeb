package com.zzj.learn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCard implements Serializable {
    /**
     * 评论id
     */
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

    private long commentId;

    /**
     * 回复评论的UserId
     */
    private long replyUserId;

    /**
     *  被回复评论昵称
     */
    private String nickName;
    /**
     *  被回复评论人头像
     */
    private String faceImage;
    /**
     * 回复评论的昵称
     */
    private String replyNickName;
    /**
     * 回复评论的UserId
     */
    private String replyFaceImage;
}
