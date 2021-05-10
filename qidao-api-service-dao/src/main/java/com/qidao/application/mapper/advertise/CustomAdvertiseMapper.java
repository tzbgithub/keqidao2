package com.qidao.application.mapper.advertise;

import com.qidao.application.vo.AdvertiseCanalChannel;
import com.qidao.application.vo.AdvertisePageRep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomAdvertiseMapper {
    @Deprecated
    List<AdvertisePageRep> findAdvertiseByList(Long location, Long cancal) ;

    /**
     * 根据频道获取广告信息
     * @param location NotNull 广告位置 ID
     * @param channelIdList NotNull 频道ID 集合，不能为空
     * @return 集合：{@link AdvertisePageRep}
     */
    List<AdvertisePageRep> getList(@Param("location") Long location,@Param("channelIdList") List<Long> channelIdList);

    /**
     * 通过渠道号和频道来查询广告
     * @param canalId 渠道号
     * @param channelId 频道id
     * @return 集合：{@link AdvertiseCanalChannel}
     */
    List<AdvertiseCanalChannel> getAdvertiseByCanalAndChannel(@Param("canalId")Long canalId, @Param("channelId") Long channelId);
}