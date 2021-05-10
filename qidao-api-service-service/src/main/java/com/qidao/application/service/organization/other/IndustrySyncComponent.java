package com.qidao.application.service.organization.other;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.entity.relation.LinkSelect;
import com.qidao.application.entity.relation.LinkSelectExample;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.relation.LinkSelectMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 行业同步
 **/
@Component
@Slf4j
public class IndustrySyncComponent {

    @Resource
    private LinkSelectMapper linkSelectMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Resource
    private MemberMapper memberMapper;


    /**
     * 行业同步
     *
     * @param industry       行业id列表
     * @param organizationId 目标id （会员id or 组织机构id）
     */
    public void industrySync(List<Long> industry, Long organizationId) {
        if (CollUtil.isNotEmpty(industry)) {
            //修改组织机构行业id
            updateIndustry(industry, organizationId);
        }
    }

    /**
     * 行业同步
     *
     * @param industry 行业id列表
     * @param sourceId 目标id （会员id or 组织机构id）
     */
    private void updateIndustry(List<Long> industry, Long sourceId) {
        Set<Long> industryIdsSet = new HashSet<>(industry);
        //需要删除的行业
        List<Long> delIndustryIds = new ArrayList<>();

        //删除原有行业
        LinkSelectExample linkSelectExample = new LinkSelectExample();
        linkSelectExample.createCriteria()
                .andSourceIdEqualTo(sourceId)
                .andTypeEqualTo(DictConstantEnum.INDUSTRY.getId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<LinkSelect> linkSelects = linkSelectMapper.selectByExample(linkSelectExample);
        if (CollUtil.isNotEmpty(linkSelects)) {
            //遍历 已经存在的行业
            for (LinkSelect linkSelect : linkSelects) {
                Long selectConfigId = linkSelect.getSelectConfigId();
                //industryIdsSet 需要修改的行业 是否 之前已经存在db中
                if (industryIdsSet.contains(selectConfigId)) {
                    //已经存在 就不需要修改db
                    industryIdsSet.remove(selectConfigId);
                } else { //不存在 需要删除
                    delIndustryIds.add(selectConfigId);
                }
            }
        }

        List<LinkSelect> linkSelectsAdd = new ArrayList<>();
        industryIdsSet.forEach(industryId -> {
            linkSelectsAdd.add(LinkSelect.builder()
                    .id(snowflakeIdWorker53.nextId())
                    .delFlag(ConstantEnum.NOT_DEL.getByte())
                    .sourceId(sourceId)
                    .selectConfigId(industryId)
                    .type(DictConstantEnum.INDUSTRY.getId())
                    .build());
        });
        log.info("IndustrySyncComponent -> updateIndustry -> 修改行业 -> 行业id参数:industry size: {}  行业添加:linkSelectsAdd size: {}   行业删除: delIndustryIds size: {}", industry.size(), linkSelectsAdd.size(), delIndustryIds.size());

        if (CollUtil.isNotEmpty(delIndustryIds)) {
            //删除行业
            linkSelectExample.clear();
            linkSelectExample.createCriteria()
                    .andSourceIdEqualTo(sourceId)
                    .andSelectConfigIdIn(delIndustryIds)
                    .andTypeEqualTo(DictConstantEnum.INDUSTRY.getId())
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            linkSelectMapper.updateByExampleSelective(LinkSelect.builder().delFlag(ConstantEnum.DELETED.getByte()).build(), linkSelectExample);
        }

        //添加新的行业
        if (CollUtil.isNotEmpty(linkSelectsAdd)) {
            linkSelectMapper.batchInsert(linkSelectsAdd);
        }
    }

}
