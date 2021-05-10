package com.qidao.application.service.dynamic.dynamic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.qidao.application.common.Query;
import com.qidao.application.entity.comment.Comment;
import com.qidao.application.entity.comment.CommentExample;
import com.qidao.application.entity.dynamic.*;
import com.qidao.application.entity.member.*;
import com.qidao.application.entity.relation.*;
import com.qidao.application.mapper.comment.CommentMapper;
import com.qidao.application.mapper.dynamic.CustomDynamicMapper;
import com.qidao.application.mapper.dynamic.DynamicMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.FavorMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.member.SubscribeMapper;
import com.qidao.application.mapper.relation.*;
import com.qidao.application.model.common.QiDaoEncodeUtil;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.dynamic.*;
import com.qidao.application.model.label.LinkLabelEnum;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.member.assistant.AssistantBaseInfoDTO;
import com.qidao.application.model.member.favor.FavorPageReq;
import com.qidao.application.model.member.favor.FavorPageRes;
import com.qidao.application.model.member.subscribe.SubscribeEnum;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;
import com.qidao.application.model.member.token.TokenConstantEnum;
import com.qidao.application.service.dynamic.dynamic.DynamicService;
import com.qidao.application.service.event.PublishEventComponent;
import com.qidao.application.service.event.dynamic.DynamicAgreeEvent;
import com.qidao.application.vo.DynamicPageVo;
import com.qidao.application.vo.SelectConfigResp;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("dynamicService")
@Slf4j
public class DynamicServiceImpl implements DynamicService {

    @Resource
    private LinkDynamicChannelMapper linkDynamicChannelMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private QiDaoEncodeUtil qiDaoEncodeUtil;

    private final CustomDynamicMapper customDynamicMapper;

    @Autowired
    private PublishEventComponent publishEventComponent;

    @Resource
    private SubscribeMapper subscribeMapper;

    @Resource
    private LinkLabelMapper linkLabelMapper;

    @Resource
    private LogBehaveDynamicMapper logBehaveDynamicMapper;

    @Resource
    private FavorMapper favorMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Resource(name = "DynamicRedisson")
    private final RedissonClient redissonClient;
    @Resource
    private LinkSelectMapper linkSelectMapper;

    @Resource
    private CustomMemberMapper customMemberMapper;

    @Resource
    private LinkPublishContentMapper linkPublishContentMapper;


    private static final long hotDynamicExpireTime = 5 * 60 + RandomUtils.nextInt(1, 60);

    private static final String hotDynamicKeyPrefix = "dynamic::hot::memberId::";

    public DynamicServiceImpl(@Autowired CustomDynamicMapper customDynamicMapper, @Autowired RedissonClient redissonClient) {
        this.customDynamicMapper = customDynamicMapper;
        this.redissonClient = redissonClient;

//        refreshRecommendCache(LocalDateTime.now());
    }

