package com.qidao.application.service.contract.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.qidao.application.entity.contract.Contract;
import com.qidao.application.entity.contract.ContractExample;
import com.qidao.application.entity.contract.Progress;
import com.qidao.application.entity.contract.ProgressExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.organization.OrganizationExample;
import com.qidao.application.mapper.contract.ContractMapper;
import com.qidao.application.mapper.contract.CustomContractMapper;
import com.qidao.application.mapper.contract.ProgressMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.contract.ContractEnum;
import com.qidao.application.model.contract.progress.*;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.service.contract.ProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * {
 * 上面是提供给Controller的方法
 * 下面是内部使用的private方法
 * }
 * @date : 2021/1/4 11:21 AM
 */
@Slf4j
@Service("progressService")
public class ProgressServiceImpl implements ProgressService {

    @Resource
    private ProgressMapper progressMapper;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private CustomContractMapper customContractMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private OrganizationMapper organizationMapper;

    /**
     * 要求角色(乙方)
     * 先决条件(项目处于 "执行中" 状态)
     * 可容忍的条件-处理返回HTTP请求的情况(项目处于 "完成"、"超时完成"、"已验收" 中任意一个)
     * 注意：更新数据库数据时，项目进度状态不能倒转，比如"已验收"的进度任务不该被修改成"完成"
     *
     * @param req 包装的请求类
     * @return dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProgressDTO complete(ProgressCompleteReq req) {
        log.info("ProgressServiceImpl -> complete -> ProgressCompleteReq req : {}", req);
        ProgressDTO progressDTO = new ProgressDTO();
        Long memberId = req.getMemberId();
        Long progressId = req.getProgressId();
        ProgressValidate progressValidate = ProgressValidate.builder()
                .memberId(memberId)
                .progressId(progressId)
                .role(ProgressEnum.ROLE_B.getIntValue())
                .preProgressStatus(new Integer[]{ProgressEnum.STATUS_EXECUTING.getIntValue()})
                .allowdProgressStatus(new Integer[]{ProgressEnum.STATUS_COMPLETED.getIntValue(), ProgressEnum.STATUS_OVERTIME.getIntValue(), ProgressEnum.STATUS_ACCEPTED.getIntValue()})
                .build();
        ProgressValidate validate = validate(progressValidate);
        Long contractId = validate.getContractId();
        Integer status = validate.getProgressStatus();
        LocalDateTime endTime = validate.getEndTime();
        boolean missingRequiredParameters = ObjectUtil.isNull(contractId) || ObjectUtil.isNull(status) || ObjectUtil.isNull(endTime);
        log.info("ProgressServiceImpl -> complete -> boolean missingRequiredParameters : {}", missingRequiredParameters);
        if (missingRequiredParameters) {
            progressDTO.setSuccess(false);
            progressDTO.setCodeMessageEnum(ProgressCodeMessageEnum.EXCEPTION_HX000_000007);
            log.info("ProgressServiceImpl -> complete -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        LocalDateTime doneTime = LocalDateTime.now();
        Progress updateProgress = Progress.builder()
                .updateBy(memberId)
                .updateTime(doneTime)
                .doneTime(doneTime)
                .build();
        boolean isOvertime = doneTime.isAfter(endTime);
        log.info("ProgressServiceImpl -> complete -> boolean isOvertime : {}", isOvertime);
        if (isOvertime) {
            updateProgress.setStatus(ProgressEnum.STATUS_OVERTIME.getIntValue());
        } else {
            updateProgress.setStatus(ProgressEnum.STATUS_COMPLETED.getIntValue());
        }
        Progress criteriaProgress = Progress.builder()
                .id(progressId)
                .status(status)
                .delFlag((byte) ProgressEnum.DELETE_FLAG_NO.getIntValue())
                .build();
        progressDTO = updateByExampleSelective(updateProgress, criteriaProgress);
        log.info("ProgressServiceImpl -> complete -> Return -> ProgressDTO : {}", progressDTO);
        return progressDTO;
    }

    /**
     * 要求角色(甲方)
     * 先决条件(项目处于 "完成"、"超时完成" 中的任意一个)
     * 可容忍的条件-处理返回HTTP请求的情况(项目处于 "已验收" 状态)
     * 注意：更新数据库数据时，项目进度状态不能倒转，比如"已验收"的进度任务不该被修改成"完成" (当然这里不存在该情况)
     * 注意：如果"验收"了所有的项目后，需更新合同的状态为"进度已完成" (更新是否成功的状态不做保存)
     * (这里认为项目进度任务直往前走，不会有回退的情况发生)
     *
     * @param req 包装的请求类
     * @return dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProgressDTO accept(ProgressAcceptReq req) {
        log.info("ProgressServiceImpl -> accept -> ProgressAcceptReq req : {}", req);
        ProgressDTO progressDTO = new ProgressDTO();
        Long memberId = req.getMemberId();
        Long progressId = req.getProgressId();
        ProgressValidate progressValidate = ProgressValidate.builder()
                .memberId(memberId)
                .progressId(progressId)
                .role(ProgressEnum.ROLE_A.getIntValue())
                .preProgressStatus(new Integer[]{ProgressEnum.STATUS_COMPLETED.getIntValue(), ProgressEnum.STATUS_OVERTIME.getIntValue()})
                .allowdProgressStatus(new Integer[]{ProgressEnum.STATUS_ACCEPTED.getIntValue()})
                .build();
        ProgressValidate validate = validate(progressValidate);
        Long contractId = validate.getContractId();
        Integer status = validate.getProgressStatus();
        boolean missingRequiredParameters = ObjectUtil.isNull(contractId) || ObjectUtil.isNull(status);
        log.info("ProgressServiceImpl -> accept -> boolean missingRequiredParameters : {}", missingRequiredParameters);
        if (missingRequiredParameters) {
            progressDTO.setSuccess(false);
            progressDTO.setCodeMessageEnum(ProgressCodeMessageEnum.EXCEPTION_HX000_000007);
            log.info("ProgressServiceImpl -> accept -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        LocalDateTime checkTime = LocalDateTime.now();
        Progress updateProgress = Progress.builder()
                .status(ProgressEnum.STATUS_ACCEPTED.getIntValue())
                .updateBy(memberId)
                .updateTime(checkTime)
                .checkTime(checkTime)
                .build();
        Progress criteriaProgress = Progress.builder()
                .id(progressId)
                .status(status)
                .delFlag((byte) ProgressEnum.DELETE_FLAG_NO.getIntValue())
                .build();
        progressDTO = updateByExampleSelective(updateProgress, criteriaProgress);
        ProgressExample progressExample = new ProgressExample();
        ProgressExample.Criteria progressCriteria = progressExample.createCriteria();
        progressCriteria.andContractIdEqualTo(contractId);
        progressCriteria.andDelFlagEqualTo((byte) ProgressEnum.DELETE_FLAG_NO.getIntValue());
        progressCriteria.andStatusNotEqualTo(ProgressEnum.STATUS_ACCEPTED.getIntValue());
        long notAcceptedCount = progressMapper.countByExample(progressExample);
        boolean allAccepted = notAcceptedCount == 0L;
        log.info("ProgressServiceImpl -> accept -> boolean allAccepted : {}", allAccepted);
        if (allAccepted) {
            Contract contract = Contract.builder()
                    .updateBy(memberId)
                    .status(ContractEnum.STATUS_COMPLETED.getValue())
                    .build();
            ContractExample contractExample = new ContractExample();
            ContractExample.Criteria contractCtiteria = contractExample.createCriteria();
            contractCtiteria.andIdEqualTo(contractId);
            contractCtiteria.andStatusEqualTo(ContractEnum.STATUS_SIGNED.getValue());
            contractCtiteria.andDelFlagEqualTo((byte) ContractEnum.DELETE_FLAG_NO.getValue());
            contractMapper.updateByExampleSelective(contract, contractExample);
        }
        log.info("ProgressServiceImpl -> accept -> Return -> ProgressDTO : {}", progressDTO);
        return progressDTO;
    }

    @Override
    public ProgressDTO detail(ProgressDetailReq req) {
        log.info("ProgressServiceImpl -> detail -> ProgressDetailReq req : {}", req);
        ProgressDTO progressDTO = new ProgressDTO();
        Long memberId = req.getMemberId();
        Long progressId = req.getProgressId();
        Progress progress = getProgressById(progressId);
        Long contractId = progress.getContractId();
        Contract contract = getContractById(contractId);
        Long memberIdPartyA = contract.getMemberIdPartyA();
        Long memberIdPartyB = contract.getMemberIdPartB();
        boolean isA = memberId.equals(memberIdPartyA);
        boolean noPermitted = !isA && !memberId.equals(memberIdPartyB);
        log.info("ProgressServiceImpl -> detail -> boolean noPermitted : {}", noPermitted);
        if (noPermitted) {
            BusinessException businessException = new BusinessException(ProgressCodeMessageEnum.FAILED_LXAPI_000001);
            log.info("ProgressServiceImpl -> validate -> Throw -> BusinessException businessException.code : {} , BusinessException businessException.message : {}", businessException.getCode(), businessException.getMessage());
            throw businessException;
        }
        Long organizationIdA = contract.getOrganizationIdPartyA();
        Long organizationIdB = contract.getOrganizationIdPartyB();
        Member memberA = getMemberById(memberIdPartyA);
        Member memberB = getMemberById(memberIdPartyB);
        Organization organizationA = getOrganizationById(organizationIdA);
        Organization organizationB = getOrganizationById(organizationIdB);
        Integer status = progress.getStatus();
        String title = progress.getTitle();
        String serverTitle = contract.getServerTitle();
        String step = progress.getStep();
        String description = progress.getDescription();
        LocalDateTime beginTime = progress.getBeginTime();
        LocalDateTime endTime = progress.getEndTime();
        LocalDateTime confirmTime = progress.getConfirmTime();
        LocalDateTime doneTime = progress.getDoneTime();
        LocalDateTime checkTime = progress.getCheckTime();
        String organizationNameB = null;
        String organizationNameA = null;
        String memberNameB = null;
        String memberNameA = null;
        boolean organizationBisNotNull = ObjectUtil.isNotNull(organizationB);
        boolean organizationAisNotNull = ObjectUtil.isNotNull(organizationA);
        boolean memberBisNotNull = ObjectUtil.isNotNull(memberB);
        boolean memberAisNotNull = ObjectUtil.isNotNull(memberA);
        log.info("ProgressServiceImpl -> detail -> boolean organizationBisNotNull : {}", organizationBisNotNull);
        if (organizationBisNotNull) {
            organizationNameB = organizationB.getName();
        }
        log.info("ProgressServiceImpl -> detail -> boolean organizationAisNotNull : {}", organizationAisNotNull);
        if (organizationAisNotNull) {
            organizationNameA = organizationA.getName();
        }
        log.info("ProgressServiceImpl -> detail -> boolean memberBisNotNull : {}", memberBisNotNull);
        if (memberBisNotNull) {
            memberNameB = memberB.getName();
        }
        log.info("ProgressServiceImpl -> detail -> boolean memberAisNotNull : {}", memberAisNotNull);
        if (memberAisNotNull) {
            memberNameA = memberA.getName();
        }
        ProgressDetailRes detail = ProgressDetailRes.builder()
                .title(title)
                .serverTitle(serverTitle)
                .step(step)
                .status(status)
                .description(description)
                .beginTime(beginTime)
                .endTime(endTime)
                .confirmTime(confirmTime)
                .doneTime(doneTime)
                .checkTime(checkTime)
                .organizationB(organizationNameB)
                .memberNameB(memberNameB)
                .organizationA(organizationNameA)
                .memberNameA(memberNameA)
                .build();
        progressDTO.setDetail(detail);
        log.info("ProgressServiceImpl -> detail -> Return -> ProgressDTO : {}", progressDTO);
        return progressDTO;
    }

    @Override
    public ProgressDTO listSteps(ProgressListStepsReq req) {
        log.info("ProgressServiceImpl -> listSteps -> ProgressListStepsReq req : {}", req);
        ProgressDTO progressDTO = new ProgressDTO();
        Long memberId = req.getMemberId();
        Long contractId = req.getContractId();
        Contract contract = customContractMapper.getContract(contractId, memberId, (byte) ContractEnum.DELETE_FLAG_NO.getValue());
        boolean contractIsNull = ObjectUtil.isNull(contract);
        log.info("ProgressServiceImpl -> listSteps -> boolean contractIsNull : {}", contractIsNull);
        if (contractIsNull) {
            log.info("ProgressServiceImpl -> listSteps -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        ProgressExample progressExample = new ProgressExample();
        ProgressExample.Criteria progressCriteria = progressExample.createCriteria();
        progressCriteria.andContractIdEqualTo(contractId);
        progressCriteria.andDelFlagEqualTo((byte) ProgressEnum.DELETE_FLAG_NO.getIntValue());
        List<Progress> progressList = progressMapper.selectByExample(progressExample);
        boolean progressListIsEmpty = ObjectUtil.isEmpty(progressList);
        if (progressListIsEmpty) {
            log.info("ProgressServiceImpl -> listSteps -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        List<ProgressStepRes> progressStepResList = new ArrayList<>(progressList.size());
        for (Progress progress : progressList) {
            ProgressStepRes stepRes = ProgressStepRes.builder()
                    .id(progress.getId())
                    .beginTime(progress.getBeginTime())
                    .endTime(progress.getEndTime())
                    .step(progress.getStep())
                    .description(progress.getDescription())
                    .build();
            progressStepResList.add(stepRes);
        }
        progressDTO.setStepList(progressStepResList);
        log.info("ProgressServiceImpl -> listSteps -> Return -> ProgressDTO : {}", progressDTO);
        return progressDTO;
    }

    /**
     * 要求角色(乙方)
     * 先决条件(项目处于 "未确认")
     * 可容忍的条件-处理返回HTTP请求的情况(项目处于 "已确认待完成"、"已完成待验收"、"已验收"、"超时完成" 状态)
     * 注意：更新数据库数据(合同从"草稿"=>"已确定合同(进度已确定)")时，项目进度状态不能倒转，比如"已验收"的进度任务不该被修改成"已确定合同(进度已确定)"
     * (这里认为项目进度任务直往前走，不会有回退的情况发生)
     * 注意：这里是先更新 contract，再更新progress(如果后续需要改动再说)
     * <p>
     * 要求：
     * (1)合同存在
     * (2)是乙方
     * (3)合同status是"草稿" (反复操作直接返回即可，不用再往下进行update了)
     *
     * @param req 包装的请求类
     * @return dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProgressDTO confirm(ProgressComfirmReq req) {
        log.info("ProgressServiceImpl -> confirm -> ProgressAcceptReq req : {}", req);
        ProgressDTO progressDTO = new ProgressDTO();
        Long memberId = req.getMemberId();
        Long contractId = req.getContractId();
        Contract contract = customContractMapper.getContract(contractId, memberId, (byte) ContractEnum.DELETE_FLAG_NO.getValue());
        boolean contractIsNull = ObjectUtil.isNull(contract);
        log.info("ProgressServiceImpl -> confirm -> boolean contractIsNull : {}", contractIsNull);
        if (contractIsNull) {
            progressDTO.setSuccess(false);
            progressDTO.setCodeMessageEnum(ProgressCodeMessageEnum.FAILED_LXAPI_000005);
            log.info("ProgressServiceImpl -> confirm -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        Long memberIdB = contract.getMemberIdPartB();
        boolean isNotB = !memberId.equals(memberIdB);
        log.info("ProgressServiceImpl -> confirm -> boolean isNotB : {}", isNotB);
        if (isNotB) {
            progressDTO.setSuccess(false);
            progressDTO.setCodeMessageEnum(ProgressCodeMessageEnum.FAILED_LXAPI_000001);
            log.info("ProgressServiceImpl -> confirm -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        boolean isNotDraft = ContractEnum.STATUS_DRAFT.getValue() != contract.getStatus();
        log.info("ProgressServiceImpl -> confirm -> boolean isNotDraft : {}", isNotB);
        if (isNotDraft) {
            progressDTO.setSuccess(false);
            progressDTO.setCodeMessageEnum(ProgressCodeMessageEnum.FAILED_LXAPI_000003);
            log.info("ProgressServiceImpl -> confirm -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractCriteria = contractExample.createCriteria();
        contractCriteria.andIdEqualTo(contractId);
        contractCriteria.andMemberIdPartBEqualTo(memberId);
        contractCriteria.andStatusEqualTo(ContractEnum.STATUS_DRAFT.getValue());
        contractCriteria.andDelFlagEqualTo((byte) ContractEnum.DELETE_FLAG_NO.getValue());
        LocalDateTime updateTime = LocalDateTime.now();
        Contract updateContract = Contract.builder()
                .updateBy(memberId)
                .updateTime(updateTime)
                .confirmTime(updateTime)
                .status(ContractEnum.STATUS_CONFIRMED.getValue())
                .build();
        int updateContractCount = contractMapper.updateByExampleSelective(updateContract, contractExample);
        ProgressExample progressExample = new ProgressExample();
        ProgressExample.Criteria progressCriteria = progressExample.createCriteria();
        progressCriteria.andContractIdEqualTo(contractId);
        progressCriteria.andStatusEqualTo(ProgressEnum.STATUS_UNCONFIRMED.getIntValue());
        progressCriteria.andDelFlagEqualTo((byte) ProgressEnum.DELETE_FLAG_NO.getIntValue());
        Progress updateProgress = Progress.builder()
                .updateBy(memberId)
                .updateTime(updateTime)
                .confirmTime(updateTime)
                .status(ProgressEnum.STATUS_EXECUTING.getIntValue())
                .build();
        int updateProgressCount = progressMapper.updateByExampleSelective(updateProgress, progressExample);
        int updateCount = updateContractCount + updateProgressCount;
        boolean updateSuccess = updateCount > 0;
        if (updateSuccess) {
            progressDTO.setSuccess(updateSuccess);
            progressDTO.setSqlCount(updateCount);
            log.info("ProgressServiceImpl -> confirm -> Return -> ProgressDTO : {}", progressDTO);
            return progressDTO;
        }
        progressDTO.setCodeMessageEnum(ProgressCodeMessageEnum.FAILED_LXAPI_000003);
        log.info("ProgressServiceImpl -> confirm -> Return -> ProgressDTO : {}", progressDTO);
        return progressDTO;
    }

    /**
     * 验证指定用户是否是指定项目的甲方(验收项目)/乙方(完成项目)，当前状态是否能修正项目进度状态。
     * 注意：进度状态之间存在先后关系，比如未确认的项目，乙方不能够触发"完成"，只有"已确认仍执行中"的任务能够被"完成"
     * eg：乙方不能验收项目；乙方未确认合同时不能完成任务...
     * (1) 校验数据库里数据是否存在
     * (2) 校验角色是否符合(甲方/乙方， eg：只有乙方能"完成"，甲方能"验收")
     * (3) 校验是否重复请求(eg: 现存项目进度已经是 "完成"、"超时完成"、"已验收"中的一个，但是仍然HTTP请求 "完成")
     * (4) 校验原本项目进度是否符合预期(eg："完成" 之前应该是 "执行中")
     *
     * @param validate 待验证信息
     * @return 是否为甲方/乙方
     */
    private ProgressValidate validate(ProgressValidate validate) {
        log.info("ProgressServiceImpl -> validate -> ProgressValidate validate : {}", validate);
        Long progressId = validate.getProgressId();
        Progress progress = getProgressById(progressId);
        Long contractId = progress.getContractId();
        Contract contract = getContractById(contractId);
        Long memberId = validate.getMemberId();
        Integer role = validate.getRole();
        boolean lackRoleA = role.equals(ProgressEnum.ROLE_A.getIntValue()) && !memberId.equals(contract.getMemberIdPartyA());
        boolean lackRoleB = role.equals(ProgressEnum.ROLE_B.getIntValue()) && !memberId.equals(contract.getMemberIdPartB());
        log.info("ProgressServiceImpl -> validate -> boolean lackRoleA : {} , lackRoleB : {}", lackRoleA, lackRoleB);
        if (lackRoleA || lackRoleB) {
            BusinessException businessException = new BusinessException(ProgressCodeMessageEnum.FAILED_LXAPI_000001);
            log.info("ProgressServiceImpl -> validate -> Throw -> BusinessException businessException.code : {} , BusinessException businessException.message : {}", businessException.getCode(), businessException.getMessage());
            throw businessException;
        }
        Integer progressStatus = progress.getStatus();
        boolean isRepeatedRequest = ArrayUtil.contains(validate.getAllowdProgressStatus(), progressStatus);
        log.info("ProgressServiceImpl -> validate -> boolean isRepeatedRequest : {}", isRepeatedRequest);
        if (isRepeatedRequest) {
            BusinessException businessException = new BusinessException(ProgressCodeMessageEnum.FAILED_LXAPI_000003);
            log.info("ProgressServiceImpl -> validate -> Throw -> BusinessException businessException.code : {} , BusinessException businessException.message : {}", businessException.getCode(), businessException.getMessage());
            throw businessException;
        }
        boolean lackPreProgressStatus = !ArrayUtil.contains(validate.getPreProgressStatus(), progressStatus);
        log.info("ProgressServiceImpl -> validate -> boolean lackPreProgressStatus : {}", lackPreProgressStatus);
        if (lackPreProgressStatus) {
            BusinessException businessException = new BusinessException(ProgressCodeMessageEnum.FAILED_LXAPI_000002);
            log.info("ProgressServiceImpl -> validate -> Throw -> BusinessException businessException.code : {} , BusinessException businessException.message : {}", businessException.getCode(), businessException.getMessage());
            throw businessException;
        }
        validate.setProgressStatus(progress.getStatus());
        validate.setContractId(contractId);
        validate.setEndTime(progress.getEndTime());
        validate.setProgressStatus(progressStatus);
        log.info("ProgressServiceImpl -> validate -> Return -> boolean validate : {}", validate);
        return validate;
    }

