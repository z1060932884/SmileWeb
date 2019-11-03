package com.zzj.learn.db;


import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_user")
public class UserDB extends BaseEntity{

    @Column(nullable = false)
    private String username;
    @Column
    private String age;
    @Column
    private String email;
    @Column
    private String token;
    @Column
    private String password;
    @Column(nullable = false)
    private String phone;
    @Column
    private String nickname;
    @Column
    private String faceImage;
    @Column
    private String description;
    @Column
    private int gender;

}
