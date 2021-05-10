package com.qidao.application.service.search.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.Subscribe;
import com.qidao.application.entity.member.SubscribeExample;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.relation.LogBehaveDynamic;
import com.qidao.application.entity.relation.LogBehaveDynamicExample;
import com.qidao.application.entity.search.SearchDynamic;
import com.qidao.application.entity.search.SearchMember;
import com.qidao.application.mapper.config.CustomSelectConfigMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.member.SubscribeMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.mapper.relation.LogBehaveDynamicMapper;
import com.qidao.application.mapper.search.CustomSearchMapper;
import com.qidao.application.model.common.QiDaoEncodeUtil;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.SelectConfigEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.dynamic.DynamicConstantEnum;
import com.qidao.application.model.dynamic.DynamicLabelRes;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.member.subscribe.SubscribeEnum;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;
import com.qidao.application.model.member.token.TokenConstantEnum;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.model.search.*;
import com.qidao.application.service.search.SearchService;
import com.qidao.framework.service.ServicePage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("searchService")
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private SubscribeMapper subscribeMapper;

    @Resource
    private LogBehaveDynamicMapper logBehaveDynamicMapper;

    @Resource
    private CustomSearchMapper customSearchMapper;

    @Resource
    private CustomMemberMapper customMemberMapper;

    @Resource
    private CustomSelectConfigMapper customSelectConfigMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Resource
    private QiDaoEncodeUtil qiDaoEncodeUtil;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    private Integer searchDynamicMaxLimit = 10;

    /**
     * 获取登录用户id
     *
     * @return
     */
    private Long getLoginMemberId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String accessToken = request.getHeader(TokenConstantEnum.ACCESS_TOKEN_NAME.getValue());
        if (!StrUtil.isBlank(accessToken)) {
            RBucket<String> accessTokenBucket = redissonClient.getBucket(accessToken);
            if (accessTokenBucket.isExists()) {
                String refreshToken = accessTokenBucket.get();
                String jsonStr = qiDaoEncodeUtil.desDecrypt(refreshToken);
                GeneratorRefreshTokenDTO generatorRefresh = JSONUtil.toBean(jsonStr, GeneratorRefreshTokenDTO.class);
                log.info("SearchServiceImpl -> getLoginMemberId --> 登录用户id:{} ", generatorRefresh.getMemberId());
                return generatorRefresh.getMemberId();
            }
        }
        return null;
    }

    private void checkVip() {
        //获取登录会员id
        Long loginMemberId = getLoginMemberId();
        if (loginMemberId == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_LOGIN);
        }
        Member member = memberMapper.selectByPrimaryKey(loginMemberId);
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.DELETE_TRUE);
        }

        LocalDateTime vipStartTime = null;
        LocalDateTime vipEndTime = null;
        //公司 会员时间
        if (member.getOrganizationId() != null) {
            Organization organization = organizationMapper.selectByPrimaryKey(member.getOrganizationId());
            if (organization == null) {
                throw new BusinessException("没有查询到公司信息");
            }
            //类别：0-实验室；1-公司
            if (OrganizationEnum.TYPE_COMPANY.getValue().equals(organization.getType())) {
                vipStartTime = organization.getVipStartTime();
                vipEndTime = organization.getVipEndTime();
                log.info("SearchServiceImpl -> checkVip -> 查询公司vip vipStartTime:{} vipEndTime:{}", vipStartTime, vipEndTime);
            }
        }

        //会员 会员时间
        if (vipStartTime == null && vipEndTime == null) {
            vipStartTime = member.getVipStartTime();
            vipEndTime = member.getVipEndTime();
            log.info("SearchServiceImpl -> checkVip -> 查询会员vip vipStartTime:{} vipEndTime:{}", vipStartTime, vipEndTime);
        }

        if (vipStartTime == null || vipEndTime == null) {
            throw new BusinessException("登录会员不是vip");
        }
        LocalDateTime currentDate = LocalDateTime.now();
        if (vipStartTime.isAfter(currentDate)) {
            log.info("SearchServiceImpl -> checkVip -> vip尚未开始");
            throw new BusinessException("vip尚未开始");
        }
        if (vipEndTime.isBefore(currentDate)) {
            log.info("SearchServiceImpl -> checkVip -> vip已过期");
            throw new BusinessException("vip已过期");
        }
    }

    @Override
    public Object getSearchDynamic(SearchPageReq req) {
        log.info("SearchServiceImpl -> getSearchResult -> start -> param:{}", req);

        //单页数量限制 最大10条
        if (searchDynamicMaxLimit < req.getLimit()) {
            log.info("SearchServiceImpl -> getSearchResult -> 单页数量限制 设置limit为10");
            req.setLimit(searchDynamicMaxLimit);
        }
        //查询第二页时验证会员身份
        if (req.getOffset() > 1) {
            checkVip();
        }

        SubscribeExample blockExample = new SubscribeExample();
        blockExample.createCriteria()
                .andTypeEqualTo(SubscribeEnum.TYPE_BLOCK.getValue())
                .andMemberIdEqualTo(req.getMemberId())
                .andSubscribeTypeEqualTo(SubscribeEnum.SUBSCRIBE_TYPE_ORGANIZATION.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<Subscribe> subscribes = subscribeMapper.selectByExample(blockExample);
        List<Long> memberIds = subscribes.stream().map(Subscribe::getSubscribeId).collect(Collectors.toList());
        Integer type = req.getType();

        if (type.equals(SearchConstantEnum.SEARCH_DYNAMIC.getValue())) {
            ServicePage<List<SearchDynamicPageRes>> res = new ServicePage<>();
            Page<SearchDynamic> page = PageUtil.start(req, () -> customSearchMapper.getSearchDynamic(req.getKeyword(), memberIds));
            BeanUtils.copyProperties(page, res);
            List<SearchDynamic> result = page.getResult();
            log.info("SearchServiceImpl -> getSearchResult -> result != null ：{}", CollUtil.isNotEmpty(result));
            if (CollUtil.isNotEmpty(result)) {
                List<SearchDynamicPageRes> dynamicPageRes = new ArrayList<>();
                result.forEach(searchDynamic -> {
                    SearchDynamicPageRes pageRes = new SearchDynamicPageRes();
                    BeanUtils.copyProperties(searchDynamic, pageRes);
                    pageRes.setPublishTime(LocalDateTimeUtil.format(searchDynamic.getPublishTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                    pageRes.setLikeStatus(DynamicConstantEnum.LIKE_FALSE.getCode());
                    pageRes.setFavorStatus(DynamicConstantEnum.FAVOR_FALSE.getCode());
                    List<String> commenterHeadImages = customMemberMapper.findAgreeHeadImages(pageRes.getDynamicId());
                    pageRes.setAgreeHeadImages(commenterHeadImages);
                    if (StringUtils.isNotEmpty(searchDynamic.getImg())) {
                        pageRes.setImg(Arrays.asList(searchDynamic.getImg().split(",")));
                    }
                    LogBehaveDynamicExample example = new LogBehaveDynamicExample();
                    example.createCriteria()
                            .andSourceIdEqualTo(pageRes.getDynamicId())
                            .andMemberIdEqualTo(req.getMemberId())
                            .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                            .andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_DYNAMIC.getCode());
                    LogBehaveDynamic behaveDynamic = logBehaveDynamicMapper.selectOneByExample(example);
                    log.info("SearchServiceImpl -> getSearchResult -> judge -> behaveDynamic != null :{}", behaveDynamic != null);
                    if (behaveDynamic != null) {
                        pageRes.setLikeStatus(DynamicConstantEnum.LIKE_TRUE.getCode());
                    }
                    SubscribeExample subscribeExample = new SubscribeExample();
                    subscribeExample.createCriteria()
                            .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                            .andMemberIdEqualTo(req.getMemberId())
                            .andTypeEqualTo(SubscribeEnum.TYPE_FOLLOW.getValue())
                            .andSubscribeIdEqualTo(pageRes.getPublisherId());
                    Subscribe subscribe = subscribeMapper.selectOneByExample(subscribeExample);
                    log.info("SearchServiceImpl -> getSearchResult -> judge -> subscribe != null :{}", subscribe != null);
                    if (subscribe != null) {
                        pageRes.setFavorStatus(DynamicConstantEnum.FAVOR_TRUE.getCode());
                    }
                    List<DynamicLabelRes> dynamicLabelRes = convertLabels(searchDynamic.getLabelIds(), searchDynamic.getLabelVals());
                    pageRes.setLabels(dynamicLabelRes);
                    dynamicPageRes.add(pageRes);
                });
                res.setResult(dynamicPageRes);
            }
            log.info("SearchServiceImpl -> getSearchResult -> end -> return :{}", res);
            return res;
        }

        if (type.equals(SearchConstantEnum.SEARCH_MEMBER.getValue())) {
            ServicePage<List<SearchMemberRes>> res = new ServicePage<>();
            Page<SearchMember> page = PageUtil.start(req, () -> customSearchMapper.getSearchMember(req.getKeyword(), memberIds));
            BeanUtils.copyProperties(page, res);
            List<SearchMember> result = page.getResult();
            List<SearchMemberRes> memberResList = new ArrayList<>();
            result.forEach(searchMember -> {
                SearchMemberRes memberRes = new SearchMemberRes();
                BeanUtils.copyProperties(searchMember, memberRes);
                memberRes.setSubscribe(true);
                SubscribeExample subscribeExample = new SubscribeExample();
                subscribeExample.createCriteria()
                        .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                        .andMemberIdEqualTo(req.getMemberId())
                        .andTypeEqualTo(SubscribeEnum.TYPE_FOLLOW.getValue())
                        .andSubscribeIdEqualTo(memberRes.getId());
                Subscribe subscribe = subscribeMapper.selectOneByExample(subscribeExample);
                if (subscribe == null) {
                    memberRes.setSubscribe(false);
                }
                memberResList.add(memberRes);
            });
            res.setResult(memberResList);
            log.info("SearchServiceImpl -> getSearchResult -> end -> return :{}", res);
            return res;
        }
        if (type.equals(SearchConstantEnum.SEARCH_ACHIEVEMENT_EQUIPMENT.getValue())) {
            ServicePage<List<SearchDynamicPageRes>> res = new ServicePage<>();
            Page<SearchDynamic> page = PageUtil.start(req, () -> customSearchMapper.getSearchAchievementEquipment(req.getKeyword(), memberIds));
            BeanUtils.copyProperties(page, res);
            List<SearchDynamic> result = page.getResult();
            List<SearchDynamicPageRes> pageRes = new ArrayList<>();
            result.forEach(searchDynamic -> {
                SearchDynamicPageRes dynamicPageRes = new SearchDynamicPageRes();
                BeanUtils.copyProperties(searchDynamic, dynamicPageRes);
                dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(searchDynamic.getPublishTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                if (StringUtils.isNotEmpty(searchDynamic.getImg())) {
                    dynamicPageRes.setImg(Arrays.asList(searchDynamic.getImg().split(",")));
                }
                List<DynamicLabelRes> dynamicLabelRes = convertLabels(searchDynamic.getLabelIds(), searchDynamic.getLabelVals());
                dynamicPageRes.setLabels(dynamicLabelRes);
                pageRes.add(dynamicPageRes);
            });
            res.setResult(pageRes);
            log.info("SearchServiceImpl -> getSearchResult -> end -> return :{}", res);
            return res;
        }
        log.info("SearchServiceImpl -> getSearchResult -> end -> return :null");
        return null;
        /*ServicePage<List<SearchServerPageRes>> res = new ServicePage<>();
        Page<SearchServer> page = PageUtil.start(req, () -> customSearchMapper.getSearchServer(req.getKeyword()));
        BeanUtils.copyProperties(page , res);
        List<SearchServer> result = page.getResult();
        log.info("SearchServiceImpl -> getSearchResult -> result != null ：{}",CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)){
            List<SearchServerPageRes> searchServerPageRes = new ArrayList<>();
            result.forEach(searchServer -> {
                SearchServerPageRes pageRes = new SearchServerPageRes();
                BeanUtils.copyProperties(searchServer , pageRes);
                List<LinkLabelVo> linkLabelVos = searchServer.getLabels();
                if (CollUtil.isNotEmpty(linkLabelVos)){
                    List<DynamicLabelRes> labelRes = new ArrayList<>();
                    linkLabelVos.forEach(linkLabelVo -> {
                        DynamicLabelRes label = new DynamicLabelRes();
                        BeanUtils.copyProperties(linkLabelVo , label);
                        labelRes.add(label);
                    });
                    pageRes.setLabels(labelRes);
                }
                searchServerPageRes.add(pageRes);
            });
            res.setResult(searchServerPageRes);
        }
        log.info("SearchServiceImpl -> getSearchResult -> end -> return :{}",res);
        return res;*/
    }

    @Override
    public HotSearchRes getHotSearch(Integer topN) {
        List<String> hotSearches = customSelectConfigMapper.getHotSelectConfig(DictConstantEnum.HOT_SEARCH.getId(), HotSearchConstantEnum.HOT_DISPLAY_COUNT.getValue(), SelectConfigEnum.STATUS_ACTIVE.getValue(), ConstantEnum.NOT_DEL.getByte());
        HotSearchRes res = HotSearchRes.builder()
                .values(hotSearches)
                .build();
        log.info("SearchServiceImpl -> getHotSearch -> Return -> HotSearchRes : {}", res);
        return res;
    }

    /**
     * 转换标签
     *
     * @param labelIds
     * @param labelVals
     * @return
     */
    private List<DynamicLabelRes> convertLabels(String labelIds, String labelVals) {
        List<DynamicLabelRes> dynamicLabelRes = new ArrayList<>();
        if (StrUtil.isAllNotEmpty(labelIds, labelVals)) {
            String[] labelIdSplit = labelIds.split(",");
            String[] labelValSplit = labelVals.split(",");
            for (int i = 0; i < labelIdSplit.length; i++) {
                DynamicLabelRes labelRes = new DynamicLabelRes();
                labelRes.setLabelId(Long.parseLong(labelIdSplit[i]));
                labelRes.setVal(labelValSplit[i]);
                dynamicLabelRes.add(labelRes);
            }
        }
        return dynamicLabelRes;
    }
}