    /**
     * 发布动态
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean pushDynamic(DynamicPushReq req) {
        log.info("DynamicServiceImpl -> pushDynamic -> start -> param: {}", req);
        String video = req.getVideo();
        String img = "";
        if (CollUtil.isNotEmpty(req.getImg())) {
            img = String.join(",", req.getImg());
        }
        String thumb = req.getThumb();
        String url = req.getUrl();
        String content = req.getContent();
        MemberInfo member = customMemberMapper.getMemberByMemberId(req.getMemberId());
        log.info("DynamicServiceImpl -> pushDynamic -> 判断用户是否存在 -> {}", ObjectUtil.isEmpty(member));
        if (ObjectUtil.isEmpty(member)) {
            log.error("DynamicServiceImpl -> pushDynamic -> error -> {}", MemberExceptionEnum.NOT_FOUND_PERSONAL.getMessage());
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        log.info("DynamicServiceImpl -> pushDynamic -> 判断当前用户角色不是实验室 -> result:{}", member.getOrganizationType() != 0);
        if (member.getOrganizationType() != 0) {
            log.error("DynamicServiceImpl -> pushDynamic -> 只有实验室可以发布动态");
            throw new BusinessException(DynamicExceptionEnum.ONLY_LABORATORY);
        }
        Boolean imgVideo = StringUtils.isNotEmpty(video) && StringUtils.isNotEmpty(img);
        log.info("DynamicServiceImpl -> pushDynamic -> imgVideo:{}", imgVideo);
        if (imgVideo) {
            log.error("DynamicServiceImpl -> pushDynamic -> 视频和图片只能选择一项添加");
            throw new BusinessException(DynamicExceptionEnum.VIDEO_OR_IMG);
        }
        Boolean imgOrVideoThumb = (StringUtils.isNotEmpty(video) || StringUtils.isNotEmpty(img)) && StringUtils.isEmpty(thumb);
        log.info("DynamicServiceImpl -> pushDynamic -> imgOrVideoThumb:{}", imgOrVideoThumb);
        if (imgOrVideoThumb) {
            log.error("DynamicServiceImpl -> pushDynamic -> 带有视频或图片必须添加封面");
            throw new BusinessException(DynamicExceptionEnum.VIDEO_OR_IMG_THUMB);
        }
        Boolean contentNoThumb = (StringUtils.isEmpty(video) && StringUtils.isEmpty(img) && StringUtils.isEmpty(url) && StringUtils.isNotEmpty(content)) && StringUtils.isNotEmpty(thumb);
        log.info("DynamicServiceImpl -> pushDynamic -> contentNoThumb:{}", contentNoThumb);
        if (contentNoThumb) {
            log.error("DynamicServiceImpl -> pushDynamic -> 纯文字内容不需要添加封面");
            throw new BusinessException(DynamicExceptionEnum.CONTENT_NOT_THUMB);
        }
        Long dynamicId = snowflakeIdWorker53.nextId();
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(req, dynamic);
        dynamic.setId(dynamicId);
        dynamic.setImg(img);
        dynamic.setDelFlag(ConstantEnum.NOT_DEL.getByte());
        dynamic.setVerifyStatus(DynamicConstantEnum.VERIFY_STATUS_INIT.getCode());
        dynamic.setPublishTime(LocalDateTime.now());
        if (StringUtils.isNotEmpty(content) && content.length() > 63) {
            dynamic.setSummary(req.getContent().substring(0, 63));
        } else {
            dynamic.setSummary(req.getContent());
        }

        List<SelectConfigResp> memberIndustryList = customMemberMapper.getMemberIndustry(req.getMemberId());
        List<LinkSelect> linkSelectList = new ArrayList<>();
        memberIndustryList.stream().filter(Objects::nonNull).forEach(memberIndustry -> {
            LinkSelect linkSelect = LinkSelect.builder()
                    .id(snowflakeIdWorker53.nextId())
                    .sourceId(dynamic.getId())
                    .type(DictConstantEnum.INDUSTRY.getId())
                    .createTime(LocalDateTime.now())
                    .delFlag(ConstantEnum.NOT_DEL.getByte())
                    .selectConfigId(memberIndustry.getId())
                    .build();
            linkSelectList.add(linkSelect);
        });
        if(CollUtil.isNotEmpty(linkSelectList)) {
            linkSelectMapper.batchInsert(linkSelectList);
        }
        boolean isAssistant = member.getTeacherId() != null && member.getRole().equals(MemberEnum.ROLE_ASSISTANT.getValue());
        log.info("DynamicServiceImpl -> pushDynamic -> 判断发布者是否为助手 -> {}", isAssistant);
        if (isAssistant) {
            dynamic.setMemberId(member.getTeacherId());
            linkPublishContentMapper.insertSelective(LinkPublishContent.builder().publishId(dynamicId)
                    .type(DynamicConstantEnum.REPLACE_TYPE_DYNAMIC.getCode())
                    .publishType(DynamicConstantEnum.PUBLISH_ASSISTANT.getCode())
                    .salesmanId(req.getMemberId())
                    .memberId(member.getTeacherId())
                    .build());
        }

        List<LinkLabel> linkLabels = new ArrayList<>();
        for (Long labelId : req.getLabels()) {
            LinkLabel linkLabel = LinkLabel.builder()
                    .delFlag(ConstantEnum.NOT_DEL.getByte())
                    .createTime(LocalDateTime.now())
                    .delFlag(ConstantEnum.NOT_DEL.getByte())
                    .id(snowflakeIdWorker53.nextId())
                    .labelId(labelId)
                    .sourceId(dynamic.getId())
                    .type(LinkLabelEnum.LINK_LABEL_DYNAMIC.getCode())
                    .build();
            linkLabels.add(linkLabel);
        }
        linkLabelMapper.batchInsert(linkLabels);

        List<LinkSelect> linkSelects = new ArrayList<>();
        for (Long article : req.getArticles()) {
            LinkSelect linkSelect = LinkSelect.builder()
                    .id(snowflakeIdWorker53.nextId())
                    .createTime(LocalDateTime.now())
                    .delFlag(ConstantEnum.NOT_DEL.getByte())
                    .type(DynamicConstantEnum.SELECT_CONFIG_ARTICLE.getCode())
                    .sourceId(dynamic.getId())
                    .selectConfigId(article)
                    .build();
            linkSelects.add(linkSelect);
        }
        linkSelectMapper.batchInsert(linkSelects);
        log.info("DynamicServiceImpl -> pushDynamic -> end");
        return dynamicMapper.insertSelective(dynamic) > 0;
    }

    /**
     * 分页获取动态列表
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<DynamicPageRes>> getDynamic(DynamicPageReq req) {
        log.info("DynamicServiceImpl -> getDynamic -> start -> param :{}", req);
//        LinkDynamicChannelExample linkDynamicChannelExample = new LinkDynamicChannelExample();
//        linkDynamicChannelExample.createCriteria()
//                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
//                .andChannelIdEqualTo(req.getChannelId());
//        List<LinkDynamicChannel> linkDynamicChannels = linkDynamicChannelMapper.selectByExample(linkDynamicChannelExample);
//        List<Long> channelDynamicIds = linkDynamicChannels.stream().map(LinkDynamicChannel::getDynamicId).collect(Collectors.toList());
//        log.info("DynamicServiceImpl -> getDynamic -> channelDynamicIds == null :{}", CollUtil.isEmpty(channelDynamicIds));
//        if (CollUtil.isEmpty(channelDynamicIds)) {
//            log.info("DynamicServiceImpl -> getDynamic -> end -> return: null");
//            return null;
//        }
        SubscribeExample subscribeExample = new SubscribeExample();
        subscribeExample.createCriteria().andTypeEqualTo(SubscribeEnum.TYPE_BLOCK.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andMemberIdEqualTo(req.getMemberId());
        List<Subscribe> subscribes = subscribeMapper.selectByExample(subscribeExample);
        List<Long> subscribeIds = subscribes.stream()
                .map(Subscribe::getSubscribeId)
                .collect(Collectors.toList());
        DynamicPageVo dynamicPageVo = new DynamicPageVo();
        dynamicPageVo.setChannelId(req.getChannelId());
        dynamicPageVo.setDynamicIds(null);
        dynamicPageVo.setSubscribeIds(subscribeIds);
        dynamicPageVo.setIndustryId(req.getIndustryId());
        Page<DynamicFollow> page = PageUtil.start(req, () -> customDynamicMapper.getDynamicPage(dynamicPageVo));
        ServicePage<List<DynamicPageRes>> dynamicPage = new ServicePage<>();
        BeanUtils.copyProperties(page, dynamicPage);
        List<DynamicFollow> result = page.getResult();
        List<DynamicPageRes> dynamicPageResList = new ArrayList<>();
        log.info("DynamicServiceImpl -> getDynamic -> result != null :{}", CollUtil.isNotEmpty(result));
        List<Long> dynamicIds = result.stream().map(DynamicFollow::getDynamicId).collect(Collectors.toList());
        List<Long> publisherIds = result.stream().map(DynamicFollow::getPublisherId).collect(Collectors.toList());
        Map<Long, Integer> favorStatusMap=new HashMap<>(16);
        Map<Long, Integer>  likeStatusMap=new HashMap<>(16);
        Map<String, Integer> subscribeStatusMap=new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            favorStatusMap= getFavorStatus(req.getMemberId(), dynamicIds);
            likeStatusMap= getLikeStatus(req.getMemberId(), dynamicIds);
        }
        if (CollectionUtils.isNotEmpty(publisherIds)) {
            subscribeStatusMap= getSubscribeStatus(req.getMemberId(), publisherIds);
        }
        if (CollUtil.isNotEmpty(result)) {
            for (DynamicFollow dynamicFollow : result) {
                DynamicPageRes dynamicPageRes = new DynamicPageRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicPageRes);
                dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                dynamicPageRes.setFavorStatus(favorStatusMap.get(dynamicFollow.getDynamicId()));
                dynamicPageRes.setLikeStatus(likeStatusMap.get(dynamicFollow.getDynamicId()));
                List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds() , dynamicFollow.getLabelVals());
                dynamicPageRes.setImg(convertImg(dynamicFollow.getImg()));
                dynamicPageRes.setSubscribeStatus(subscribeStatusMap.get(dynamicPageRes.getPublisherId()+"-"+req.getMemberId()));
                dynamicPageRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setLabels(labelRes);
                dynamicPageResList.add(dynamicPageRes);
            }
            dynamicPage.setResult(dynamicPageResList);
        }
        log.info("DynamicServiceImpl -> getDynamic -> end -> return:{}", dynamicPage);
        return dynamicPage;
    }

    /**
     * 根据动态ID删除动态及评论
     *
     * @param dynamicId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDynamicById(Long dynamicId, Long memberId) {
        log.info("DynamicServiceImpl -> deleteDynamicById -> start -> dynamicId:{} -> memberId -> {}", dynamicId, memberId);
        DynamicExample queryExample = new DynamicExample();
        queryExample.createCriteria().andIdEqualTo(dynamicId)
                .andMemberIdEqualTo(memberId);
        Dynamic queryDynamic = dynamicMapper.selectOneByExample(queryExample);
        LinkPublishContentExample example = new LinkPublishContentExample();
        example.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andPublishIdEqualTo(dynamicId).andSalesmanIdEqualTo(memberId);
        LinkPublishContent linkPublishContent = linkPublishContentMapper.selectOneByExample(example);
        example.clear();
        boolean isPublisher = ObjectUtil.isNotEmpty(queryDynamic) || ObjectUtil.isNotEmpty(linkPublishContent);
        log.info("DynamicServiceImpl -> deleteDynamicById -> 判断用户是否有权限删除动态 -> {}", isPublisher);
        if (isPublisher) {
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria()
                    .andDynamicIdEqualTo(dynamicId);
            Comment comment = new Comment();
            comment.setDelFlag(ConstantEnum.DELETED.getByte());
            commentMapper.updateByExampleSelective(comment, commentExample);

            example.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andPublishIdEqualTo(dynamicId).andTypeEqualTo(DynamicConstantEnum.REPLACE_TYPE_DYNAMIC.getCode());
            LinkPublishContent publishContent = linkPublishContentMapper.selectOneByExample(example);
            example.clear();
            if (ObjectUtil.isNotEmpty(publishContent)) {
                publishContent.setDelFlag(ConstantEnum.DELETED.getByte());
                example.createCriteria().andPublishIdEqualTo(dynamicId)
                        .andTypeEqualTo(DynamicConstantEnum.REPLACE_TYPE_DYNAMIC.getCode());
                linkPublishContentMapper.updateByExampleSelective(publishContent, example);
            }

            LinkLabelExample linkLabelExample = new LinkLabelExample();
            linkLabelExample.createCriteria()
                    .andTypeEqualTo(LinkLabelEnum.LINK_LABEL_DYNAMIC.getCode())
                    .andSourceIdEqualTo(dynamicId);
            LinkLabel linkLabel = new LinkLabel();
            linkLabel.setDelFlag(ConstantEnum.DELETED.getByte());
            linkLabelMapper.updateByExampleSelective(linkLabel, linkLabelExample);
            dynamicMapper.updateByPrimaryKeySelective(Dynamic.builder().id(dynamicId).delFlag(ConstantEnum.DELETED.getByte()).build());
            log.info("DynamicServiceImpl -> deleteDynamicById -> end");
            return;
        }
        log.error("DynamicServiceImpl -> deleteDynamicById -> error :{}", DynamicExceptionEnum.DONT_DELETE.getMessage());
        throw new BusinessException(DynamicExceptionEnum.DONT_DELETE);
    }

    /**
     * 返回动态详情
     *
     * @param dynamicId
     * @param memberId
     * @return
     */
    @Override
    public DynamicDetailedRes getDynamicDetailed(Long dynamicId, Long memberId) {
        log.info("DynamicServiceImpl -> getDynamicDetailed -> start -> dynamicId:{} , memberId:{}", dynamicId, memberId);
        DynamicDetailedRes res = new DynamicDetailedRes();
        res.setPermissions(true);
        DynamicDetailed dynamicDetailed = customDynamicMapper.getDynamicDetailed(dynamicId);
        log.info("DynamicServiceImpl -> getDynamicDetailed -> dynamicDetailed != null :{}", dynamicDetailed != null);
        if (dynamicDetailed != null) {
            BeanUtils.copyProperties(dynamicDetailed, res);
            res.setPushTime(LocalDateTimeUtil.format(dynamicDetailed.getPushTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
            res.setImg(convertImg(dynamicDetailed.getImg()));
            res.setLabels(convertLabels(dynamicDetailed.getLabelIds(), dynamicDetailed.getLabelVals()));
            if (memberId != null) {
                res.setSubscribeStatus(getSubscribeStatus(memberId, res.getPublisherId()));
                res.setLikeStatus(getLikeStatus(memberId, res.getDynamicId()));
                res.setFavorStatus(getFavorStatus(memberId, res.getDynamicId()));
                Member member = memberMapper.selectByPrimaryKey(memberId);
                log.info("DynamicServiceImpl -> getDynamicDetailed -> member == null :{}", member == null);
                if (member == null) {
                    log.error("DynamicServiceImpl -> getDynamicDetailed -> error:{}", MemberExceptionEnum.NOT_LOGIN.getMessage());
                    throw new BusinessException(MemberExceptionEnum.NOT_LOGIN);
                }
                Boolean permissions = member.getLevel() >= dynamicDetailed.getNeedVip();
                log.info("DynamicServiceImpl -> getDynamicDetailed -> permissions :{}", permissions);
                if (!permissions) {
                    res.setPermissions(false);
                }
            } else {
                res.setLikeStatus(DynamicConstantEnum.LIKE_FALSE.getCode());
                res.setFavorStatus(DynamicConstantEnum.FAVOR_FALSE.getCode());
                res.setSubscribeStatus(DynamicConstantEnum.SUBSCRIBE_FALSE.getCode());
            }
            List<DynamicSelectConfigRes> selectConfigList = new ArrayList<>();
            if (StrUtil.isAllNotEmpty(dynamicDetailed.getSelectConfigIds(), dynamicDetailed.getArticle())) {
                String[] selectConfigIds = dynamicDetailed.getSelectConfigIds().split(",");
                String[] article = dynamicDetailed.getArticle().split(",");
                for (int i = 0; i < selectConfigIds.length; i++) {
                    DynamicSelectConfigRes dynamicSelectConfigRes = new DynamicSelectConfigRes();
                    dynamicSelectConfigRes.setId(Long.parseLong(selectConfigIds[i]));
                    dynamicSelectConfigRes.setVal(article[i]);
                    selectConfigList.add(dynamicSelectConfigRes);
                }
            }
            res.setArticles(selectConfigList);
        } else {
            log.error("DynamicServiceImpl -> getDynamicDetailed -> error :{}", DynamicExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(DynamicExceptionEnum.DELETE_TRUE);
        }
        log.info("DynamicServiceImpl -> getDynamicDetailed -> end -> res :{}", res);
        return res;
    }

    /**
     * 获取关注列表
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<DynamicFollowRes>> getDynamicFollow(DynamicFollowReq req) {
        log.info("DynamicServiceImpl -> getDynamicFollow -> start -> req :{}", req);
        Long memberId = req.getMemberId();
        Page<DynamicFollow> page = PageUtil.start(req, () -> customDynamicMapper.getDynamicFollow(memberId));
        log.info("DynamicServiceImpl -> getDynamicFollow -> page != null -> page:{}", page);
        ServicePage<List<DynamicFollowRes>> dynamicPage = new ServicePage<>();
        BeanUtils.copyProperties(page, dynamicPage);
        List<DynamicFollow> result = page.getResult();

        List<Long> dynamicIds = result.stream().map(DynamicFollow::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer>  favorStatusMap=new HashMap<>(16);
        Map<Long, Integer>  likeStatusMap=new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            favorStatusMap= getFavorStatus(req.getMemberId(), dynamicIds);
            likeStatusMap = getLikeStatus(req.getMemberId(), dynamicIds);
        }
        log.info("DynamicServiceImpl -> getDynamicFollow -> result != null :{}", CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)) {
            List<DynamicFollowRes> res = new ArrayList<>();
            for (DynamicFollow dynamicFollow : result) {
                DynamicFollowRes dynamicFollowRes = new DynamicFollowRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicFollowRes);
                dynamicFollowRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                if (dynamicFollowRes.getDynamicId()!=null) {
                    dynamicFollowRes.setLikeStatus(favorStatusMap.get(dynamicFollowRes.getDynamicId()));
                    dynamicFollowRes.setFavorStatus(likeStatusMap.get(dynamicFollowRes.getDynamicId()));
                }
                List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds() , dynamicFollow.getLabelVals());
                dynamicFollowRes.setLabels(labelRes);
                dynamicFollowRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicFollowRes.getDynamicId()));
                dynamicFollowRes.setImg(convertImg(dynamicFollow.getImg()));
                res.add(dynamicFollowRes);
            }
            dynamicPage.setResult(res);
        }
        log.info("DynamicServiceImpl -> getDynamicFollow -> end -> dynamicPage：{}", dynamicPage);
        return dynamicPage;
    }

    /**
     * 获取我的评论
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<MyCommentRes>> getMyComment(MyCommentReq req) {
        log.info("DynamicServiceImpl -> getMyComment -> start -> req:{}", req);
        Long memberId = req.getMemberId();
        Page<MyComment> page = PageUtil.start(req, () -> customDynamicMapper.getMyComment(memberId));
        log.info("DynamicServiceImpl -> getMyComment -> page == null? -> page:{}", page);
        ServicePage<List<MyCommentRes>> myCommentPage = new ServicePage<>();
        BeanUtils.copyProperties(page, myCommentPage);
        List<MyComment> result = page.getResult();
        List<Long> dynamicIds = result.stream().map(MyComment::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> favorStatus = getFavorStatus(req.getMemberId(), dynamicIds);
        Map<Long, Integer> likeStatus = getLikeStatus(req.getMemberId(), dynamicIds);
        List<MyCommentRes> res = new ArrayList<>();
        log.info("DynamicServiceImpl -> getMyComment -> result != null -> result:{}", CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)) {
            for (MyComment myComment : result) {
                MyCommentRes commentRes = new MyCommentRes();
                BeanUtils.copyProperties(myComment, commentRes);
                commentRes.setPublishTime(LocalDateTimeUtil.format(myComment.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                commentRes.setLikeStatus(likeStatus.get(myComment.getDynamicId()));
                commentRes.setFavorStatus(favorStatus.get(myComment.getDynamicId()));
                List<DynamicLabelRes> labels = convertLabels(myComment.getLabelIds() , myComment.getLabelVals());
                commentRes.setLabels(labels);
                commentRes.setAgreeHeadImages(getAgreeMemberHeadImage(commentRes.getDynamicId()));
                commentRes.setImg(convertImg(myComment.getImg()));
                res.add(commentRes);
            }
            myCommentPage.setResult(res);
        }
        log.info("DynamicServiceImpl -> getMyComment -> end -> res:{}", myCommentPage);
        return myCommentPage;
    }

    /**
     * 点赞
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean agreeDynamic(DynamicAgreeReq req) {
        String lockKey ="LOCK::DYNAMIC::"+req.getMemberId()+"::"+req.getDynamicId();
        RBucket<Long> bucket =redissonClient.getBucket(lockKey);
        boolean ifExists = bucket.trySet(req.getMemberId(), 5L, TimeUnit.SECONDS);
        if (!ifExists) {
            log.info("DynamicServiceImpl->agreeDynamic->此用户已点过赞");
            throw new BusinessException(DynamicExceptionEnum.DYNAMIC_AFTER_LIKE);
        }

        log.info("DynamicServiceImpl -> agreeDynamic -> start -> param:{}", req);
        Long dynamicId = req.getDynamicId();
        Long memberId = req.getMemberId();
        DynamicExample dynamicExample = new DynamicExample();
        dynamicExample.createCriteria()
                .andIdEqualTo(dynamicId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Dynamic dynamicQuery = dynamicMapper.selectOneByExample(dynamicExample);
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        log.info("DynamicServiceImpl -> agreeDynamic -> member == null :{}", member == null);
        if (member == null) {
            log.error("DynamicServiceImpl -> agreeDynamic -> error :{}", DynamicExceptionEnum.NOT_EXIST.getMessage());
            throw new BusinessException(DynamicExceptionEnum.NOT_EXIST);
        }
        log.info("DynamicServiceImpl -> agreeDynamic -> dynamicQuery == null :{}", dynamicQuery == null);
        if (dynamicQuery == null) {
            log.error("DynamicServiceImpl -> agreeDynamic -> error :{}", DynamicExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(DynamicExceptionEnum.DELETE_TRUE);
        }
        LogBehaveDynamicExample logBehaveDynamicExample = new LogBehaveDynamicExample();
        logBehaveDynamicExample.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_DYNAMIC.getCode())
                .andSourceIdEqualTo(dynamicId);
        LogBehaveDynamic logBehaveDynamic = logBehaveDynamicMapper.selectOneByExample(logBehaveDynamicExample);
        Boolean isTrue = logBehaveDynamic != null && logBehaveDynamic.getDelFlag() == ConstantEnum.NOT_DEL.getByte();
        log.info("DynamicServiceImpl -> agreeDynamic -> isTrue :{}", isTrue);
        if (isTrue) {
            log.error("DynamicServiceImpl -> agreeDynamic -> error :{}", DynamicExceptionEnum.DYNAMIC_AFTER_LIKE.getMessage());
            throw new BusinessException(DynamicExceptionEnum.DYNAMIC_AFTER_LIKE);
        }
        Boolean isFalse = logBehaveDynamic != null && logBehaveDynamic.getDelFlag() == ConstantEnum.DELETED.getByte();
        log.info("DynamicServiceImpl -> agreeDynamic -> isTrue :{}", isFalse);
        if (isFalse) {
            logBehaveDynamic.setDelFlag(ConstantEnum.NOT_DEL.getByte());
            Dynamic dynamic = dynamicMapper.selectByPrimaryKey(dynamicId);
            dynamicMapper.updateByPrimaryKeySelective(Dynamic.builder().id(dynamicId).likeNum(dynamic.getLikeNum() + 1).build());
            log.info("DynamicServiceImpl -> agreeDynamic -> end");
            return logBehaveDynamicMapper.updateByPrimaryKeySelective(logBehaveDynamic) > 0;
        }
        log.info("DynamicServiceImpl -> agreeDynamic -> logBehaveDynamic == null :{}", logBehaveDynamic == null);
            if (logBehaveDynamic == null) {
                        int behaveInsert = logBehaveDynamicMapper.insertSelective(LogBehaveDynamic.builder()
                                .id(snowflakeIdWorker53.nextId())
                                .sourceId(dynamicId)
                                .memberId(memberId)
                                .type(DynamicConstantEnum.LIKE_TYPE_DYNAMIC.getCode())
                                .delFlag(ConstantEnum.NOT_DEL.getByte())
                                .build());
                        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(dynamicId);
                        dynamic.setLikeNum(dynamic.getLikeNum() + 1);
                        int dynamicInsert = dynamicMapper.updateByPrimaryKeySelective(dynamic);
                        Boolean res = behaveInsert + dynamicInsert == 2;
                        log.info("DynamicServiceImpl -> agreeDynamic -> end -> return:{}", res);
                        //消息通知
                        messageNotice(dynamicId, dynamic);
                        return res;

            }
            bucket.delete();
        log.info("DynamicServiceImpl -> agreeDynamic -> end -> return:false");
        return false;
    }


    private void messageNotice(Long dynamicId, Dynamic dynamic) {
        publishEventComponent.publishEvent(new DynamicAgreeEvent(DynamicEventParam.builder()
                .dynamicId(dynamicId)
                .msgBody("有人给您点了个赞")
                .msgSendTo(String.valueOf(dynamic.getMemberId()))
                .build()));
    }

    /**
     * 动态取消点赞
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disagreeDynamic(DynamicDisagreeReq req) {
        log.info("DynamicServiceImpl -> disagreeDynamic -> start -> param:{}", req);
        Long dynamicId = req.getDynamicId();
        Long memberId = req.getMemberId();
        LogBehaveDynamicExample logBehaveDynamicExample = new LogBehaveDynamicExample();
        logBehaveDynamicExample.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andSourceIdEqualTo(dynamicId)
                .andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_DYNAMIC.getCode())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        LogBehaveDynamic logBehaveDynamic = logBehaveDynamicMapper.selectOneByExample(logBehaveDynamicExample);
        log.info("DynamicServiceImpl -> disagreeDynamic -> logBehaveDynamic == null :{}", logBehaveDynamic == null);
        if (logBehaveDynamic == null) {
            throw new BusinessException(DynamicExceptionEnum.DYNAMIC_NOT_LIKE);
        }
        logBehaveDynamic.setDelFlag(ConstantEnum.DELETED.getByte());
        int behaveUpdate = logBehaveDynamicMapper.updateByPrimaryKeySelective(logBehaveDynamic);
        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(dynamicId);
        dynamic.setLikeNum(dynamic.getLikeNum() - 1);
        int dynamicUpdate = dynamicMapper.updateByPrimaryKeySelective(dynamic);
        Boolean res = behaveUpdate + dynamicUpdate == 2;
        log.info("DynamicServiceImpl -> disagreeDynamic -> end -> return:{}", res);
        return res;
    }


    /**
     * 获取我的点赞
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<MyAgreeRes>> getMyAgree(MyAgreeReq req) {
        log.info("DynamicServiceImpl -> getMyAgree -> start -> req:{}", req);
        Long memberId = req.getMemberId();
        Page<MyAgree> page = PageUtil.start(req, () -> customDynamicMapper.getMyAgree(memberId));
        ServicePage<List<MyAgreeRes>> myAgreePage = new ServicePage<>();
        BeanUtils.copyProperties(page, myAgreePage);
        List<MyAgree> result = page.getResult();
        List<Long> dynamicIds = result.stream().map(MyAgree::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> favorStatus = getFavorStatus(req.getMemberId(), dynamicIds);
        List<MyAgreeRes> res = new ArrayList<>();
        log.info("DynamicServiceImpl -> getMyAgree -> result != null:{}", CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)) {
            for (MyAgree myAgree : result) {
                MyAgreeRes agreeRes = new MyAgreeRes();
                BeanUtils.copyProperties(myAgree, agreeRes);
                agreeRes.setPublishTime(LocalDateTimeUtil.format(myAgree.getPublishTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                agreeRes.setLikeStatus(DynamicConstantEnum.LIKE_TRUE.getCode());
                agreeRes.setFavorStatus(favorStatus.get(myAgree.getDynamicId()));
                List<DynamicLabelRes> labels = convertLabels(myAgree.getLabelIds() , myAgree.getLabelVals());
                agreeRes.setLabels(labels);
                agreeRes.setAgreeHeadImages(getAgreeMemberHeadImage(agreeRes.getDynamicId()));
                agreeRes.setImg(convertImg(myAgree.getImg()));
                res.add(agreeRes);
            }
            myAgreePage.setResult(res);
        }
        log.info("DynamicServiceImpl -> getMyAgree -> end -> myAgreePage:{}", myAgreePage);
        return myAgreePage;
    }

    /**
     * 根据memberId分页查询收藏
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<FavorPageRes>> getByMemberId(FavorPageReq req) {
        log.info("DynamicServiceImpl -> getByMemberId -> start -> req:{}", req);
        ServicePage<List<FavorPageRes>> res = new ServicePage<>();
        Page<DynamicFollow> page = PageUtil.start(req, () -> customDynamicMapper.getFavorDynamic(req.getMemberId()));
        BeanUtils.copyProperties(page, res);
        List<DynamicFollow> result = page.getResult();
        List<Long> dynamicIds = result.stream().map(DynamicFollow::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> likeStatusMap =new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            likeStatusMap = getLikeStatus(req.getMemberId(), dynamicIds);
        }
        List<FavorPageRes> favorPageRes = new ArrayList<>();
        for (DynamicFollow dynamicFollow:result) {
            FavorPageRes pageRes = new FavorPageRes();
            BeanUtils.copyProperties(dynamicFollow, pageRes);
            pageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
            pageRes.setLikeStatus(likeStatusMap.get(dynamicFollow.getDynamicId()));
            pageRes.setFavorStatus(DynamicConstantEnum.FAVOR_TRUE.getCode());
            List<DynamicLabelRes> dynamicLabelRes = convertLabels(dynamicFollow.getLabelIds(), dynamicFollow.getLabelVals());
            pageRes.setLabels(dynamicLabelRes);
            pageRes.setAgreeHeadImages(getAgreeMemberHeadImage(pageRes.getDynamicId()));
            pageRes.setImg(convertImg(dynamicFollow.getImg()));
            favorPageRes.add(pageRes);
        }
        res.setResult(favorPageRes);
        log.info("DynamicServiceImpl -> getByMemberId -> end -> return :{}", res);
        return res;
    }

    /**
     * 获取热门动态
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<DynamicHotRes>> getHotDynamic(DynamicHotReq req) {
        log.info("DynamicServiceImpl -> getHotDynamic -> start -> param:{}", req);
        ServicePage<List<DynamicHotRes>> res = new ServicePage<>();
        RBucket<String> bucket = redissonClient.getBucket(hotDynamicKeyPrefix + req.getMemberId());
        List<Long> ids = JSONArray.parseArray(bucket.get(), Long.class);
        SubscribeExample subscribeExample = new SubscribeExample();
        subscribeExample.createCriteria().andTypeEqualTo(SubscribeEnum.TYPE_BLOCK.getValue())
                .andMemberIdEqualTo(req.getMemberId());
        List<Subscribe> subscribes = subscribeMapper.selectByExample(subscribeExample);
        List<Long> subscribeIds = subscribes.stream().map(Subscribe::getSubscribeId).collect(Collectors.toList());
        Page<DynamicFollow> page = PageUtil.start(req, () -> customDynamicMapper.getHotDynamic(ids, subscribeIds));
        BeanUtils.copyProperties(page, res);
        List<DynamicFollow> result = page.getResult();
        List<DynamicHotRes> dynamicHotResList = new ArrayList<>();
        List<Long> dynamicIds =result.stream().map(DynamicFollow::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> favorStatusMap=new HashMap<>(16);
        Map<Long, Integer> likeStatusMap=new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            favorStatusMap = getFavorStatus(req.getMemberId(), dynamicIds);
            likeStatusMap = getLikeStatus(req.getMemberId(), dynamicIds);
        }
        if (CollectionUtils.isNotEmpty(result)) {
            for (DynamicFollow dynamicFollow : result) {
                DynamicHotRes dynamicHotRes = new DynamicHotRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicHotRes);
                dynamicHotRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                List<DynamicLabelRes> labelResList = convertLabels(dynamicFollow.getLabelIds(), dynamicFollow.getLabelVals());
                dynamicHotRes.setLabels(labelResList);
                dynamicHotRes.setFavorStatus(favorStatusMap.get(dynamicHotRes.getDynamicId()));
                dynamicHotRes.setLikeStatus(likeStatusMap.get(dynamicHotRes.getDynamicId()));
                dynamicHotRes.setImg(convertImg(dynamicFollow.getImg()));
                dynamicHotRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicHotRes.getDynamicId()));
                dynamicHotResList.add(dynamicHotRes);
            }
        }
        res.setResult(dynamicHotResList);
        log.info("DynamicServiceImpl -> getHotDynamic -> end -> return:{}", res);
        if (CollUtil.isEmpty(ids)){
            bucket.set(JSONUtil.toJsonStr(dynamicIds) , hotDynamicExpireTime , TimeUnit.SECONDS);
            log.info("DynamicServiceImpl -> getHotDynamic -> end -> return:{}", res);
            return res;
        }
        return res;
    }

    @Override
    public ServicePage<List<MyDynamicRes>> getMyDynamic(MyDynamicReq req) {
        log.info("DynamicServiceImpl -> getMyDynamic -> start -> params -> {}", req);
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        Query query = new Query();
        query.put("id", req.getMemberId());
        query.put("assistantId", null);
        if (member.getRole().equals(MemberEnum.ROLE_ASSISTANT.getValue()) && member.getTeacherId() != null) {
            query.put("assistantId", member.getId());
            query.put("id", member.getTeacherId());
        }
        Page<MyDynamic> page = PageUtil.start(req, () -> customDynamicMapper.getMyDynamic(query));
        ServicePage<List<MyDynamicRes>> dynamicPage = new ServicePage<>();
        BeanUtils.copyProperties(page, dynamicPage);
        List<MyDynamic> result = page.getResult();
        List<Long> dynamicIds = result.stream().map(MyDynamic::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> favorStatusMap=new HashMap<>(16);
        Map<Long, Integer> likeStatusMap=new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            favorStatusMap = getFavorStatus(req.getMemberId(), dynamicIds);
            likeStatusMap = getLikeStatus(req.getMemberId(), dynamicIds);
        }
        List<MyDynamicRes> dynamicPageResList = new ArrayList<>();
        log.info("DynamicServiceImpl -> getDynamic -> result != null :{}", CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)) {
            for (MyDynamic dynamicFollow : result) {
                MyDynamicRes dynamicPageRes = new MyDynamicRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicPageRes);
                if (ObjectUtil.isNotEmpty(dynamicFollow.getAssistant())) {
                    AssistantBaseInfoDTO assistantBaseInfoDTO = new AssistantBaseInfoDTO();
                    BeanUtils.copyProperties(dynamicFollow.getAssistant(), assistantBaseInfoDTO);
                    dynamicPageRes.setAssistant(assistantBaseInfoDTO);
                }
                dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                dynamicPageRes.setFavorStatus(favorStatusMap.get(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setLikeStatus(likeStatusMap.get(dynamicPageRes.getDynamicId()));
                List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds() , dynamicFollow.getLabelVals());
                dynamicPageRes.setLabels(labelRes);
                dynamicPageRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setImg(convertImg(dynamicFollow.getImg()));
                dynamicPageResList.add(dynamicPageRes);
            }
            dynamicPage.setResult(dynamicPageResList);
        }
        log.info("DynamicServiceImpl -> getDynamic -> end -> return:{}", dynamicPage);
        return dynamicPage;
    }

    @Override
    public ServicePage<List<MyDynamicArticlePageRes>> getMyDynamicArticle(MyDynamicArticleReq req) {
        log.info("DynamicServiceImpl -> getMyDynamicArticle -> start -> params -> {}", req);
        MyDynamicArticleDo dynamicArticleDo = MyDynamicArticleDo.builder()
                .sourceId(req.getSourceId() == null ? req.getMemberId() : req.getSourceId())
                .articleId(req.getArticleId())
                .verify(req.getSourceId() == null || req.getSourceId().equals(req.getMemberId()))
                .build();
        Page<DynamicFollow> page = PageUtil.start(req, () -> customDynamicMapper.getMyDynamicByArticle(dynamicArticleDo));
        ServicePage<List<MyDynamicArticlePageRes>> dynamicPage = new ServicePage<>();
        BeanUtils.copyProperties(page, dynamicPage);
        List<DynamicFollow> result = page.getResult();
        List<MyDynamicArticlePageRes> dynamicPageResList = new ArrayList<>();
        log.info("DynamicServiceImpl -> getMyDynamicArticle -> result != null :{}", CollUtil.isNotEmpty(result));
        List<Long> dynamicIds = result.stream().map(DynamicFollow::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> favorStatusMap =new HashMap<>(16);
        Map<Long, Integer> likeStatusMap=new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            favorStatusMap= getFavorStatus(req.getMemberId(), dynamicIds);
            likeStatusMap= getLikeStatus(req.getMemberId(), dynamicIds);
        }
        if (CollUtil.isNotEmpty(result)) {
            for (DynamicFollow dynamicFollow : result) {
                MyDynamicArticlePageRes dynamicPageRes = new MyDynamicArticlePageRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicPageRes);
                if (ObjectUtil.isNotEmpty(dynamicFollow.getAssistant())) {
                    AssistantBaseInfoDTO assistantBaseInfoDTO = new AssistantBaseInfoDTO();
                    BeanUtils.copyProperties(dynamicFollow.getAssistant(), assistantBaseInfoDTO);
                    dynamicPageRes.setAssistant(assistantBaseInfoDTO);
                }
                dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                dynamicPageRes.setFavorStatus(favorStatusMap.get(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setLikeStatus(likeStatusMap.get(dynamicPageRes.getDynamicId()));
                List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds() , dynamicFollow.getLabelVals());
                dynamicPageRes.setLabels(labelRes);
                dynamicPageRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setImg(convertImg(dynamicFollow.getImg()));
                dynamicPageResList.add(dynamicPageRes);
            }
            dynamicPage.setResult(dynamicPageResList);
        }
        log.info("DynamicServiceImpl -> getMyDynamicArticle -> end -> return:{}", dynamicPage);
        return dynamicPage;
    }

    @Override
    public ServicePage<List<OrganizationDynamicArticleRes>> getOrganizationDynamicArticle(OrganizationDynamicArticleReq req) {
        log.info("DynamicServiceImpl -> getOrganizationDynamicArticle -> start -> params -> {}", req);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andOrganizationIdEqualTo(req.getOrganizationId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<Member> members = memberMapper.selectByExample(memberExample);
        List<Long> memberIds = members.stream().map(Member::getId).collect(Collectors.toList());

        OrganizationDynamicArticleDo organizationDynamicArticleDo = OrganizationDynamicArticleDo.builder()
                .verify(memberIds.contains(req.getMemberId()))
                .organizationId(req.getOrganizationId())
                .articleId(req.getArticleId())
                .build();
        Page<DynamicFollow> page = PageUtil.start(req, () -> customDynamicMapper.getOrganizationDynamicByArticle(organizationDynamicArticleDo));
        ServicePage<List<OrganizationDynamicArticleRes>> dynamicPage = new ServicePage<>();
        BeanUtils.copyProperties(page, dynamicPage);
        List<DynamicFollow> result = page.getResult();
        List<OrganizationDynamicArticleRes> dynamicPageResList = new ArrayList<>();
        log.info("DynamicServiceImpl -> getOrganizationDynamicArticle -> result != null :{}", CollUtil.isNotEmpty(result));
        List<Long> dynamicIds = result.stream().map(DynamicFollow::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> favorStatusMap =new HashMap<>(16);
        Map<Long, Integer> likeStatusMap =new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            favorStatusMap=getFavorStatus(req.getMemberId(), dynamicIds);
            likeStatusMap= getLikeStatus(req.getMemberId(), dynamicIds);
        }
        if (CollUtil.isNotEmpty(result)) {
            for (DynamicFollow dynamicFollow : result) {
                OrganizationDynamicArticleRes dynamicPageRes = new OrganizationDynamicArticleRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicPageRes);
                dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                dynamicPageRes.setFavorStatus(favorStatusMap.get(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setLikeStatus(likeStatusMap.get(dynamicPageRes.getDynamicId()));
                List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds() , dynamicFollow.getLabelVals());
                dynamicPageRes.setLabels(labelRes);
                dynamicPageRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setImg(convertImg(dynamicFollow.getImg()));
                dynamicPageResList.add(dynamicPageRes);
            }
            dynamicPage.setResult(dynamicPageResList);
        }
        log.info("DynamicServiceImpl -> getOrganizationDynamicArticle -> end -> return:{}", dynamicPage);
        return dynamicPage;
    }

    @Override
    public void refreshRecommendCache(LocalDateTime lastQueryTime) {
        LocalDate now = LocalDate.now();
//        RList<DynamicRecommendCacheDTO> redissonClientList = redissonClient.getList("recommend::dynamic::source::" + now.format(DatePattern.NORM_DATE_FORMATTER));
        RList<String> redissonClientList = redissonClient.getList("recommend::dynamic::source::" + now.format(DatePattern.NORM_DATE_FORMATTER));
        boolean exists = redissonClientList.isExists();

        String queryTime = null;
        // 增量更新
        if (redissonClientList.isExists() && null != lastQueryTime) {
            queryTime = lastQueryTime.format(DatePattern.NORM_DATETIME_FORMATTER);
        }
        List<DynamicRecommendCacheDO> queryList = customDynamicMapper.listRefreshRecommendCache(queryTime);
        List<DynamicRecommendCacheDTO> list = new ArrayList<>();
        for (DynamicRecommendCacheDO cacheDO : queryList) {
            DynamicRecommendCacheDTO dto = new DynamicRecommendCacheDTO();
            BeanUtils.copyProperties(cacheDO, dto);
            String labels = cacheDO.getLabels();
            if (StrUtil.isNotBlank(labels)) {
                dto.setLabelList(Arrays.asList(labels.split(",")));
            }
            list.add(dto);
        }
        log.info("refreshRecommendCache -> list -> size -> {}", list.size());
        List<String> saveList = list.stream()
                .filter(item -> !redissonClientList.contains(item))
                .map(JSONUtil::toJsonStr)
                .collect(Collectors.toList());
        redissonClientList.addAll(saveList);
        if(! exists ) {
            redissonClientList.expire(24, TimeUnit.HOURS);
        }
    }

    @Override
    public List<DynamicPageRes> getDynamicList(List<Long> dynamicIdList,Long memberId) {
       log.info("DynamicServiceImpl -> getDynamicList -> start -> param -> {}", dynamicIdList);
        if (CollUtil.isEmpty(dynamicIdList)) {
            log.info("DynamicServiceImpl -> getDynamicList -> end -> return: null");
            return null;
        }
        SubscribeExample subscribeExample = new SubscribeExample();
        subscribeExample.createCriteria().andTypeEqualTo(SubscribeEnum.TYPE_BLOCK.getValue())
                .andMemberIdEqualTo(memberId);
        List<Subscribe> subscribes = subscribeMapper.selectByExample(subscribeExample);
        List<Long> subscribeIds = subscribes.stream().map(Subscribe::getSubscribeId).collect(Collectors.toList());
        DynamicPageVo dynamicPageVo =new DynamicPageVo();
        dynamicPageVo.setDynamicIds(dynamicIdList);
        dynamicPageVo.setSubscribeIds(subscribeIds);
        dynamicPageVo.setDynamicIds(null);
        dynamicPageVo.setChannelId(null);
        List<DynamicFollow> result = customDynamicMapper.getDynamicPage(dynamicPageVo);
        List<DynamicPageRes> dynamicPageResList = new ArrayList<>();
        log.info("DynamicServiceImpl -> getDynamicList -> result != null :{}", CollUtil.isNotEmpty(result));
        List<Long> dynamicIds = result.stream().map(DynamicFollow::getDynamicId).collect(Collectors.toList());
        Map<Long, Integer> favorStatusMap =new HashMap<>(16);
        Map<Long, Integer> likeStatusMap = new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(dynamicIds)) {
            favorStatusMap = getFavorStatus(memberId, dynamicIds);
            likeStatusMap = getLikeStatus(memberId, dynamicIds);
        }
        if (CollUtil.isNotEmpty(result)) {
            for (DynamicFollow dynamicFollow : result) {
                DynamicPageRes dynamicPageRes = new DynamicPageRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicPageRes);
                dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime() , DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                dynamicPageRes.setFavorStatus(favorStatusMap.get(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setLikeStatus(likeStatusMap.get(dynamicPageRes.getDynamicId()));
                List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds(),dynamicFollow.getLabelVals());
                dynamicPageRes.setLabels(labelRes);
                dynamicPageResList.add(dynamicPageRes);
            }
        }
        log.info("DynamicServiceImpl -> getDynamicList -> end ");
        return dynamicPageResList;
    }

    @Override
    public List<RandomDynamicRes> getRandomDynamic(RandomDynamicReq req) {
        List<RandomDynamicRes> res = new ArrayList<>();
        List<DynamicFollow> randomDynamic = customDynamicMapper.getRandomDynamic(req.getLimit());
        randomDynamic.forEach(dynamicFollow -> {
            RandomDynamicRes dynamicPageRes = new RandomDynamicRes();
            BeanUtils.copyProperties(dynamicFollow, dynamicPageRes);
            dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
            dynamicPageRes.setImg(convertImg(dynamicFollow.getImg()));
            dynamicPageRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicPageRes.getDynamicId()));
            List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds(), dynamicFollow.getLabelVals());
            dynamicPageRes.setLabels(labelRes);
            dynamicPageRes.setLikeStatus(DynamicConstantEnum.LIKE_FALSE.getCode());
            dynamicPageRes.setFavorStatus(DynamicConstantEnum.FAVOR_FALSE.getCode());
            if (req.getMemberId() != null) {
                dynamicPageRes.setLikeStatus(getLikeStatus(req.getMemberId(), dynamicPageRes.getDynamicId()));
                dynamicPageRes.setFavorStatus(getFavorStatus(req.getMemberId(), dynamicPageRes.getDynamicId()));
            }
            res.add(dynamicPageRes);
        });
        return res;
    }

    @Override
    public ServicePage<List<MyDynamicArticlePageRes>> myselfMyDynamicArticle(MyselfDynamicArticleReq req) {
        log.info("DynamicServiceImpl -> myselfMyDynamicArticle -> start -> params -> {}", req);
        Query query = new Query();
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        if (null == member) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        query.put("memberId", member.getId());
        query.put("articleId", req.getArticleId());
        if (member.getRole().equals(MemberEnum.ROLE_ASSISTANT.getValue()) && member.getTeacherId() != null) {
            query.put("assistantId", member.getId());
            query.put("memberId", member.getTeacherId());
        }
        Page<DynamicFollow> page = PageUtil.start(req, () -> customDynamicMapper.myselfDynamicByArticle(query));
        ServicePage<List<MyDynamicArticlePageRes>> dynamicPage = new ServicePage<>();
        BeanUtils.copyProperties(page, dynamicPage);
        List<DynamicFollow> result = page.getResult();
        List<MyDynamicArticlePageRes> dynamicPageResList = new ArrayList<>();
        log.info("DynamicServiceImpl -> getMyDynamicArticle -> result != null :{}", CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)) {
            for (DynamicFollow dynamicFollow : result) {
                MyDynamicArticlePageRes dynamicPageRes = new MyDynamicArticlePageRes();
                BeanUtils.copyProperties(dynamicFollow, dynamicPageRes);
                if (ObjectUtil.isNotEmpty(dynamicFollow.getAssistant())) {
                    AssistantBaseInfoDTO assistantBaseInfoDTO = new AssistantBaseInfoDTO();
                    BeanUtils.copyProperties(dynamicFollow.getAssistant(), assistantBaseInfoDTO);
                    dynamicPageRes.setAssistant(assistantBaseInfoDTO);
                }
                dynamicPageRes.setPublishTime(LocalDateTimeUtil.format(dynamicFollow.getPublishTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                dynamicPageRes.setFavorStatus(getFavorStatus(req.getMemberId(), dynamicPageRes.getDynamicId()));
                dynamicPageRes.setLikeStatus(getLikeStatus(req.getMemberId(), dynamicPageRes.getDynamicId()));
                List<DynamicLabelRes> labelRes = convertLabels(dynamicFollow.getLabelIds(), dynamicFollow.getLabelVals());
                dynamicPageRes.setLabels(labelRes);
                dynamicPageRes.setAgreeHeadImages(getAgreeMemberHeadImage(dynamicPageRes.getDynamicId()));
                dynamicPageRes.setImg(convertImg(dynamicFollow.getImg()));
                dynamicPageResList.add(dynamicPageRes);
            }
            dynamicPage.setResult(dynamicPageResList);
        }
        log.info("DynamicServiceImpl -> myselfMyDynamicArticle -> end -> return:{}", dynamicPage);
        return dynamicPage;
    }

    /**
     * 获取收藏状态
     *
     * @param memberId
     * @param dynamicId
     * @return
     */
    private Integer getFavorStatus(Long memberId, Long dynamicId) {
        log.info("DynamicServiceImpl -> getFavorStatus -> start -> param1:{} ,param2:{}", memberId, dynamicId);
        FavorExample example = new FavorExample();
        example.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andDynamicIdEqualTo(dynamicId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Favor favor = favorMapper.selectOneByExample(example);
        log.info("DynamicServiceImpl -> getFavorStatus -> judge -> favor != null :{}", favor != null);
        if (favor != null) {
            log.info("DynamicServiceImpl -> getFavorStatus -> end -> return:{}", DynamicConstantEnum.FAVOR_TRUE.getCode());
            return DynamicConstantEnum.FAVOR_TRUE.getCode();
        }
        log.info("DynamicServiceImpl -> getFavorStatus -> end -> return:{}", DynamicConstantEnum.FAVOR_FALSE.getCode());
        return DynamicConstantEnum.FAVOR_FALSE.getCode();
    }

    /**
     * 获取收藏状态(优化sql)
     *
     * @param memberId
     * @param dynamicId
     * @return
     */
    private Map<Long,Integer> getFavorStatus(Long memberId, List<Long> dynamicId) {
        Long[] math = dynamicId.toArray(new Long[0]);
        List<Long> favor = favorMapper.getExampleList(memberId,math);
        List<Long> newList=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(favor)) {
            newList.addAll(dynamicId);
            newList.removeAll(favor);
        }
        Map<Long,Integer> map = new HashMap<>();
        if (CollectionUtils.isEmpty(favor)) {
            for (Long dynamicIds: dynamicId) {
                map.put(dynamicIds,DynamicConstantEnum.FAVOR_FALSE.getCode());
            }
        }else{
            for (Long dynamicIds: newList) {
                map.put(dynamicIds,DynamicConstantEnum.FAVOR_FALSE.getCode());
            }
            for (Long filterList:favor){
                map.put(filterList,DynamicConstantEnum.FAVOR_TRUE.getCode());
            }
        }
        return map;
    }

    /**
     * 获取点赞状态
     *
     * @param memberId
     * @param dynamicId
     * @return
     */
    private Integer getLikeStatus(Long memberId, Long dynamicId) {
        log.info("DynamicServiceImpl -> getLikeStatus -> start -> param1:{} ,param2:{}", memberId, dynamicId);
        LogBehaveDynamicExample example = new LogBehaveDynamicExample();
        example.createCriteria()
                .andSourceIdEqualTo(dynamicId)
                .andMemberIdEqualTo(memberId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_DYNAMIC.getCode());
        LogBehaveDynamic behaveDynamic = logBehaveDynamicMapper.selectOneByExample(example);
        log.info("DynamicServiceImpl -> getLikeStatus -> judge -> behaveDynamic != null :{}", behaveDynamic != null);
        if (behaveDynamic != null) {
            log.info("DynamicServiceImpl -> getLikeStatus -> end -> return:{}", DynamicConstantEnum.LIKE_TRUE.getCode());
            return DynamicConstantEnum.LIKE_TRUE.getCode();
        }
        log.info("DynamicServiceImpl -> getLikeStatus -> end -> return:{}", DynamicConstantEnum.LIKE_FALSE.getCode());
        return DynamicConstantEnum.LIKE_FALSE.getCode();
    }


    /**
     * 获取点赞状态(sql提出)
     *
     * @param memberId
     * @param dynamicId
     * @return
     */
    private Map<Long,Integer> getLikeStatus(Long memberId, List<Long> dynamicId) {
        Long[] math = dynamicId.toArray(new Long[0]);
        List<Long> logBehaveList = logBehaveDynamicMapper.getLogBehaveList(memberId,math);
        List<Long> newLikeList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(logBehaveList)){
            newLikeList.addAll(dynamicId);
            newLikeList.removeAll(logBehaveList);
        }
        Map<Long,Integer> map = new HashMap<>();
        if (CollectionUtils.isEmpty(logBehaveList)) {
            for (Long dynamicIds: dynamicId) {
                map.put(dynamicIds,DynamicConstantEnum.LIKE_FALSE.getCode());
            }
        }else{
            for (Long dynamicIds: newLikeList) {
                map.put(dynamicIds,DynamicConstantEnum.LIKE_FALSE.getCode());
            }
            for (Long filterList:logBehaveList){
                map.put(filterList,DynamicConstantEnum.LIKE_TRUE.getCode());
            }
        }
      return  map;
    }



    /**
     * 获取关注状态(Method优化）
     *
     * @param memberId
     * @param
     * @return
     */
    private Map<String,Integer>  getSubscribeStatus(Long memberId,  List<Long> subscribeIds) {
        Long[] mathSubscribeId = subscribeIds.toArray(new Long[0]);
        List<Long> subscribeIdList = subscribeMapper.selectSubscribeId(memberId, mathSubscribeId);
        List<Long> newSubscrList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(subscribeIdList)){
            newSubscrList.addAll(subscribeIds);
            newSubscrList.removeAll(subscribeIdList);
        }
        Map<String,Integer> map = new HashMap<>();
        if (CollectionUtils.isEmpty(subscribeIdList)) {
            for (Long subscribeIdLists: subscribeIds) {

                map.put(subscribeIdLists+"-"+memberId,DynamicConstantEnum.SUBSCRIBE_FALSE.getCode());
            }
        }else{
            for (Long subscribeIdLists: newSubscrList) {
                map.put(subscribeIdLists+"-"+memberId,DynamicConstantEnum.SUBSCRIBE_FALSE.getCode());
            }
            for (Long filterList:subscribeIdList){
                map.put(filterList+"-"+memberId,DynamicConstantEnum.SUBSCRIBE_TRUE.getCode());
            }
        }
        return map;
    }

    /**
     * 获取关注状态
     *
     * @param memberId
     * @param subscribeId
     * @return
     */
    private Integer getSubscribeStatus(Long memberId, Long subscribeId) {
        log.info("DynamicServiceImpl -> getSubscribeStatus -> start -> param1:{} ,param2:{}", memberId, subscribeId);
        SubscribeExample example = new SubscribeExample();
        example.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andMemberIdEqualTo(memberId)
                .andSubscribeTypeEqualTo(SubscribeEnum.SUBSCRIBE_TYPE_MEMBER.getValue())
                .andTypeEqualTo(SubscribeEnum.TYPE_FOLLOW.getValue())
                .andSubscribeIdEqualTo(subscribeId);
        Subscribe subscribe = subscribeMapper.selectOneByExample(example);
        log.info("DynamicServiceImpl -> getSubscribeStatus -> judge -> subscribe != null :{}", subscribe != null);
        if (subscribe != null) {
            log.info("DynamicServiceImpl -> getSubscribeStatus -> end -> return:{}", DynamicConstantEnum.SUBSCRIBE_TRUE.getCode());
            return DynamicConstantEnum.SUBSCRIBE_TRUE.getCode();
        }
        log.info("DynamicServiceImpl -> getSubscribeStatus -> end -> return:{}", DynamicConstantEnum.SUBSCRIBE_FALSE.getCode());
        return DynamicConstantEnum.SUBSCRIBE_FALSE.getCode();
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


    /**
     * 转换图片
     *
     * @param images
     * @return
     */
    private List<String> convertImg(String images) {
        List<String> res = new ArrayList<>();
        if (StringUtils.isNotEmpty(images)) {
            res = Arrays.asList(images.split(","));
        }
        return res;
    }

    /**
     * 获取点赞用户头像
     *
     * @param dynamicId
     * @return  返回头像结果集
     */
    private List<String> getAgreeMemberHeadImage(Long dynamicId) {
        return customMemberMapper.findAgreeHeadImages(dynamicId);
    }

    public Map<Long , Long> baseFavorStatus(Long memberId){
        FavorExample favorExample = new FavorExample();
        favorExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andMemberIdEqualTo(memberId);
        List<Favor> favors = favorMapper.selectByExample(favorExample);
        return favors.stream().collect(Collectors.toMap(Favor::getDynamicId, Favor::getMemberId, (oldV, newV) -> newV));
    }

    public Map<Long , Long> baseLikeStatus(Long memberId){
        LogBehaveDynamicExample example = new LogBehaveDynamicExample();
        example.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andMemberIdEqualTo(memberId).andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_DYNAMIC.getCode());
        List<LogBehaveDynamic> logBehaveDynamics = logBehaveDynamicMapper.selectByExample(example);
        return logBehaveDynamics.stream().collect(Collectors.toMap(LogBehaveDynamic::getSourceId , LogBehaveDynamic::getMemberId , (oldV , newV) -> newV));
    }

    public Map<Long , Long> baseSubscribeStatus(Long memberId){
        SubscribeExample subscribeExample = new SubscribeExample();
        subscribeExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andSubscribeTypeEqualTo(SubscribeEnum.SUBSCRIBE_TYPE_MEMBER.getValue())
                .andMemberIdEqualTo(memberId);
        List<Subscribe> subscribes = subscribeMapper.selectByExample(subscribeExample);
        return subscribes.stream().collect(Collectors.toMap(Subscribe::getSubscribeId , Subscribe::getMemberId , (oldV , newV) -> newV));
    }

}