    /**
     * 通用的updateByExampleSelective方法
     *
     * @param updateProgress   用于修改的参数
     * @param criteriaProgress 用于修改的条件
     * @return 是否成功+影响的元组数量
     */
    private ProgressDTO updateByExampleSelective(Progress updateProgress, Progress criteriaProgress) {
        log.info("ProgressServiceImpl -> updateByExampleSelective -> Progress updateProgress : {} , Progress criteriaProgress : {} ", updateProgress, criteriaProgress);
        ProgressDTO dto;
        ProgressExample progressExample = new ProgressExample();
        ProgressExample.Criteria criteria = progressExample.createCriteria();
        Long progressId = criteriaProgress.getId();
        boolean progressIdIsNotNull = ObjectUtil.isNotNull(progressId);
        log.info("ProgressServiceImpl -> updateByExampleSelective -> boolean progressIdIsNotNull : {}", progressIdIsNotNull);
        if (progressIdIsNotNull) {
            criteria.andIdEqualTo(progressId);
        }
        Byte delFlag = criteriaProgress.getDelFlag();
        boolean delFlagIsNotNull = ObjectUtil.isNotNull(delFlag);
        log.info("ProgressServiceImpl -> updateByExampleSelective -> boolean delFlagIsNotNull : {}", delFlagIsNotNull);
        if (delFlagIsNotNull) {
            criteria.andDelFlagEqualTo(delFlag);
        }
        int count = progressMapper.updateByExampleSelective(updateProgress, progressExample);
        boolean updateSuccess = count > 0;
        dto = ProgressDTO.builder()
                .success(updateSuccess)
                .build();
        log.info("ProgressServiceImpl -> updateByExampleSelective -> boolean updateSuccess : {}", updateSuccess);
        if (updateSuccess) {
            dto.setSqlCount(count);
            log.info("ProgressServiceImpl -> updateByExampleSelective -> Return -> ProgressDTO dto : {}", dto);
            return dto;
        }
        dto.setCodeMessageEnum(ProgressCodeMessageEnum.FAILED_LXAPI_000003);
        log.info("ProgressServiceImpl -> updateByExampleSelective -> Return -> ProgressDTO dto : {}", dto);
        return dto;
    }

