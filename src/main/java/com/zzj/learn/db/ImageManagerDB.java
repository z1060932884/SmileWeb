package com.zzj.learn.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_image_manager")
public class ImageManagerDB extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private String url;
    @Column(updatable = false, nullable = false)
    private String userId;

    private String location;


}
