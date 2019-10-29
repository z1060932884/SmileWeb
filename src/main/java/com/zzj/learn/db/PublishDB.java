package com.zzj.learn.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_publish")
public class PublishDB extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private String userId;
    @Column()
    private String content;
    @Column()
    private String pictureUrlList;
    @Column()
    private String location;
}