    /**
     * 根据Id获取"未被逻辑删除"的Progress
     *
     * @param progressId progress的主键
     * @return progress
     */
    private Progress getProgressById(Long progressId) {
        log.info("ProgressServiceImpl -> getProgressById -> Long progressId : {}", progressId);
        ProgressExample progressExample = new ProgressExample();
        ProgressExample.Criteria progressCriteria = progressExample.createCriteria();
        progressCriteria.andIdEqualTo(progressId);
        progressCriteria.andDelFlagEqualTo((byte) ProgressEnum.DELETE_FLAG_NO.getIntValue());
        Progress progress = progressMapper.selectOneByExample(progressExample);
        boolean progressIsNull = ObjectUtil.isNull(progress);
        log.info("ProgressServiceImpl -> getProgressById -> boolean progressIsNull : {}", progressIsNull);
        if (progressIsNull) {
            BusinessException businessException = new BusinessException(ProgressCodeMessageEnum.FAILED_LXAPI_000004);
            log.info("ProgressServiceImpl -> getProgressById -> Throw -> BusinessException businessException.code : {} , BusinessException businessException.message : {}", businessException.getCode(), businessException.getMessage());
            throw businessException;
        }
        log.info("ProgressServiceImpl -> getProgressById -> Return -> Progress progress : {}", progress);
        return progress;
    }

