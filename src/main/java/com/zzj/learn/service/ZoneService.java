package com.zzj.learn.service;

import com.zzj.learn.domain.PublishModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZoneService {

    PublishModel publish(long userId,String imageUrlList
            , String content, String location);

    List<PublishModel> getPublishList();
}
