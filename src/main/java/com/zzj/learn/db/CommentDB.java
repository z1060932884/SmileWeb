package com.zzj.learn.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_comment")
public class CommentDB extends BaseEntity {

    /**
     * 评论动态的id
     */
    @Column(updatable = false, nullable = false)
    private long dynamicId;
    /**
     * 评论的用户id
     */
    @Column(updatable = false, nullable = false)
    private long commentUserId;

    /**
     * 评论的内容
     */
    @Column(updatable = false, nullable = false)
    private String commentContent;
}
