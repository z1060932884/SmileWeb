package com.zzj.learn.db;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "s_chat_msg")
public class ChatMsgDB {
    @Id
    @PrimaryKeyJoinColumn
    @Column(updatable = false, nullable = false)
    private long id;
    @Column(updatable = false, nullable = false)
    private long sendUserId;
    @Column(updatable = false, nullable = false)
    private long acceptUserId;
    @Column()
    private Integer signFlag;
    @Column(updatable = false, nullable = false)
    private Date createTime;
    @Column()
    private Integer type;
    @Column()
    private Integer itemType;
    @Column()
    private Integer chatType;
    @Column()
    private String msg;


}