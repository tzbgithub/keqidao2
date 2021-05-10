package com.qidao.application.mapper.msg;

import com.qidao.application.vo.NoticeMsgRecordPageReqVo;
import com.qidao.application.vo.NoticeMsgRecordPageRespVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomMsgRecordMapper {

    /**
     * 根据消息id 获取会员id列表
     *
     * @param msgId
     * @return
     */
    List<String> getMemberIdsByMsgId(Long msgId);

    /**
     * 所有通知类型的消息查询
     * @param param
     * @return
     */
    List<NoticeMsgRecordPageRespVo> getAllNoticeMsgRecordPage(NoticeMsgRecordPageReqVo param);
}