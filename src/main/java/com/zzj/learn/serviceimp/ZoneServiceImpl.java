package com.zzj.learn.serviceimp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzj.learn.dao.CommentMapper;
import com.zzj.learn.dao.ReplyCommentMapper;
import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.dao.ZonePublishMapper;
import com.zzj.learn.domain.CommentModel;
import com.zzj.learn.domain.PublishModel;
import com.zzj.learn.domain.ReplyCommentModel;
import com.zzj.learn.domain.User;
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
    public PublishCard getDynamicById(long id) {
        PublishModel publishModel = publishMapper.selectById(id);
        //查询评论
        QueryWrapper<CommentModel> queryWrapper01 = new QueryWrapper<>();
        queryWrapper01.eq("dynamic_id",id);
        Page<CommentModel> page = new Page<>(1,3);

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
        PublishCard publishCard = new PublishCard();
        BeanUtils.copyProperties(publishModel,publishCard);
        User user = userMapper.selectById(publishModel.getUserId());
        publishCard.setNickName(user.getNickname());
        publishCard.setFaceImage(user.getFaceImage());
        publishCard.setGender(user.getGender());
        publishCard.setCommentList(commentCards);
        return publishCard;
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

}
