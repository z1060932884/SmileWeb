package com.zzj.learn.serviceimp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzj.learn.dao.*;
import com.zzj.learn.domain.*;
import com.zzj.learn.service.ZoneService;
import com.zzj.learn.utils.SpringUtil;
import com.zzj.learn.vo.CommentCard;
import com.zzj.learn.vo.PublishCard;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    ZonePublishMapper publishMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ReplyCommentMapper replyCommentMapper;
    @Autowired
    AttentionMapper attentionMapper;
    @Autowired
    FavoriteDynamicMapper favoriteDynamicMapper;
    @Override
    public PublishModel publish(long userId,String imageUrlList, String content, String location) {
        PublishModel publishModel = new PublishModel();
        publishModel.setUserId(userId);
        publishModel.setContent(content);
        publishModel.setPictureUrlList(imageUrlList);
        publishModel.setLocation(location);
        publishModel.setCreateAt(new Date());
        publishModel.setUpdateAt(new Date());
        int index = publishMapper.insert(publishModel);
        if(index != 0){
            return publishModel;
        }
      return null;
    }

    @Override
    public List<PublishCard> getPublishList() {
        List<PublishModel> publishModels = publishMapper.selectList(null);
        return publishModels.stream().map(new Function<PublishModel, PublishCard>() {
            @Override
            public PublishCard apply(PublishModel publishModel) {
                PublishCard publishCard = new PublishCard();
                BeanUtils.copyProperties(publishModel,publishCard);
               User user = userMapper.selectById(publishModel.getUserId());
               publishCard.setNickName(user.getNickname());
               publishCard.setFaceImage(user.getFaceImage());
               publishCard.setGender(user.getGender());
                return publishCard;
            }
        }).collect(Collectors.toList());

    }

    @Override
    public PublishCard getDynamicById(long userId,long id) {
        PublishModel publishModel = publishMapper.selectById(id);
        //查询评论
        QueryWrapper<CommentModel> queryWrapper01 = new QueryWrapper<>();
        queryWrapper01.eq("dynamic_id",id);
        Page<CommentModel> page = new Page<>(1,4);

        IPage<CommentModel> commentModelIPage = commentMapper.selectPage(page,queryWrapper01);
        List<CommentModel> commentModels = commentModelIPage.getRecords();
        List<CommentCard> commentCards = commentModels.stream().map(new Function<CommentModel, CommentCard>() {
            @Override
            public CommentCard apply(CommentModel commentModel) {
                CommentCard commentCard = new CommentCard();
                BeanUtils.copyProperties(commentModel,commentCard);
                return commentCard;
            }
        }).collect(Collectors.toList());
        //是否关注
        AttentionModel attentionModel = queryAttentionById(userId,publishModel.getUserId());
        boolean isAttention = false;
        if(attentionModel == null){
            isAttention = false;
        }else {
            isAttention = true;
        }
        //是否点赞
        FavoriteDynamicModel favoriteDynamicModel = queryFavoriteDynamicById(userId,id);
        boolean isFavoriteDynamic = false;
        if(favoriteDynamicModel == null){
            isFavoriteDynamic = false;
        }else {
            isFavoriteDynamic = true;
        }
        PublishCard publishCard = new PublishCard();
        BeanUtils.copyProperties(publishModel,publishCard);
        User user = userMapper.selectById(publishModel.getUserId());
        publishCard.setNickName(user.getNickname());
        publishCard.setFaceImage(user.getFaceImage());
        publishCard.setGender(user.getGender());
        publishCard.setCommentList(commentCards);
        publishCard.setAttention(isAttention);
        publishCard.setFavoriteDynamic(isFavoriteDynamic);
        return publishCard;
    }

    @Override
    public List<PublishCard> getDynamicListById(long userId,long id,int page,int pagesize) {
        QueryWrapper<PublishModel> publishModelQueryWrapper = new QueryWrapper<>();
        //查询等于大于id
        publishModelQueryWrapper.eq("id", id).or().gt("id", id);
        publishModelQueryWrapper.notLike("user_id", userId);
        Page<PublishModel> publishModelPage = new Page<>(page, pagesize);
        IPage<PublishModel> publishModelIPage = publishMapper.selectPage(publishModelPage, publishModelQueryWrapper);
        List<PublishModel> publishModels = publishModelIPage.getRecords();
        List < PublishCard > publishCardList = publishModels.stream().map(new Function<PublishModel, PublishCard>() {
            @Override
            public PublishCard apply(PublishModel publishModel) {
                //查询评论
                QueryWrapper<CommentModel> queryWrapper01 = new QueryWrapper<>();
                queryWrapper01.eq("dynamic_id", id);
                Page<CommentModel> commentModelPage = new Page<>(1, 4);

                IPage<CommentModel> commentModelIPage = commentMapper.selectPage(commentModelPage, queryWrapper01);
                List<CommentModel> commentModels = commentModelIPage.getRecords();
                List<CommentCard> commentCards = commentModels.stream().map(new Function<CommentModel, CommentCard>() {
                    @Override
                    public CommentCard apply(CommentModel commentModel) {
                        CommentCard commentCard = new CommentCard();
                        BeanUtils.copyProperties(commentModel, commentCard);
                        return commentCard;
                    }
                }).collect(Collectors.toList());
                //是否关注
                AttentionModel attentionModel = queryAttentionById(userId, publishModel.getUserId());
                boolean isAttention = false;
                if (attentionModel == null) {
                    isAttention = false;
                } else {
                    isAttention = true;
                }
                //是否点赞
                FavoriteDynamicModel favoriteDynamicModel = queryFavoriteDynamicById(userId, id);
                boolean isFavoriteDynamic = false;
                if (favoriteDynamicModel == null) {
                    isFavoriteDynamic = false;
                } else {
                    isFavoriteDynamic = true;
                }
                PublishCard publishCard = new PublishCard();
                BeanUtils.copyProperties(publishModel, publishCard);
                User user = userMapper.selectById(publishModel.getUserId());
                publishCard.setNickName(user.getNickname());
                publishCard.setFaceImage(user.getFaceImage());
                publishCard.setGender(user.getGender());
                publishCard.setCommentList(commentCards);
                publishCard.setAttention(isAttention);
                publishCard.setFavoriteDynamic(isFavoriteDynamic);
                return publishCard;
            }
        }).collect(Collectors.toList());

        return publishCardList;
    }

    @Override
    public List<PublishCard> attentionDynamicList(long userId, int page, int pagesize) {
        QueryWrapper<PublishModel> publishModelQueryWrapper = new QueryWrapper<>();
       /* //是否关注
        AttentionModel attentionModel = queryAttentionById(userId, publishModel.getUserId());
        boolean isAttention = false;
        if (attentionModel == null) {
            isAttention = false;
        } else {
            isAttention = true;
        }
        //查询等于大于id
        publishModelQueryWrapper.eq("id", id).or().gt("id", id);*/
        Page<PublishModel> publishModelPage = new Page<>(page, pagesize);
        IPage<PublishModel> publishModelIPage = publishMapper.selectPage(publishModelPage, publishModelQueryWrapper);
        List<PublishModel> publishModels = publishModelIPage.getRecords();
        List < PublishCard > publishCardList = publishModels.stream().map(new Function<PublishModel, PublishCard>() {
            @Override
            public PublishCard apply(PublishModel publishModel) {
                //查询评论
                QueryWrapper<CommentModel> queryWrapper01 = new QueryWrapper<>();
                queryWrapper01.eq("dynamic_id", publishModel.getId());
                Page<CommentModel> commentModelPage = new Page<>(1, 4);

                IPage<CommentModel> commentModelIPage = commentMapper.selectPage(commentModelPage, queryWrapper01);
                List<CommentModel> commentModels = commentModelIPage.getRecords();
                List<CommentCard> commentCards = commentModels.stream().map(new Function<CommentModel, CommentCard>() {
                    @Override
                    public CommentCard apply(CommentModel commentModel) {
                        CommentCard commentCard = new CommentCard();
                        BeanUtils.copyProperties(commentModel, commentCard);
                        return commentCard;
                    }
                }).collect(Collectors.toList());

                //是否点赞
                FavoriteDynamicModel favoriteDynamicModel = queryFavoriteDynamicById(userId, publishModel.getId());
                boolean isFavoriteDynamic = false;
                if (favoriteDynamicModel == null) {
                    isFavoriteDynamic = false;
                } else {
                    isFavoriteDynamic = true;
                }
                PublishCard publishCard = new PublishCard();
                BeanUtils.copyProperties(publishModel, publishCard);
                User user = userMapper.selectById(publishModel.getUserId());
                publishCard.setNickName(user.getNickname());
                publishCard.setFaceImage(user.getFaceImage());
                publishCard.setGender(user.getGender());
                publishCard.setCommentList(commentCards);
                publishCard.setAttention(true);
                publishCard.setFavoriteDynamic(isFavoriteDynamic);
                return publishCard;
            }
        }).collect(Collectors.toList());

        return publishCardList;
    }

    @Override
    public CommentCard sendComment(CommentCard commentCard) {
        CommentModel commentModel = new CommentModel();
        BeanUtils.copyProperties(commentCard,commentModel);
        commentModel.setCreateAt(new Date());
        commentModel.setUpdateAt(new Date());
        int index = commentMapper.insert(commentModel);
        if(index>0){
            return commentCard;
        }
        return null;
    }

    @Override
    public CommentCard sendReplyComment(CommentCard commentCard) {
        ReplyCommentModel commentModel = new ReplyCommentModel();
        BeanUtils.copyProperties(commentCard,commentModel);
        commentModel.setCreateAt(new Date());
        commentModel.setUpdateAt(new Date());
        int index = replyCommentMapper.insert(commentModel);
        if(index>0){
            return commentCard;
        }
        return null;
    }

    @Override
    public int attention(long userId, long attentionUserId) {
        AttentionModel attentionModel = queryAttentionById(userId,attentionUserId);
        if(attentionModel == null){
            attentionModel = new AttentionModel();
            attentionModel.setCreateAt(new Date());
            attentionModel.setUpdateAt(new Date());
            attentionModel.setFollowUserId(userId);
            attentionModel.setFollowedUserId(attentionUserId);
            return attentionMapper.insert(attentionModel);
        }else {
            QueryWrapper<AttentionModel> queryWrapper01 = new QueryWrapper<>();
            queryWrapper01.eq("follow_user_id",userId);
            queryWrapper01.eq("followed_user_id",attentionUserId);
            return attentionMapper.delete(queryWrapper01);
        }
    }

    @Override
    public AttentionModel queryAttentionById(long userId, long attentionUserId) {
        //查询是否已经关注
        QueryWrapper<AttentionModel> queryWrapper01 = new QueryWrapper<>();
        queryWrapper01.eq("follow_user_id",userId);
        queryWrapper01.eq("followed_user_id",attentionUserId);
        AttentionModel attentionModel = attentionMapper.selectOne(queryWrapper01);
        return attentionModel;
    }

    @Override
    public FavoriteDynamicModel queryFavoriteDynamicById(long userId, long favoriteDynamicId) {
        //查询是否已经关注
        QueryWrapper<FavoriteDynamicModel> queryWrapper01 = new QueryWrapper<>();
        queryWrapper01.eq("user_id",userId);
        queryWrapper01.eq("dynamic_id",favoriteDynamicId);
        FavoriteDynamicModel favoriteDynamicModel = favoriteDynamicMapper.selectOne(queryWrapper01);
        return favoriteDynamicModel;
    }

    @Override
    public int favoriteDynamic(long id, long favoriteDynamicId) {
        //查询是否已经关注
        QueryWrapper<FavoriteDynamicModel> queryWrapper01 = new QueryWrapper<>();
        queryWrapper01.eq("user_id",id);
        queryWrapper01.eq("dynamic_id",favoriteDynamicId);
        FavoriteDynamicModel favoriteDynamicModel = favoriteDynamicMapper.selectOne(queryWrapper01);
        if(favoriteDynamicModel == null){
            favoriteDynamicModel = new FavoriteDynamicModel();
            favoriteDynamicModel.setCreateAt(new Date());
            favoriteDynamicModel.setUpdateAt(new Date());
            favoriteDynamicModel.setUserId(id);
            favoriteDynamicModel.setDynamicId(favoriteDynamicId);
            return favoriteDynamicMapper.insert(favoriteDynamicModel);
        }else {
            return favoriteDynamicMapper.delete(queryWrapper01);
        }
    }

}
