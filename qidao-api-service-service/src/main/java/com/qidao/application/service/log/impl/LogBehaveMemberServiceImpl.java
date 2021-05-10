package com.qidao.application.service.log.impl;

import com.qidao.application.entity.log.LogBehaveMember;
import com.qidao.application.entity.log.LogBehaveMemberExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.mapper.log.CustomLogBehaveMemberMapper;
import com.qidao.application.mapper.log.LogBehaveMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.log.LogBehaveMemberErrorEnum;
import com.qidao.application.service.log.LogBehaveMemberService;
import com.qidao.application.vo.BehaveMemeberReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class LogBehaveMemberServiceImpl implements LogBehaveMemberService {
    @Autowired
    private  MemberMapper memberMapper;
    @Autowired
    private LogBehaveMemberMapper logBehaveMemberMapper;
    @Autowired
    private CustomLogBehaveMemberMapper customLogBehaveMemberMapper;
    /**
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBehaveMemeber(BehaveMemeberReq behaveMemeberReq) {
        log.info("LogBehaveMemberController-> ---- 浏览记录:{}",behaveMemeberReq);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(behaveMemeberReq.getMemberId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        log.info("LogBehaveMemberController-> ---- saveBehaveMemeber 用户不存在");
        if(member==null){
            throw new BusinessException(LogBehaveMemberErrorEnum.USER_LOGBEHAVEMEMBER_ERROR);
        }
        memberExample.clear();
        memberExample.createCriteria().andIdEqualTo(behaveMemeberReq.getVisitMemberId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member visitMember = memberMapper.selectOneByExample(memberExample);
        log.info("LogBehaveMemberController-> ---- saveBehaveMemeber 浏览人不存在");
        if(visitMember==null){
            throw new BusinessException(LogBehaveMemberErrorEnum.VISITOR_LOGBEHAVEMEMBER_ERROR);
        }
        LogBehaveMember logBehaveMember = new LogBehaveMember();
        LogBehaveMemberExample logBehaveMemberExample = new LogBehaveMemberExample();
        logBehaveMemberExample.createCriteria().andMemberIdEqualTo(behaveMemeberReq.getMemberId()).
                andVisitMemberIdEqualTo(behaveMemeberReq.getVisitMemberId());
        LogBehaveMember logBehaveMembe = logBehaveMemberMapper.selectOneByExample(logBehaveMemberExample);
        log.info("LogBehaveMemberController-> ---- saveBehaveMemeber 浏览记录存在直接改状态");
        if(logBehaveMembe!=null){
            logBehaveMembe.setDelFlag(ConstantEnum.NOT_DEL.getByte());
            logBehaveMemberMapper.updateByExampleSelective(logBehaveMembe,logBehaveMemberExample);
        }else {
            logBehaveMember.setDelFlag(ConstantEnum.NOT_DEL.getByte()).
                    setMemberId(behaveMemeberReq.getMemberId()).
                    setVisitMemberId(behaveMemeberReq.getVisitMemberId());
            logBehaveMemberMapper.insertSelective(logBehaveMember);
        }

    }

    /**
     * 清空浏览痕迹
     * @param baseIdQuery
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBehaveMember(BaseIdQuery baseIdQuery) {
        log.info("LogBehaveMemberController-> ---- deleteBehaveMember 清空浏览痕迹");
        boolean flag = customLogBehaveMemberMapper.deleteBehaveMember(baseIdQuery.getId());
        log.info("LogBehaveMemberController-> ---- deleteBehaveMember 是否变更状态");
        if(flag){
            return true;
        }
        return false;
    }
}
