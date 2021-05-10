package com.qidao.application.service.member.impl;

import cn.hutool.core.util.ObjectUtil;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.member.Feedback;
import com.qidao.application.entity.member.Member;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.member.FeedbackMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.SelectConfigEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.feedback.FeedbackAddReq;
import com.qidao.application.model.member.feedback.FeedbackEnum;
import com.qidao.application.model.member.feedback.FeedbackExceptionEnum;
import com.qidao.application.service.member.FeedbackService;
import com.qidao.application.vo.FeedbackMemberVo;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Service("feedbackService")
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private SelectConfigMapper selectConfigMapper;
    @Autowired
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    /**
     * 用于插入新的反馈记录
     *
     * @param feedbackAddReq 待添加的反馈对象
     * @return 插入是否成功
     *
     */
    @Override
    public Boolean insert(FeedbackAddReq feedbackAddReq) {
        log.info("FeedbackServiceImpl -> insert -> start -> feedbackAddReq : {}", feedbackAddReq);
        FeedbackMemberVo member = memberMapper.selectById(feedbackAddReq.getMemberId());
        if(ObjectUtil.isEmpty(member) || member.getDelFlag() == ConstantEnum.DELETED.getByte()){
            log.error("FeedbackServiceImpl -> insert -> end -> error : {}", FeedbackExceptionEnum.MEMBER_NOT_EXIST.getMessage());
            throw new BusinessException(FeedbackExceptionEnum.MEMBER_NOT_EXIST);
        }
        Long selectConfigId = selectConfigMapper.getConfigId(feedbackAddReq.getReasonId());
        log.info("FeedbackServiceImpl -> insert -> ObjectUtil.isEmpty(selectConfigId) : {}", (ObjectUtil.isEmpty(selectConfigId)));
        if(ObjectUtil.isEmpty(selectConfigId)){
            log.error("FeedbackServiceImpl -> insert -> end -> error : {}", FeedbackExceptionEnum.REASON_NOT_EXIST.getMessage());
            throw new BusinessException(FeedbackExceptionEnum.REASON_NOT_EXIST);
        }
        Feedback feedback = Feedback.builder()
                .id(snowflakeIdWorker53.nextId())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .delFlag((byte) FeedbackEnum.DELETE_FLAG_NO.getValue())
                .reasonId(selectConfigId)
                .status(FeedbackEnum.STATUS_PENDING.getValue())
                .memberId(member.getId())
                .level(member.getLevel())
                .description(feedbackAddReq.getDescription())
                .build();
        log.info("FeedbackServiceImpl -> insert -> end");
        return  feedbackMapper.insert(feedback) > 0;
    }

}
