package com.qidao.application.service.msg.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.msg.Msg;
import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.entity.msg.MsgRecordExample;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.msg.CustomMsgRecordMapper;
import com.qidao.application.mapper.msg.MsgMapper;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.msg.*;
import com.qidao.application.model.msg.enums.MsgConstantEnum;
import com.qidao.application.model.msg.enums.MsgExceptionEnum;
import com.qidao.application.service.msg.MsgRecordService;
import com.qidao.application.service.msg.other.InsertMsgRecordInterfaceComponent;
import com.qidao.application.vo.NoticeMsgRecordPageReqVo;
import com.qidao.application.vo.NoticeMsgRecordPageRespVo;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("msgRecordService")
@Slf4j
public class MsgRecordServiceImpl implements MsgRecordService {

    @Resource
    private MsgRecordMapper msgRecordMapper;

    @Resource
    private CustomMsgRecordMapper customMsgRecordMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private InsertMsgRecordInterfaceComponent insertMsgRecordInterfaceComponent;

    @Resource
    private MsgMapper msgMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    /**
     * 查询消息记录
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<MsgRecordPageRes>> getMsgRecordPage(MsgRecordPageReq req) {
        log.info("MsgRecordServiceImpl -> getMsgRecordPage -> start -> param:{}", req);
        MsgRecordExample msgRecordExample = new MsgRecordExample();
        MsgRecordExample.Criteria criteria = msgRecordExample.createCriteria();
        log.info("MsgRecordServiceImpl -> getMsgRecordPage -> req.getMsgId()!=null :{}", req.getMsgId() != null);
        if (req.getMsgId() != null) {
            criteria.andMsgIdEqualTo(req.getMsgId());
        }
        log.info("MsgRecordServiceImpl -> getMsgRecordPage -> req.getStatus() != null :{}", req.getStatus() != null);
        if (req.getStatus() != null) {
            criteria.andStatusEqualTo(req.getStatus());
        }
        log.info("MsgRecordServiceImpl -> getMsgRecordPage -> req.getMemberId() != null :{}", req.getMemberId() != null);
        if (req.getMemberId() != null) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Page<MsgRecord> page = PageUtil.start(req, () -> msgRecordMapper.selectByExample(msgRecordExample));
        ServicePage<List<MsgRecordPageRes>> res = new ServicePage<>();
        BeanUtils.copyProperties(page, res);
        List<MsgRecord> result = page.getResult();
        log.info("MsgRecordServiceImpl -> getMsgRecordPage -> CollUtil.isNotEmpty(result) :{}", CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)) {
            List<MsgRecordPageRes> pageRes = new ArrayList<>();
            result.forEach(msgRecord -> {
                MsgRecordPageRes msgRecordPageRes = new MsgRecordPageRes();
                BeanUtils.copyProperties(msgRecord, msgRecordPageRes);
                pageRes.add(msgRecordPageRes);
            });
            res.setResult(pageRes);
        }
        log.info("MsgRecordServiceImpl -> getMsgRecordPage -> end");
        return res;
    }

    /**
     * 修改消息记录
     *
     * @param req
     */
    @Override
    public void update(MsgRecordUpdateReq req) {
        log.info("MsgRecordServiceImpl -> update -> start -> param:{}", req);
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        log.info("MsgRecordServiceImpl -> update -> member ==null ；{}", member == null);
        if (member == null) {
            log.error("MsgRecordServiceImpl -> update -> error :{}", MemberExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MemberExceptionEnum.DELETE_TRUE);
        }
        Msg msg = msgMapper.selectByPrimaryKey(req.getMsgId());
        log.info("MsgRecordServiceImpl -> update -> msg==null ；{}", msg == null);
        if (msg == null) {
            log.error("MsgRecordServiceImpl -> update -> error :{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        MsgRecordExample msgRecordExample = new MsgRecordExample();
        msgRecordExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(req.getId());
        MsgRecord msgRecord = msgRecordMapper.selectOneByExample(msgRecordExample);
        log.info("MsgRecordServiceImpl -> update -> msgRecord == null :{}", msgRecord == null);
        if (msgRecord == null) {
            log.error("MsgRecordServiceImpl -> update -> error :{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        BeanUtils.copyProperties(req, msgRecord);
        msgRecordMapper.updateByPrimaryKeySelective(msgRecord);
        log.info("MsgRecordServiceImpl -> update -> end");
    }

    /**
     * 新增消息记录
     *
     * @param req
     */
    @Override
    public Long insert(MsgRecordInsertReq req) {
        log.info("MsgRecordServiceImpl -> insert -> start -> param:{}", req);
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        log.info("MsgRecordServiceImpl -> insert -> member ==null ；{}", member == null);
        if (member == null) {
            log.error("MsgRecordServiceImpl -> insert -> error :{}", MemberExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MemberExceptionEnum.DELETE_TRUE);
        }
        Msg msg = msgMapper.selectByPrimaryKey(req.getMsgId());
        log.info("MsgRecordServiceImpl -> insert -> msg==null ；{}", msg == null);
        if (msg == null) {
            log.error("MsgRecordServiceImpl -> insert -> error :{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        long id = snowflakeIdWorker53.nextId();
        MsgRecord msgRecord = new MsgRecord();
        BeanUtils.copyProperties(req, msgRecord);
        msgRecord.setId(id);
        msgRecord.setDelFlag(ConstantEnum.NOT_DEL.getByte());
        msgRecord.setStatus(MsgConstantEnum.STATUS_TRUE.getCode());
        msgRecordMapper.insertSelective(msgRecord);
        log.info("MsgRecordServiceImpl -> insert -> end -> id -> {}", id);
        return id;
    }

    /**
     * 删除消息记录
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        log.info("MsgRecordServiceImpl -> delete -> start -> param:{}", id);
        MsgRecordExample msgRecordExample = new MsgRecordExample();
        msgRecordExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(id);
        MsgRecord msgRecord = msgRecordMapper.selectOneByExample(msgRecordExample);
        log.info("MsgRecordServiceImpl -> delete -> msgRecord == null :{}", msgRecord == null);
        if (msgRecord == null) {
            log.error("MsgRecordServiceImpl -> delete -> error :{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        msgRecord.setDelFlag(ConstantEnum.DELETED.getByte());
        msgRecordMapper.updateByPrimaryKeySelective(msgRecord);
        log.info("MsgRecordServiceImpl -> delete -> end");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> insertMsgRecordByReceiveType(Long msgId, Integer receiveType) {
        log.info("MsgRecordServiceImpl -> insertMsgRecordByReceiveType -> start -> msgId:{} receiveType:{}", msgId, receiveType);
        Msg msg = msgMapper.selectByPrimaryKey(msgId);
        if (msg == null) {
            log.error("MsgRecordServiceImpl -> insertMsgRecordByReceiveType -> error :{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }

        LocalDateTime now = LocalDateTime.now();
        List<Long> memberIds = insertMsgRecordInterfaceComponent.insertMsgRecordByReceiveType(msgId, receiveType, now);
        log.info("MsgRecordServiceImpl -> insertMsgRecordByReceiveType -> end ");
        return memberIds;
    }

    @Override
    public void clearReadMsgRecordByMemberId(Long memberId) {
        log.info("MsgRecordServiceImpl -> clearReadMsgRecordByMemberId -> start -> memberId:{}", memberId);
        MsgRecordExample example = new MsgRecordExample();
        example.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andStatusEqualTo(MsgConstantEnum.RECORD_READ.getCode())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        int i = msgRecordMapper.updateByExampleSelective(MsgRecord.builder().delFlag(ConstantEnum.DELETED.getByte()).build(), example, MsgRecord.Column.delFlag);
        log.info("MsgRecordServiceImpl -> clearReadMsgRecordByMemberId -> end db_size:{}", i);
    }

    @Override
    public ServicePage<List<NoticeMsgRecordPageResp>> getAllNoticeMsgRecordPage(NoticeMsgRecordPageReq req) {
        log.info("MsgRecordServiceImpl -> getAllNoticeMsgRecordPage -> start -> param:{}", req);
        NoticeMsgRecordPageReqVo dbReq = new NoticeMsgRecordPageReqVo();
        BeanUtils.copyProperties(req, dbReq);


        Page<NoticeMsgRecordPageRespVo> page = PageUtil.start(req, () -> customMsgRecordMapper.getAllNoticeMsgRecordPage(dbReq));
        ServicePage<List<NoticeMsgRecordPageResp>> res = new ServicePage<>();
        BeanUtils.copyProperties(page, res);
        List<NoticeMsgRecordPageRespVo> result = page.getResult();

        //记录未读状态消息记录id
        List<Long> notReadMsgRecordIds = new ArrayList<>(result.size());
        Integer msgRecordStatus = MsgConstantEnum.RECORD_UNREAD.getCode();

        List<NoticeMsgRecordPageResp> listMsg = result.stream().map(item -> {
            NoticeMsgRecordPageResp resp = new NoticeMsgRecordPageResp();
            BeanUtils.copyProperties(item, resp);
            //添加未读消息记录id
            if (msgRecordStatus.equals(item.getStatus())) {
                notReadMsgRecordIds.add(item.getId());
            }
            return resp;
        }).collect(Collectors.toList());
        res.setResult(listMsg);

        //修改为已读状态
        log.info("MsgRecordServiceImpl -> getAllNoticeMsgRecordPage -> 未读消息size:{}", notReadMsgRecordIds.size());
        if (CollUtil.isNotEmpty(notReadMsgRecordIds)) {
            MsgRecord record = new MsgRecord();
            record.setStatus(MsgConstantEnum.RECORD_READ.getCode());

            MsgRecordExample example = new MsgRecordExample();
            example.createCriteria()
                    .andIdIn(notReadMsgRecordIds);
            msgRecordMapper.updateByExampleSelective(record, example, MsgRecord.Column.status);
        }
        log.info("MsgRecordServiceImpl -> getAllNoticeMsgRecordPage -> end");
        return res;
    }

    @Override
    public long selectNotReadMsgCount(Long memberId) {
        MsgRecordExample example = new MsgRecordExample();
        example.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andStatusEqualTo(MsgConstantEnum.RECORD_UNREAD.getCode());
        return msgRecordMapper.countByExample(example);
    }
}
