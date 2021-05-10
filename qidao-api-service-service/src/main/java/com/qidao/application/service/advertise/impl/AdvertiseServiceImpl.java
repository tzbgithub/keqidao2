package com.qidao.application.service.advertise.impl;

import cn.hutool.core.bean.BeanUtil;
import com.qidao.application.mapper.advertise.CustomAdvertiseMapper;
import com.qidao.application.model.aadvertise.AdvertiseInfoRes;
import com.qidao.application.service.advertise.AdvertiseService;
import com.qidao.application.vo.AdvertiseCanalChannel;
import com.qidao.application.vo.AdvertisePageRep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdvertiseServiceImpl implements AdvertiseService {
    @Autowired
    private  CustomAdvertiseMapper customAdvertiseMapper;
    @Override
    public List<AdvertisePageRep> findAdvertiseByList(Long location, Long cancal) {
        return  customAdvertiseMapper.findAdvertiseByList(location, cancal);
    }

    @Override
    public List<AdvertiseInfoRes> getList(Long location, List<Long> channelIdList) {
        List<AdvertisePageRep> list = customAdvertiseMapper.getList(location, channelIdList);
        return list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertiseCanalChannel> getAdvertiseByCanalAndChannel(Long canalId, Long channelId) {
        List<AdvertiseCanalChannel> res = customAdvertiseMapper.getAdvertiseByCanalAndChannel(canalId, channelId);
        return res;
    }


    private  AdvertiseInfoRes convert(AdvertisePageRep bean){
        AdvertiseInfoRes result = new AdvertiseInfoRes();
        BeanUtil.copyProperties(bean,result);
        return result;
    }
}
