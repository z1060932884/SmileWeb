package com.zzj.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("s_chat_msg")
public class ChatMsgModel {

    @TableId(value = "id")
    private String id;
    private long sendUserId;
    private long acceptUserId;
    private Integer signFlag;
    private Date createTime;
    private Integer type;
    private Integer itemType;
    private Integer chatType;
    private String msg;
}
