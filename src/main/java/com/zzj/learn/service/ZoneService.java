package com.zzj.learn.service;

import com.zzj.learn.domain.PublishModel;
import com.zzj.learn.vo.CommentCard;
import com.zzj.learn.vo.PublishCard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZoneService {

    PublishModel publish(long userId,String imageUrlList
            , String content, String location);

    List<PublishCard> getPublishList();

    /**
     * 获取动态详情
     * @return
     */
    PublishCard getDynamicById(long id);

    /**
     * 评论
     * @param commentCard
     * @return
     */
    CommentCard sendComment(CommentCard commentCard);

    /**
     * 回复评论
     * @param commentCard
     * @return
     */
    CommentCard sendReplyComment(CommentCard commentCard);
}
