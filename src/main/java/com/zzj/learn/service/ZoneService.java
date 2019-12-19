package com.zzj.learn.service;

import com.zzj.learn.domain.AttentionModel;
import com.zzj.learn.domain.FavoriteDynamicModel;
import com.zzj.learn.domain.ImageManagerModel;
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
    PublishCard getDynamicById(long userId,long id);

    List<PublishCard> getDynamicListById(long userId,long id,int page,int pagesize);

    /**
     * 关注的动态列表
     * @param userId
     * @param page
     * @param pagesize
     * @return
     */
    List<PublishCard> attentionDynamicList(long userId,int page,int pagesize);
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

    /**
     * 关注人
     * @param userId 当前用户
     * @param attentionUserId 关注的用户
     */
    int attention(long userId,long attentionUserId);

    /**
     * 根据id查询关注信息
     * @return
     */
    AttentionModel queryAttentionById(long userId,long attentionUserId);

    /**
     * 根据id查询点赞信息
     * @return
     */
    FavoriteDynamicModel queryFavoriteDynamicById(long userId, long favoriteDynamicId);
    /**
     * 点赞动态
     * @param id
     * @param favoriteDynamicId
     * @return
     */
    int favoriteDynamic(long id, long favoriteDynamicId);

    /**
     * 获取用户相册数据
     * @param userId
     * @return
     */
    List<ImageManagerModel> getAlbum(long userId,int page,int pagesize);
}
