package com.qidao.application.service.dynamic.complaint.impl;

import cn.hutool.core.util.ObjectUtil;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.dynamic.Complaint;
import com.qidao.application.entity.dynamic.ComplaintExample;
import com.qidao.application.entity.dynamic.Dynamic;
import com.qidao.application.entity.dynamic.DynamicExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.dynamic.ComplaintMapper;
import com.qidao.application.mapper.dynamic.DynamicMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.SelectConfigEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dynamic.ComplaintAddReq;
import com.qidao.application.model.dynamic.ComplaintEnum;
import com.qidao.application.model.dynamic.ComplaintExceptionEnum;
import com.qidao.application.model.member.feedback.MemberComplaintAddReq;
import com.qidao.application.service.dynamic.complaint.ComplaintService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Service(value = "ComplaintService")
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {

    @Resource
    private ComplaintMapper complaintMapper;

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private SelectConfigMapper selectConfigMapper;

    @Autowired
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Boolean insert(ComplaintAddReq complaintAddReq) {
        log.info("ComplaintServiceImpl -> insert -> start -> complaintAddReq : {}", complaintAddReq);

        DynamicExample dynamicExample = new DynamicExample();
        dynamicExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andIdEqualTo(complaintAddReq.getDynamicId());
        Dynamic dynamic = dynamicMapper.selectOneByExample(dynamicExample);
        log.info("ComplaintServiceImpl -> insert -> ObjectUtil.isEmpty(dynamic) : {}", (ObjectUtil.isEmpty(dynamic)));
        if (ObjectUtil.isEmpty(dynamic)) {
            log.error("ComplaintServiceImpl -> insert -> end -> error : {}", ComplaintExceptionEnum.COMPLAINT_DYNAMIC_NOT_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.COMPLAINT_DYNAMIC_NOT_EXISTS);
        }

        Member member = memberMapper.selectByPrimaryKey(complaintAddReq.getMemberId());
        log.info("ComplaintServiceImpl -> insert -> ObjectUtil.isEmpty(member) ：{}", (ObjectUtil.isEmpty(member)));
        if (ObjectUtil.isEmpty(member)) {
            log.error("ComplaintServiceImpl -> insert -> end -> error : {}", ComplaintExceptionEnum.COMPLAINT_MEMBER_NOT_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.COMPLAINT_MEMBER_NOT_EXISTS);
        }

        log.info("ComplaintServiceImpl -> insert -> dynamic.getMemberId().equals(complaintAddReq.getMemberId()) : {}",
                (dynamic.getMemberId().equals(complaintAddReq.getMemberId())));
        if (dynamic.getMemberId().equals(complaintAddReq.getMemberId())) {
            log.error("ComplaintServiceImpl -> insert -> end -> error : {}", ComplaintExceptionEnum.COMPLAINT_ONESELF_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.COMPLAINT_ONESELF_EXISTS);
        }

        SelectConfigExample slex = new SelectConfigExample();
        SelectConfigExample.Criteria slcr = slex.createCriteria();
        slcr.andIdEqualTo(complaintAddReq.getReasonId());
        slcr.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        slcr.andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue());
        SelectConfig selectConfig = selectConfigMapper.selectOneByExample(slex);
        log.info("ComplaintServiceImpl -> insert -> ObjectUtil.isEmpty(selectConfig) : {}", (ObjectUtil.isEmpty(selectConfig)));
        if (ObjectUtil.isEmpty(selectConfig)) {
            log.error("ComplaintServiceImpl -> insert -> end -> error : {}", ComplaintExceptionEnum.COMPLAINT_REASON_NOT_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.COMPLAINT_REASON_NOT_EXISTS);
        }

        ComplaintExample example = new ComplaintExample();
        ComplaintExample.Criteria criteria = example.createCriteria();
        criteria.andDynamicIdEqualTo(complaintAddReq.getDynamicId());
        criteria.andMemberIdEqualTo(complaintAddReq.getMemberId());
        criteria.andTypeEqualTo(ComplaintEnum.TYPE_DYNAMIC.getValue());
        Complaint existsComplaint = complaintMapper.selectOneByExample(example);
        log.info("ComplaintServiceImpl -> insert -> ObjectUtil.isNotEmpty(existsComplaint) : {}", (ObjectUtil.isNotEmpty(existsComplaint)));
        if (ObjectUtil.isNotEmpty(existsComplaint)) {
            log.error("ComplaintServiceImpl -> insert -> end -> error : {}", ComplaintExceptionEnum.DYNAMIC_AGAIN_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.DYNAMIC_AGAIN_EXISTS);
        }

        Complaint newComplaint = Complaint.builder()
                .memberId(complaintAddReq.getMemberId())
                .dynamicId(complaintAddReq.getDynamicId())
                .complaintMemberId(dynamic.getMemberId())
                .level(member.getLevel())
                .reasonId(selectConfig.getId())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .delFlag(ConstantEnum.NOT_DEL.getByte())
                .status(ComplaintEnum.PROCESS_STATUS_UN.getValue())
                .type(ComplaintEnum.TYPE_DYNAMIC.getValue())
                .id(snowflakeIdWorker53.nextId())
                .build();
        log.info("ComplaintServiceImpl -> insert -> end");
        return complaintMapper.insertSelective(newComplaint) > 0;
    }

    @Override
    public Boolean memberComplaint(MemberComplaintAddReq complaintAddReq) {
        log.info("ComplaintServiceImpl -> memberComplaint -> start -> complaintAddReq : {}", complaintAddReq);

        String lockKey = "LOCK::COMPLAINT::" + complaintAddReq.getMemberId() + "::" + complaintAddReq.getComplaintMemberId();
        RBucket<Long> bucket = redissonClient.getBucket(lockKey);
        boolean ifExists = bucket.trySet(complaintAddReq.getReasonId(), 5L, TimeUnit.SECONDS);
        if (!ifExists) {
            log.info("ComplaintServiceImpl -> memberComplaint -> 投诉redisson存在");
            return false;
        }

        Member member = memberMapper.selectByPrimaryKey(complaintAddReq.getMemberId());
        log.info("ComplaintServiceImpl -> memberComplaint -> ObjectUtil.isEmpty(member) ：{}", (ObjectUtil.isEmpty(member)));
        if (ObjectUtil.isEmpty(member)) {
            log.error("ComplaintServiceImpl -> memberComplaint -> end -> error : {}", ComplaintExceptionEnum.COMPLAINT_MEMBER_NOT_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.COMPLAINT_MEMBER_NOT_EXISTS);
        }

        Long complaintMemberId = complaintAddReq.getComplaintMemberId();
        if (complaintMemberId.equals(complaintAddReq.getMemberId())) {
            log.error("ComplaintServiceImpl -> memberComplaint -> end -> error : {}", ComplaintExceptionEnum.COMPLAINT_ONESELF_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.COMPLAINT_ONESELF_EXISTS);
        }

        //验证投诉是否存在
        ComplaintExample example = new ComplaintExample();
        ComplaintExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(complaintAddReq.getMemberId());
        criteria.andComplaintMemberIdEqualTo(complaintMemberId);
        criteria.andTypeEqualTo(ComplaintEnum.TYPE_MEMBER.getValue());
        Complaint existsComplaint = complaintMapper.selectOneByExample(example);
        log.info("ComplaintServiceImpl -> memberComplaint -> ObjectUtil.isNotEmpty(existsComplaint) : {}", (ObjectUtil.isNotEmpty(existsComplaint)));
        if (ObjectUtil.isNotEmpty(existsComplaint)) {
            log.error("ComplaintServiceImpl -> memberComplaint -> end -> error : {}", ComplaintExceptionEnum.MEMBER_AGAIN_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.MEMBER_AGAIN_EXISTS);
        }

        SelectConfigExample slex = new SelectConfigExample();
        SelectConfigExample.Criteria slcr = slex.createCriteria();
        slcr.andIdEqualTo(complaintAddReq.getReasonId());
        slcr.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        slcr.andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue());
        SelectConfig selectConfig = selectConfigMapper.selectOneByExample(slex);
        log.info("ComplaintServiceImpl -> memberComplaint -> ObjectUtil.isEmpty(selectConfig) : {}", (ObjectUtil.isEmpty(selectConfig)));
        if (ObjectUtil.isEmpty(selectConfig)) {
            log.error("ComplaintServiceImpl -> memberComplaint -> end -> error : {}", ComplaintExceptionEnum.COMPLAINT_REASON_NOT_EXISTS.getMessage());
            throw new BusinessException(ComplaintExceptionEnum.COMPLAINT_REASON_NOT_EXISTS);
        }

        Complaint newComplaint = Complaint.builder()
                .memberId(complaintAddReq.getMemberId())
                .complaintMemberId(complaintMemberId)
                .level(member.getLevel())
                .reasonId(selectConfig.getId())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .delFlag(ConstantEnum.NOT_DEL.getByte())
                .status(ComplaintEnum.PROCESS_STATUS_UN.getValue())
                .type(ComplaintEnum.TYPE_MEMBER.getValue())
                .id(snowflakeIdWorker53.nextId())
                .build();
        log.info("ComplaintServiceImpl -> memberComplaint -> end");
        boolean result = complaintMapper.insertSelective(newComplaint) > 0;
        //删除redis中的key
        bucket.delete();
        return result;
    }
}
