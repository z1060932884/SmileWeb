package com.zzj.learn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zzj.learn.domain.PublishModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ZonePublishMapper extends BaseMapper<PublishModel> {

    @Select("Select * from s_publish publish,s_attention attention where attention.followed_user_id = publish.user_id " +
            "AND attention.follow_user_id = ${userId} order By publish.create_at desc")
    IPage<PublishModel>  attentionDynamicList(IPage<PublishModel> page, @Param(value = "userId")long userId);
}
