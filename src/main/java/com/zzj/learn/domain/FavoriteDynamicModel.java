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
@TableName("s_favorite_dynamic")
public class FavoriteDynamicModel {

    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    private Date createAt;
    private Date updateAt;

    /**
     * 点赞人的id
     */
    private long userId;
    /**
     * 点赞的动态id
     */
    private long dynamicId;
}
