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
     * 被回复的用户id
     */
    @Column(updatable = false, nullable = false)
    private long commentUserId;

    /**
     * 评论的内容
     */
    @Column(updatable = false, nullable = false)
    private String commentContent;

}
