package com.zzj.learn.serviceimp;

import com.zzj.learn.dao.UserMapper;
import com.zzj.learn.dao.ZonePublishMapper;
import com.zzj.learn.domain.PublishModel;
import com.zzj.learn.domain.User;
import com.zzj.learn.service.ZoneService;
import com.zzj.learn.utils.SpringUtil;
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
        PublishCard publishCard = new PublishCard();
        BeanUtils.copyProperties(publishModel,publishCard);
        User user = userMapper.selectById(publishModel.getUserId());
        publishCard.setNickName(user.getNickname());
        publishCard.setFaceImage(user.getFaceImage());
        publishCard.setGender(user.getGender());
        return publishCard;
    }
}
