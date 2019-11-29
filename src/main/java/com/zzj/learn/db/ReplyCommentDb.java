package com.zzj.learn.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_reply_comment")
public class ReplyCommentDb extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private long commentId;

    /**
     * 回复评论的UserId
     */
    @Column(updatable = false, nullable = false)
    private long replyUserId;
    /**
     * 回复评论的昵称
     */
    @Column()
    private String replyNickName;
    /**
     * 回复评论的UserId
     */
    @Column()
    private String replyFaceImage;
    /**
     * 被回复的用户id
     */
    @Column(updatable = false, nullable = false)
    private long commentUserId;

    /**
     *  被回复评论昵称
     */
    @Column()
    private String nickName;
    /**
     *  被回复评论人头像
     */
    @Column()
    private String faceImage;
    /**
     * 评论的内容
     */
    @Column(updatable = false, nullable = false)
    private String commentContent;

}
