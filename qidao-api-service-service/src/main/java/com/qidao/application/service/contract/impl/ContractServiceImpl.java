package com.qidao.application.service.contract.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qidao.application.entity.contract.Contract;
import com.qidao.application.entity.contract.ContractExample;
import com.qidao.application.entity.contract.Progress;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.mapper.contract.ContractMapper;
import com.qidao.application.mapper.contract.CustomContractMapper;
import com.qidao.application.mapper.contract.CustomProgressMapper;
import com.qidao.application.mapper.contract.ProgressMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.CustomOrganizationMapper;
import com.qidao.application.model.common.PdfGeneratorUtil;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.contract.*;
import com.qidao.application.model.contract.progress.ProgressEnum;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;
import com.qidao.application.service.contract.ContractService;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.framework.service.ServicePage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * {
 * 上面是提供给Controller的方法
 * 下面是内部使用的private方法
 * }
 * @date : 2021/1/4 11:21 AM
 */
@Slf4j
@Service("ContractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private MemberMapper memberMapper;


    @Autowired
    @Qualifier("EmailMsgSendImpl")
    private MsgSendService emailMsgSend;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private ProgressMapper progressMapper;

    @Resource
    private CustomContractMapper customContractMapper;

    @Resource
    private CustomProgressMapper customProgressMapper;

    @Resource
    private CustomOrganizationMapper customOrganizationMapper;

    @Override
    public ContractDTO list(ContractListReq req) {
        log.info("ContractServiceImpl -> list -> ContractListReq req : {}", req);
        ContractDTO contractDTO = new ContractDTO();
        Long memberId = req.getMemberId();
        Integer offset = req.getOffset();
        Integer limit = req.getLimit();
        Page<Contract> page = PageHelper.startPage(offset, limit).doSelectPage(() -> customContractMapper.getContractItemList(memberId, (byte) ContractEnum.DELETE_FLAG_NO.getValue()));
        boolean pageIsNull = ObjectUtil.isNull(page);
        log.info("ContractServiceImpl -> list -> boolean pageIsNull : {}", pageIsNull);
        if (pageIsNull) {
            log.info("ContractServiceImpl -> list -> Return -> ContractDTO : {}", contractDTO);
            return contractDTO;
        }
        ServicePage<List<ContractListItemRes>> servicePage = new ServicePage<>();
        servicePage.setEndRow(page.getEndRow());
        servicePage.setPageNum(page.getPageNum());
        servicePage.setPages(page.getPages());
        servicePage.setPageSize(page.getPageSize());
        servicePage.setStartRow(page.getStartRow());
        servicePage.setTotal(page.getTotal());
        List<Contract> contractList = page.getResult();
        int size = contractList.size();
        Long[] contractIds = new Long[size];
        Long[] organizationIds = new Long[size];
        int index = 0;
        for (Contract contract : contractList) {
            contractIds[index] = contract.getId();
            organizationIds[index] = contract.getOrganizationIdPartyA();
            ++index;
        }
        HashMap<Long, Progress> progressHashMap = recentUpdatedProgressList(contractIds);
        HashMap<Long, String> organizationNameMap = getOrganizationNames(organizationIds);
        boolean contractListIsNotNull = ObjectUtil.isNotEmpty(contractList);
        log.info("ContractServiceImpl -> list -> contractListIsNotNull : {}", contractListIsNotNull);
        if (contractListIsNotNull) {
            List<ContractListItemRes> contractListItemResList = new ArrayList<>(size);
            for (Contract contract : contractList) {
                Long contractId = contract.getId();
                Long organizationIdA = contract.getOrganizationIdPartyA();
                int role = memberId.equals(contract.getMemberIdPartyA()) ? 0 : 1;
                Progress progress = progressHashMap.get(contractId);
                String progressStep = ObjectUtil.isNull(progress) ? null : progress.getStep();
                Integer progressStatus = ObjectUtil.isNull(progress) ? null : progress.getStatus();
                String organizationNameA = organizationNameMap.get(organizationIdA);
                ContractListItemRes itemRes = ContractListItemRes.builder()
                        .contractId(contractId)
                        .serverTitle(contract.getServerTitle())
                        .organizationName(organizationNameA)
                        .signTime(contract.getSignTime())
                        .endTime(contract.getEndTime())
                        .progressStep(progressStep)
                        .progressStatus(progressStatus)
                        .contractStatus(contract.getStatus())
                        .role(role)
                        .build();
                contractListItemResList.add(itemRes);
            }
            servicePage.setResult(contractListItemResList);
            contractDTO.setContractList(servicePage);
        }
        log.info("ContractServiceImpl -> list -> Return -> ContractDTO : {}", contractDTO);
        return contractDTO;
    }

    /**
     * 要求角色(乙方)
     * 先决条件(合同处于 "1-已确定合同(项目进度任务已确定)")
     * 可容忍的条件-处理返回HTTP请求的情况(合同处于 "2-已签订合同(合同生效)"、"3-进度已完成" 状态)
     * 注意：更新数据库数据(合同从"草稿"=>"已确定合同(进度已确定)")时，项目进度状态不能倒转，比如"已验收"的进度任务不该被修改成"已确定合同(进度已确定)"
     * (这里认为项目进度任务直往前走，不会有回退的情况发生)
     * 注意：如果条件不满足，状态先于我们需要的前置条件，提示"需要前置阶段"之类的
     * 注意：如果已经处于超前状态("已签订合同"、"进度已完成")，则提示请勿重复操作。
     * <p>
     * 要求：
     * (1)合同存在
     * (2)是乙方
     * (3)合同status是"1-已确定合同(项目进度任务已确定)" (反复操作直接返回即可，不用再往下进行update了)
     *
     * @param req 包装的请求类
     * @return dto
     */
    @Override
    public ContractDTO sign(ContractSignReq req) {
        log.info("ContractServiceImpl -> sign -> ContractListReq req : {}", req);
        ContractDTO contractDTO = new ContractDTO();
        Long memberId = req.getMemberId();
        Long contractId = req.getContractId();
        Contract contract = customContractMapper.getContract(contractId, memberId, (byte) ContractEnum.DELETE_FLAG_NO.getValue());
        boolean contractIsNull = ObjectUtil.isNull(contract);
        log.info("ContractServiceImpl -> sign -> boolean contractIsNull : {}", contractIsNull);
        if (contractIsNull) {
            contractDTO.setSuccess(false);
            contractDTO.setCodeMessageEnum(ContractCodeMessageEnum.FAILED_LXAPI_000002);
            log.info("ContractServiceImpl -> sign -> Return -> ContractDTO : {}", contractDTO);
            return contractDTO;
        }
        Long memberIdB = contract.getMemberIdPartB();
        boolean isNotB = !memberId.equals(memberIdB);
        log.info("ContractServiceImpl -> sign -> boolean isNotB : {}", isNotB);
        if (isNotB) {
            contractDTO.setSuccess(false);
            contractDTO.setCodeMessageEnum(ContractCodeMessageEnum.FAILED_LXAPI_000001);
            log.info("ContractServiceImpl -> sign -> Return -> ContractDTO : {}", contractDTO);
            return contractDTO;
        }
        Integer[] cantSignStatusArr = {ContractEnum.STATUS_DRAFT.getValue()};
        Integer[] hadSignedStatusArr = {ContractEnum.STATUS_SIGNED.getValue(), ContractEnum.STATUS_COMPLETED.getValue()};
        Integer existStatus = contract.getStatus();
        boolean cantSign = ArrayUtil.contains(cantSignStatusArr, existStatus);
        log.info("ContractServiceImpl -> sign -> boolean cantSign : {}", cantSign);
        if (cantSign) {
            contractDTO.setSuccess(false);
            contractDTO.setCodeMessageEnum(ContractCodeMessageEnum.FAILED_LXAPI_000004);
            log.info("ContractServiceImpl -> sign -> Return -> ContractDTO : {}", contractDTO);
            return contractDTO;
        }
        boolean hadSigned = ArrayUtil.contains(hadSignedStatusArr, existStatus);
        log.info("ContractServiceImpl -> sign -> boolean hadSigned : {}", hadSigned);
        if (hadSigned) {
            contractDTO.setSuccess(false);
            contractDTO.setCodeMessageEnum(ContractCodeMessageEnum.FAILED_LXAPI_000003);
            log.info("ContractServiceImpl -> sign -> Return -> ContractDTO : {}", contractDTO);
            return contractDTO;
        }
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractCriteria = contractExample.createCriteria();
        contractCriteria.andIdEqualTo(contractId);
        contractCriteria.andMemberIdPartBEqualTo(memberId);
        contractCriteria.andStatusEqualTo(ContractEnum.STATUS_CONFIRMED.getValue());
        contractCriteria.andDelFlagEqualTo((byte) ContractEnum.DELETE_FLAG_NO.getValue());
        LocalDateTime updateTime = LocalDateTime.now();
        Contract updateContract = Contract.builder()
                .updateBy(memberId)
                .updateTime(updateTime)
                .signTime(updateTime)
                .status(ContractEnum.STATUS_SIGNED.getValue())
                .build();
        int updateCount = contractMapper.updateByExampleSelective(updateContract, contractExample);
        boolean updateSuccess = updateCount > 0;
        if (updateSuccess) {
            contractDTO.setSuccess(updateSuccess);
            contractDTO.setSqlCount(updateCount);
            log.info("ContractServiceImpl -> sign -> Return -> ContractDTO : {}", contractDTO);
            return contractDTO;
        }
        contractDTO.setCodeMessageEnum(ContractCodeMessageEnum.FAILED_LXAPI_000003);
        log.info("ContractServiceImpl -> sign -> Return -> ContractDTO : {}", contractDTO);
        return contractDTO;
    }

    @Override
    public void sendContractToMemberEmail(SendContractToMemberEmailReq req) {
        // 根据会员ID 获取电子邮件地址
        Long memberId = req.getMemberId();

        MemberExample example = new MemberExample();
        example.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(memberId);
        Member member = memberMapper.selectOneByExample(example);
        String email = member.getEmail();

        if(StrUtil.isBlank(email)) {
            throw new BusinessException(ContractCodeMessageEnum.EMAIL_ADDRESS);
        }

        // 根据 合同ID 获取合同信息
        Long contractId = req.getContractId();
        ContractBaseInfoRes contract = getContractBaseInfo(contractId);
        String contractServerTitle = contract.getServerTitle();
        String filename = contractServerTitle + ".pdf";

        // todo apollo 配置 or 服务器文件模板 (Autuan)[3.1]
        String titleTemplate = "【企岛科技】 {} 需求合同附件下载";
        List<String> titleParam = Collections.singletonList(contractServerTitle);
        List<String> titleList = new ArrayList<>();
        titleList.add(titleTemplate);
        titleList.addAll(titleParam);

        // 内容
        String emailContentTemplate = "<html>尊敬的{} ：<br/> 您好！ 这是 {} 需求的合同。 <br/> 谢谢您的使用。 <br/> 企岛科技";
        List<String> contentParam = Arrays.asList(member.getName(),contractServerTitle);
        List<String> contents = new ArrayList<>();
        contents.add(emailContentTemplate);
        contents.addAll(contentParam);

        // 生成 PDF 流
        String contractDetail = generatorContractHtml(contractId);
        InputStream inputStream = PdfGeneratorUtil.generatorInputStream(contractDetail);
        // 发送邮件
        MsgSendDTO param = MsgSendDTO.builder()
                .contentType(MsgSendTypeEnum.CONTRACT_PDF.getVal())
                .title(titleList)
                .contents(contents)
                .receivers(Collections.singletonList(email))
                .attachmentFileName(filename)
                .attachment(inputStream)
                .build();
        emailMsgSend.send(param);
    }

    @Override
    public ContractBaseInfoRes getContractBaseInfo(Long id) {
        ContractExample example = new ContractExample();
        example.createCriteria()
                .andIdEqualTo(id)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Contract contract = contractMapper.selectOneByExample(example);
        ContractBaseInfoRes res = new ContractBaseInfoRes();
        BeanUtils.copyProperties(contract , res);
        return res;
    }

    /**
     * todo 实现 （Autuan)[2.28]
     * 生成 html 标记语言
     * @param contractId
     * @return
     */
    private String generatorContractHtml(Long contractId) {
        // todo apollo set template resource (AUtuan)[2.28]
        String path = "D:\\Temp\\template.html";

        boolean isFile = FileUtil.isFile(path);
        log.info("ContractServiceImpl -> generatorContractHtml -> ifFile -> {}",isFile);

        if(isFile) {
            // todo 填充内容 (Autuan)[2.28]
            return FileUtil.readString(path , StandardCharsets.UTF_8);
        }
        throw new BusinessException("未找到合同模板");
    }

    /**
     * 根据contractId集合获取每个合同最近更新的进度任务信息
     *
     * @param contractIds 合同id集合
     * @return HashMap（合同ID，合同项目进展对象progress）
     */
    private HashMap<Long, Progress> recentUpdatedProgressList(Long[] contractIds) {
        log.info("ContractServiceImpl -> recentUpdatedProgress -> List<Long> contractIds : {}", Arrays.toString(contractIds));
        HashMap<Long, Progress> hashMap;
        List<Progress> progressList = customProgressMapper.getProgressListByContractIds(contractIds, (byte) ProgressEnum.DELETE_FLAG_NO.getIntValue());
        boolean progressListIsEmpty = ObjectUtil.isEmpty(progressList);
        log.info("ContractServiceImpl -> recentUpdatedProgress -> boolean progressListIsEmpty : {}", progressListIsEmpty);
        if (progressListIsEmpty) {
            return new HashMap<>(0);
        }
        int size = progressList.size();
        log.info("ContractServiceImpl -> recentUpdatedProgress -> progressList.size() : {}", size);
        hashMap = new HashMap<>(size);
        for (Progress progress : progressList) {
            hashMap.put(progress.getContractId(), progress);
        }
        return hashMap;
    }

    /**
     * 根据organizationIds数组获取对应的所有组织的组织名
     *
     * @param organizationIds 多份合同对应的甲方组织的id
     * @return HashMap
     */
    private HashMap<Long, String> getOrganizationNames(Long[] organizationIds) {
        log.info("ContractServiceImpl -> getOrganizationNames -> List<Long> organizationIds : {}", Arrays.toString(organizationIds));
        HashMap<Long, String> hashMap;
        List<Organization> organizationList = customOrganizationMapper.listByIds(organizationIds,
                ConstantEnum.NOT_DEL.getByte());
        boolean organizationListIsEmpty = ObjectUtil.isEmpty(organizationList);
        log.info("ContractServiceImpl -> recentUpdatedProgress -> boolean organizationListIsEmpty : {}", organizationListIsEmpty);
        if (organizationListIsEmpty) {
            return new HashMap<>(0);
        }
        int size = organizationList.size();
        log.info("ContractServiceImpl -> getOrganizationNames -> organizationList.size() : {}", size);
        hashMap = new HashMap<>(size);
        for (Organization organization : organizationList) {
            hashMap.put(organization.getId(), organization.getName());
        }
        return hashMap;
    }
}
