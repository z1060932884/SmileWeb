package com.zzj.learn.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 关注
 */
@Entity
@Table(name = "s_attention")
public class AttentionDB extends BaseEntity {
    /**
     * 关注人id
     */
    @Column(updatable = false, nullable = false)
    private long followUserId;
    /**
     * 被关注人id
     */
    @Column(updatable = false, nullable = false)
    private long followedUserId;


}
