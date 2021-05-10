package com.qidao.application.service.advertise;

import com.qidao.application.model.aadvertise.AdvertiseInfoRes;
import com.qidao.application.vo.AdvertiseCanalChannel;
import com.qidao.application.vo.AdvertisePageRep;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AdvertiseService {
    /**
     * 查询广告位
     * @return
     */
    @Deprecated
    List<AdvertisePageRep> findAdvertiseByList(@Param("lacation") Long location ,@Param("cancal") Long cancal);

    /**
     * 根据频道获取广告信息
     * @param location 广告位置 ID
     * @param channelIdList 频道ID 集合，不能为空
     * @return 集合：{@link AdvertiseInfoRes}
     */
    List<AdvertiseInfoRes> getList(@NotNull Long location , @NotNull List<Long> channelIdList);

    /**
     * 根据渠道号和频道获取广告
     * @param canalId 渠道号
     * @param channelId 频道id
     * @return
     */
    List<AdvertiseCanalChannel> getAdvertiseByCanalAndChannel(@NotNull Long canalId, @NotNull Long channelId);
}
