package com.zzj.learn.serviceimp;

import com.zzj.learn.dao.ZonePublishMapper;
import com.zzj.learn.domain.PublishModel;
import com.zzj.learn.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    ZonePublishMapper publishMapper;
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
    public List<PublishModel> getPublishList() {
        return publishMapper.selectList(null);
    }
}
