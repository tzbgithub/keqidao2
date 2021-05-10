package com.qidao.application.service.dynamic.channel;

import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.common.req.BasePageQuery;
import com.qidao.application.model.dynamic.channel.ChannelAddReq;
import com.qidao.application.model.dynamic.channel.ChannelQuery;
import com.qidao.application.model.dynamic.channel.ChannelRes;
import com.qidao.application.model.dynamic.channel.ChannelUpdateReq;
import com.qidao.framework.service.ServicePage;

import java.util.List;


/**
 * @author xinfeng
 * @create 2021-01-29 13:40
 */
public interface ChannelService {

    /**
     * 新增频道记录 <br>
     * @param channelAddReq {@link ChannelAddReq} - 待添加的频道对象
     * @return true-添加成功 false-添加失败
     */
    Boolean insert(ChannelAddReq channelAddReq);

    /**
     * 修改频道记录 <br>
     * @param channelUpdateReq {@link ChannelUpdateReq} - 待更新的频道对象
     * @return true-更新成功 false-更新失败
     */
    Boolean update(ChannelUpdateReq channelUpdateReq);

    /**
     * 根据频道标题查询频道列表 <br>
     *
     * @param channelQuery {@link ChannelRes} - 查询结果对象
     * @return 集合 {@link ChannelRes}
     */
    ServicePage<List<ChannelRes>> getListByName(ChannelQuery channelQuery);

    /**
     * 根据ID删除频道记录 <br>
     * @param baseIdQuery {@link BaseIdQuery} - 待删除的频道ID
     * @return true-删除成功 false-删除失败
     */
    Boolean deleteById(BaseIdQuery baseIdQuery);

    /**
     * 查询所有频道列表 <br>
     * @param basePageQuery {@link BasePageQuery} - 待查询对象
     * @return 集合 {@link ChannelRes}
     */
    ServicePage<List<ChannelRes>> getList(BasePageQuery basePageQuery);
}