    /**
     * 根据contractId获取"未被逻辑删除"的合同
     *
     * @param contractId 合同主键
     * @return 合同
     */
    private Contract getContractById(Long contractId) {
        log.info("ProgressServiceImpl -> getContractById -> Long contractId : {}", contractId);
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractCriteria = contractExample.createCriteria();
        contractCriteria.andIdEqualTo(contractId);
        contractCriteria.andDelFlagEqualTo((byte) ContractEnum.DELETE_FLAG_NO.getValue());
        Contract contract = contractMapper.selectOneByExample(contractExample);
        boolean contractIsNull = ObjectUtil.isNull(contract);
        log.info("ProgressServiceImpl -> getContractById -> boolean contractIsNull : {}", contractIsNull);
        if (contractIsNull) {
            BusinessException businessException = new BusinessException(ProgressCodeMessageEnum.FAILED_LXAPI_000005);
            log.info("ProgressServiceImpl -> getContractById -> Throw -> BusinessException businessException.code : {} , BusinessException businessException.message : {}", businessException.getCode(), businessException.getMessage());
            throw businessException;
        }
        log.info("ProgressServiceImpl -> getContractById -> Return -> Contract contract : {}", contract);
        return contract;
    }

    /**
     * 根据memberId获取"未被逻辑删除"的用户信息
     *
     * @param memberId 用户主键
     * @return 用户
     */
    private Member getMemberById(Long memberId) {
        log.info("ProgressServiceImpl -> getMemberById -> Long memberId : {}", memberId);
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria memberCriteria = memberExample.createCriteria();
        memberCriteria.andIdEqualTo(memberId);
        memberCriteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        log.info("ProgressServiceImpl -> getMemberById -> Return -> Member member : {}", member);
        return member;
    }

