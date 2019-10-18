package com.zzj.learn.db;


import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_user")
public class UserDB extends BaseEntity{

    @Expose
    @Column
    private String name;
    @Expose
    @Column
    private String age;
    @Expose
    @Column
    private String email;



}
