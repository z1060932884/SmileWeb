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
@TableName("s_attention")
public class AttentionModel {

    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    private Date createAt;
    private Date updateAt;

    /**
     * 关注人id
     */
    private long followUserId;
    /**
     * 被关注人id
     */
    private long followedUserId;

}