    /**
     * 根据organizationId获取"未被逻辑删除"的组织机构信息
     *
     * @param organizationId 组织机构主键
     * @return 组织机构
     */
    private Organization getOrganizationById(Long organizationId) {
        log.info("ProgressServiceImpl -> getOrganizationById -> Long organizationId : {}", organizationId);
        OrganizationExample organizationExample = new OrganizationExample();
        OrganizationExample.Criteria organizationCriteria = organizationExample.createCriteria();
        organizationCriteria.andIdEqualTo(organizationId);
        organizationCriteria.andDelFlagEqualTo(
                ConstantEnum.NOT_DEL.getByte());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        log.info("ProgressServiceImpl -> getOrganizationById -> Return -> Organization organization : {}", organization);
        return organization;
    }

    /**
     * 根据status返回对应的进度名称(项目完成 、 项目待验收...)
     * 甲方乙方获取的进度名称可能不同，这里做一个判断
     *
     * @param status (进度状态)
     * @param isA    是否甲方(否，则表明是乙方)
     * @return 进度状态名称
     * @deprecated
     */
    private String getTitle(Integer status, boolean isA) {
        log.info("ProgressServiceImpl -> getTitle -> Integer status : {} , boolean isA : {}", status, isA);
        String title = ProgressEnum.TITLE_ERROR.getStrValue();
        boolean STATUS_UNCONFIRMED = status == ProgressEnum.STATUS_UNCONFIRMED.getIntValue();
        boolean STATUS_EXECUTING = status == ProgressEnum.STATUS_EXECUTING.getIntValue();
        boolean STATUS_COMPLETED = status == ProgressEnum.STATUS_COMPLETED.getIntValue();
        boolean STATUS_ACCEPTED = status == ProgressEnum.STATUS_ACCEPTED.getIntValue();
        boolean STATUS_OVERTIME = status == ProgressEnum.STATUS_OVERTIME.getIntValue();
        if (STATUS_UNCONFIRMED) {
            title = isA ? ProgressEnum.TITLE_A_UNCONFIRMED.getStrValue() : ProgressEnum.TITLE_B_UNCONFIRMED.getStrValue();
        }
        if (STATUS_EXECUTING) {
            title = isA ? ProgressEnum.TITLE_A_EXECUTING.getStrValue() : ProgressEnum.TITLE_B_EXECUTING.getStrValue();
        }
        if (STATUS_COMPLETED) {
            title = isA ? ProgressEnum.TITLE_A_COMPLETED.getStrValue() : ProgressEnum.TITLE_B_COMPLETED.getStrValue();
        }
        if (STATUS_ACCEPTED) {
            title = isA ? ProgressEnum.TITLE_A_ACCEPTED.getStrValue() : ProgressEnum.TITLE_B_ACCEPTED.getStrValue();
        }
        if (STATUS_OVERTIME) {
            title = isA ? ProgressEnum.TITLE_A_OVERTIME.getStrValue() : ProgressEnum.TITLE_B_OVERTIME.getStrValue();
        }
        log.info("ProgressServiceImpl -> getTitle -> Return -> String title : {} ", title);
        return title;
    }

}
