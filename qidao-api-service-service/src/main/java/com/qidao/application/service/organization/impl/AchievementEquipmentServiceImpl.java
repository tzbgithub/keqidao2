package com.qidao.application.service.organization.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qidao.application.common.Query;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.organization.AchievementEquipment;
import com.qidao.application.entity.organization.AchievementEquipmentExample;
import com.qidao.application.entity.relation.LinkLabel;
import com.qidao.application.entity.relation.LinkSelect;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.AchievementEquipmentMapper;
import com.qidao.application.mapper.organization.CustomAchievementEquipmentMapper;
import com.qidao.application.mapper.relation.CustomLinkLabelMapper;
import com.qidao.application.mapper.relation.LinkLabelMapper;
import com.qidao.application.mapper.relation.LinkSelectMapper;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dto.AchievementEquipmentDto;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.organization.enums.AchievementEquipmentEnum;
import com.qidao.application.model.organization.enums.AchievementEquipmentErrorEnum;
import com.qidao.application.model.organization.enums.OrganizaitonErrorEnum;
import com.qidao.application.service.organization.AchievementEquipmentService;
import com.qidao.application.vo.AchievementListDto;
import com.qidao.application.vo.AchievementListReq;
import com.qidao.application.vo.LinkLabelVo;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AchievementEquipmentServiceImpl implements AchievementEquipmentService {
    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Autowired
    private AchievementEquipmentMapper achievementEquipmentMapper;
    @Autowired
    private LinkLabelMapper linkLabelMapper;
    @Autowired
    private CustomAchievementEquipmentMapper customAchievementEquipmentMapper;
    @Autowired
    private LinkSelectMapper linkSelectMapper;
    @Autowired
    private CustomLinkLabelMapper customLinkLabelMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SelectConfigMapper selectConfigMapper;


    /**
     * 添加实验室设备 / 成果 * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AchievementEquipmentDto achievementEquipmentDto) {

        log.info("----- AchievementEquipmentServiceImpl ---> save ---> start ---> req:{}", achievementEquipmentDto);
        List<Long> types = achievementEquipmentDto.getType();
        if (CollUtil.isEmpty(types)) {
            throw new BusinessException(OrganizaitonErrorEnum.SENDTYPE_AHIECEMENTEQUIPMENT_ERROR);
        }

        byte flag = 0;
        Long memberId = achievementEquipmentDto.getMemberId();
        MemberExample example = new MemberExample();
        example.createCriteria()
                .andIdEqualTo(memberId)
                .andDelFlagEqualTo(MemberEnum.VERIFY_STATUS_CLOSE.getValue().byteValue());
        Member member = memberMapper.selectOneByExample(example);
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        Long organizationId = member.getOrganizationId();
        if (organizationId == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }

        List<String> imgs = achievementEquipmentDto.getImgs();
        String content = achievementEquipmentDto.getContent();
        AchievementEquipment achievementEquipment = new AchievementEquipment();
        int maxLength = 256;
        if (content != null) {
            if (content.length() > maxLength) {
                achievementEquipment.setSummary(content.substring(0, maxLength));
            } else {
                achievementEquipment.setSummary(content);
            }
        }

        long achievementEquipmentId = snowflakeIdWorker53.nextId();
        String achImage = null;
        if (CollUtil.isNotEmpty(imgs)) {
            achImage = imgs.stream().collect(Collectors.joining(","));
        }
        String thumb = achievementEquipmentDto.getThumb();
        if (StringUtils.isBlank(achievementEquipmentDto.getThumb()) &&
                StringUtils.isNotBlank(achievementEquipmentDto.getVideo()) &&
                CollUtil.isEmpty(achievementEquipmentDto.getImgs())) {
            thumb = "video.png";
        }

        achievementEquipment.setId(achievementEquipmentId)
                .setDelFlag(flag)
                .setContent(achievementEquipmentDto.getContent())
                .setImgs(achImage)
                .setOrganizationId(organizationId)
                .setMemberId(memberId)
                .setTitle(achievementEquipmentDto.getTitle())
                .setUrl(achievementEquipmentDto.getUrl())
                .setMaturity(achievementEquipmentDto.getMaturity())
                .setVideo(achievementEquipmentDto.getVideo())
                .setThumb(thumb);
        int dbCount = achievementEquipmentMapper.insertSelective(achievementEquipment);
        if (dbCount != 1) {
            throw new BusinessException(AchievementEquipmentErrorEnum.INSERT_ACHIEVEMENT_EQUIPMENT_ERROR);
        }

        final SnowflakeIdWorker53 snowflakeIdWorker53 = this.snowflakeIdWorker53;

        Long[] labelId = achievementEquipmentDto.getLabelId();
        if (ArrayUtil.isNotEmpty(labelId)) {
            List<LinkLabel> linkLabels = Arrays.stream(labelId).map(id -> {
                LinkLabel linkLabel = new LinkLabel();
                linkLabel.setSourceId(achievementEquipmentId)
                        .setId(snowflakeIdWorker53.nextId())
                        .setLabelId(id)
                        .setDelFlag(flag)
                        .setType(AchievementEquipmentEnum.ACH_PATENT.getValue());
                return linkLabel;
            }).collect(Collectors.toList());
            int i = linkLabelMapper.batchInsertSelective(linkLabels,
                    LinkLabel.Column.sourceId,
                    LinkLabel.Column.id,
                    LinkLabel.Column.labelId,
                    LinkLabel.Column.delFlag,
                    LinkLabel.Column.type);
            if (i != linkLabels.size()) {
                throw new BusinessException(AchievementEquipmentErrorEnum.BATCH_INSERT_LINK_LABEL_ERROR);
            }
            log.info("----- AchievementEquipmentServiceImpl ---> save ---> 标签中间记录批量添加 ---> db.count:{}", i);
        }

        List<LinkSelect> linkSelects = types.stream().map(type -> {
            LinkSelect linkSelect = new LinkSelect();
            linkSelect.setId(snowflakeIdWorker53.nextId())
                    .setCreateTime(LocalDateTime.now())
                    .setDelFlag(flag)
                    .setSourceId(achievementEquipmentId)
                    .setSelectConfigId(type)
                    .setType(13);
            return linkSelect;
        }).collect(Collectors.toList());
        int i = linkSelectMapper.batchInsertSelective(linkSelects,
                LinkSelect.Column.id,
                LinkSelect.Column.createTime,
                LinkSelect.Column.delFlag,
                LinkSelect.Column.sourceId,
                LinkSelect.Column.selectConfigId,
                LinkSelect.Column.type);
        if (i != linkSelects.size()) {
            throw new BusinessException(AchievementEquipmentErrorEnum.BATCH_INSERT_LINK_SELECT_ERROR);
        }
        log.info("----- AchievementEquipmentServiceImpl ---> save ---> 下拉选择中间记录批量添加 ---> db.count:{}", i);

        log.info("----- AchievementEquipmentServiceImpl ---> save ---> end");
        return "发布成功";
    }


    /**
     * 逻辑删除实验室设备 / 成果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateFlag(Long id) {
        byte flag = 1;
        AchievementEquipmentExample achievementEquipmentExample = new AchievementEquipmentExample();
        achievementEquipmentExample.createCriteria().andIdEqualTo(id);
        AchievementEquipment achievementEquipment = achievementEquipmentMapper.selectOneByExample(achievementEquipmentExample);
        if (achievementEquipment == null) {
            throw new BusinessException(OrganizaitonErrorEnum.MESSAGE_AHIECEMENTEQUIPMENT_ERROR);
        }

        log.info("--- AchievementEquipmentContorller ---> updateFlag ---> object not null -----");
        achievementEquipment.setDelFlag(flag);
        int num = achievementEquipmentMapper.updateByExampleWithBLOBs(achievementEquipment, achievementEquipmentExample);
        if (num == 0) {
            throw new BusinessException(OrganizaitonErrorEnum.REQYEST_AHIECEMENTEQUIPMENT_ERROR);
        }
        return "操作成功";
    }

    /**
     * 查询设备/成果列表
     */
    @Override
    public PageInfo<AchievementListDto> findAchievementPage(Integer pageNum, Integer pageSize, Long organizationId, Long type) {
        Query query = new Query();
        query.put("organizationId", String.valueOf(organizationId));
        query.put("type", type);
        PageHelper.startPage(pageNum, pageSize);
        Page<AchievementListReq> list = customAchievementEquipmentMapper.findAchievementPage(query);
        List<AchievementListDto> dto = list.getResult().stream().map(x -> {
            AchievementListDto achievementListDto = new AchievementListDto();
            achievementListDto.setBackendImage(x.getHeadImage())
                    .setCreateTime(x.getCreateTime())
                    .setTitle(x.getTitle())
                    .setId(x.getId())
                    .setMaturity(x.getMaturity())
                    .setOrganizationId(x.getOrganizationId())
                    .setType(x.getType())
                    .setName(x.getOrganizationName())
                    .setSummary(x.getSummary())
                    .setVideo(x.getVideo())
                    .setOrganizationName(x.getOrganizationName())
                    .setUrl(x.getUrl())
                    .setThumb(x.getThumb())
                    .setBelong(x.getBelong())
                    .setContent(x.getContent());

            List<LinkLabelVo> linkLabelVos = customLinkLabelMapper.findLinkLabelBySourceId(x.getId());
            achievementListDto.setLinkLabelVo(linkLabelVos);

            if (StringUtils.isNotBlank(x.getImgs())) {
                List<String> strings = Arrays.asList(x.getImgs().split(","));
                achievementListDto.setImgs(strings);
            }
            SelectConfigExample selectConfigExample = new SelectConfigExample();
            log.info("--- MemberServiceImpl ---> findMemberMessage ----> 学历标识不为空 -----");
            if (x.getEducationId() != null) {
                selectConfigExample.createCriteria().andIdEqualTo(x.getEducationId());
                SelectConfig selectConfig = selectConfigMapper.selectOneByExample(selectConfigExample);
                if (selectConfig != null) {
                    achievementListDto.setEducationName(selectConfig.getVal());
                }
                selectConfigExample.clear();
            }
            log.info("--- MemberServiceImpl ---> findMemberMessage ----> 职位标识不为空 -----");
            if (x.getPositionId() != null) {
                selectConfigExample.createCriteria().andIdEqualTo(x.getPositionId());
                SelectConfig selectConfig = selectConfigMapper.selectOneByExample(selectConfigExample);
                if (selectConfig != null) {
                    achievementListDto.setPositionName(selectConfig.getVal());
                }
            }
            return achievementListDto;
        }).collect(Collectors.toList());

        return new PageInfo<>(dto);
    }


    /**
     * 查询设备/成果详情
     */
    @Override
    public AchievementListDto findAchievementDetail(Long id) {
        byte flag = 0;
        AchievementEquipmentExample example = new AchievementEquipmentExample();
        example.createCriteria().andIdEqualTo(id).andDelFlagEqualTo(flag);
        AchievementEquipment achievementEquipment = achievementEquipmentMapper.selectOneByExampleWithBLOBs(example);
        if (achievementEquipment == null) {
            throw new BusinessException(OrganizaitonErrorEnum.MESSAGE_AHIECEMENTEQUIPMENT_ERROR);
        }
        String imgs = achievementEquipment.getImgs();
        AchievementListDto achievementListDto = new AchievementListDto();
        if (StringUtils.isNotBlank(imgs)) {
            List<String> images = Arrays.asList(imgs.split(","));
            achievementListDto.setImgs(images);
        }
        List<LinkLabelVo> linkLabelVos = customLinkLabelMapper.findLinkLabelBySourceId(achievementEquipment.getId());
        achievementListDto.setLinkLabelVo(linkLabelVos);
        achievementListDto.setSummary(achievementEquipment.getSummary());
        achievementListDto.setOrganizationId(achievementEquipment.getOrganizationId());
        achievementListDto.setTitle(achievementEquipment.getTitle());
        achievementListDto.setType(achievementEquipment.getType());
        achievementListDto.setVideo(achievementEquipment.getVideo());
        achievementListDto.setId(achievementEquipment.getId());
        achievementListDto.setCreateTime(achievementEquipment.getCreateTime());
        achievementListDto.setContent(achievementEquipment.getContent());
        achievementListDto.setThumb(achievementEquipment.getThumb());
        achievementListDto.setUrl(achievementEquipment.getUrl());
        achievementListDto.setMaturity(achievementEquipment.getMaturity());
        return achievementListDto;
    }


}
