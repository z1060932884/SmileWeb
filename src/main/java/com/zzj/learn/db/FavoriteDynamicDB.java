package com.zzj.learn.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 点赞的动态
 */
@Entity
@Table(name = "s_favorite_dynamic")
public class FavoriteDynamicDB extends BaseEntity{
    /**
     * 点赞人的id
     */
    @Column(updatable = false, nullable = false)
    private long userId;
    /**
     * 点赞的动态id
     */
    @Column(updatable = false, nullable = false)
    private long dynamicId;

}
