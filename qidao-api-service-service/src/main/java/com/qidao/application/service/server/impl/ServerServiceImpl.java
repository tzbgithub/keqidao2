package com.qidao.application.service.server.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.dangdang.ddframe.job.lite.api.strategy.impl.OdevitySortByNameJobShardingStrategy;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qidao.application.common.Query;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.label.Label;
import com.qidao.application.entity.label.LabelExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.relation.LinkLabel;
import com.qidao.application.entity.relation.LinkLabelExample;
import com.qidao.application.entity.relation.LinkSelect;
import com.qidao.application.entity.relation.LinkSelectExample;
import com.qidao.application.entity.server.*;
import com.qidao.application.mapper.config.CustomSelectConfigMapper;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.label.CustomLabelMapper;
import com.qidao.application.mapper.label.LabelMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.relation.CustomLinkLabelMapper;
import com.qidao.application.mapper.relation.LinkLabelMapper;
import com.qidao.application.mapper.relation.LinkSelectMapper;
import com.qidao.application.mapper.server.CustomServerMapper;
import com.qidao.application.mapper.server.ServerMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.SelectConfigEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.dto.ServerPageDto;
import com.qidao.application.model.label.LinkLabelEnum;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.server.*;
import com.qidao.application.service.member.impl.MemberServiceImpl;
import com.qidao.application.service.server.ServerService;
import com.qidao.application.vo.*;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * {
 * ??????????????????Controller?????????
 * ????????????????????????private??????
 * }
 * @date : 2021/1/4 11:21 AM
 */
@Slf4j
@Service("ServerService")
public class ServerServiceImpl implements ServerService {

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Resource
    private LinkLabelMapper linkLabelMapper;
    @Resource
    private LinkSelectMapper linkSelectMapper;
    @Resource
    private MemberMapper memberMapper;
    @Autowired
    private MemberServiceImpl memberService;
    @Resource
    private LabelMapper labelMapper;
    @Resource
    private SelectConfigMapper selectConfigMapper;
    @Resource
    private ServerMapper serverMapper;

    @Resource
    private CustomServerMapper customServerMapper;

    @Resource
    private CustomSelectConfigMapper customSelectConfigMapper;

    @Resource
    private CustomLabelMapper customLabelMapper;
    @Autowired
    CustomLinkLabelMapper customLinkLabelMapper;
    @Resource
    private CustomMemberMapper customMemberMapper;

    /**
     * ????????????????????? Version 2
     * <p>
     * (1) ????????????id??????
     * (2) ????????????????????????
     * (3) ????????????????????????
     * (4) ?????????????????????????????????
     * (5) ??????label?????????link_label????????????????????????????????????????????????server??????
     * ---
     * (1) ??????server
     * (2) ??????server.spec_amount_id?????? ????????????, HashMap
     * (3) ??????link_label????????????server???label????????????,HashMap
     * <p>
     * ????????????????????????????????????status=1??????"?????????"?????????"????????????"
     *
     * @param req ??????????????????
     * @return dto
     */
    @Override
    public ServerDTO list(ServerListReq req) {
        log.info("ServerServiceImpl -> list -> ServerListReq req : {}", req);
        ServerDTO serverDTO = new ServerDTO();
        Integer offset = req.getOffset();
        Integer limit = req.getLimit();
        List<Long> industryId = req.getIndustryId();
        Page<ServerList> page = PageHelper.startPage(offset, limit).doSelectPage(() -> customServerMapper.list(ConstantEnum.NOT_DEL.getByte(), ServerEnum.STATUS_RELEASED.getValue(), req.getSpecAreaId(), req.getLabelIdArray(), req.getKeyword(), req.getQueryTimeStart(), req.getQueryTimeEnd(), industryId));
        List<ServerList> servers = page.getResult();
        List<ServerList> memberServer = servers.stream().filter(s -> s.getMemberId().equals(req.getMemberId())).collect(Collectors.toList());
        List<ServerList> serverPage = servers.stream().sequential().filter(s -> s.getStatus().equals(MemberEnum.VERIFY_STATUS_SUCCESS.getValue())).collect(Collectors.toCollection(() -> memberServer));
        Set<ServerList> serverSet = new HashSet<>(serverPage);
        List<ServerList> serverList = new ArrayList<>(serverSet);
        page.setTotal(serverList.size());
        int size = serverList.size();
        log.info("ServerServiceImpl -> list -> Return -> serverList.size() : {}", size);
        Long[] specAmountIds = new Long[size];
        Long[] serverIds = new Long[size];
        int index = 0;
        for (ServerList server : serverList) {
            specAmountIds[index] = server.getSpecAmountId();
            serverIds[index] = server.getId();
            ++index;
        }
        HashMap<Long, String> amountMap = getSpecAmountMap(specAmountIds);
        HashMap<Long, List<String>> labelMap = getLabelMap(serverIds);
        Long serverId;
        List<ServerListItemRes> serverListItemResList = new LinkedList<>();
        for (ServerList server : serverList) {
            serverId = server.getId();
            ServerListItemRes serverListItemRes = ServerListItemRes.builder()
                    .id(server.getId())
                    .specAmountName(amountMap.get(server.getSpecAmountId()))
                    .solutionTime(server.getSolutionTime())
                    .title(server.getTitle())
                    .createTime(server.getCreateTime())
                    .description(server.getDescription())
                    .addressProvinceName(server.getAddressProvinceName())
                    .addressCityName(server.getAddressCityName())
                    .labelName(labelMap.get(serverId))
                    .build();
            serverListItemResList.add(serverListItemRes);
        }
        ServicePage<List<ServerListItemRes>> servicePage = new ServicePage<>();
        BeanUtils.copyProperties(page, servicePage);
        servicePage.setResult(serverListItemResList);
        serverDTO.setServerList(servicePage);
        log.info("ServerServiceImpl -> list -> Return -> ServerDTO : {}", serverDTO);
        return serverDTO;
    }

    /**
     * ???????????????serverId????????????????????????????????????
     * (1) ??????server??????
     * (2) ??????server?????????spec_area_id????????????id ?????????????????????String
     * (3) ??????server?????????spec_amount_id????????????id ?????????????????????String
     *
     * @param req ??????????????????
     * @return dto
     */
    @Override
    public ServerDTO detail(ServerDetailReq req) {
        log.info("ServerServiceImpl -> detail -> ServerDetailReq req : {}", req);
        ServerDTO serverDTO = new ServerDTO();
        Long serverId = req.getServerId();
        ServerExample serverExample = new ServerExample();
        ServerExample.Criteria criteria = serverExample.createCriteria();
        criteria.andIdEqualTo(serverId);
        criteria.andDelFlagEqualTo((byte) ServerEnum.DELETE_FLAG_NO.getValue());
        Server server = serverMapper.selectOneByExample(serverExample);
        boolean serverIsNull = ObjectUtil.isNull(server);
        if (serverIsNull) {
            log.info("ServerServiceImpl -> detail -> Return -> ServerDTO : {}", serverDTO);
            return serverDTO;
        }
        String specArea = getSpecAreaById(server.getSpecAreaId());
        String specAmount = getSpecAmountById(server.getSpecAmountId());
        ServerDetailRes serverDetailRes = ServerDetailRes.builder()
                .title(server.getTitle())
                .specArea(specArea)
                .specFund(specAmount)
                .addressProvinceName(server.getAddressProvinceName())
                .addressCityName(server.getAddressCityName())
                .addressAreaName(server.getAddressAreaName())
                .createTime(server.getCreateTime())
                .solutionTime(server.getSolutionTime())
                .description(server.getDescription())
                .thumb(server.getThumb())
                .url(server.getUrl())
                .status(server.getStatus())
                .memberIdPartyA(server.getMemberIdPartyA())
                .memberIdPartyB(server.getMemberIdPartyB())
                .organizedIdPartyA(server.getOrganizedIdPartyA())
                .organizedIdPartyB(server.getOrganizedIdPartyB())
                .build();
        serverDTO.setServerDetailRes(serverDetailRes);
        log.info("ServerServiceImpl -> detail -> Return -> ServerDTO : {}", serverDTO);
        return serverDTO;
    }

    /**
     * ???????????????spec_amount_id????????????select_config????????????"????????????"
     *
     * @param specAmountIds ?????????id??????
     * @return hashmap
     */

    private HashMap<Long, String> getSpecAmountMap(Long[] specAmountIds) {
        log.info("ServerServiceImpl -> getSpecAmountMap -> Long[] specAmountIds : {}", Arrays.toString(specAmountIds));
        HashMap<Long, String> map = new HashMap<>();
        if (specAmountIds.length < 1) {
            return map;
        }
        List<SelectConfig> specAmountList = customSelectConfigMapper.getSelectConfigByIds(ConstantEnum.NOT_DEL.getByte(), specAmountIds);
        int size = specAmountList.size();
        log.info("ServerServiceImpl -> getSpecAmountMap -> specAmountList.size() : {}", size);
        boolean listIsEmpty = 0 == size;
        log.info("ServerServiceImpl -> getSpecAmountMap -> boolean listIsEmpty : {}", listIsEmpty);
        if (listIsEmpty) {
            return map;
        }
        for (SelectConfig selectConfig : specAmountList) {
            map.put(selectConfig.getId(), selectConfig.getVal());
        }
        return map;
    }

    /**
     * ??????????????? server.id???????????????link_label???label??????????????????"??????"?????????"??????"??????(??????????????????)
     * ???????????????List<Label>???????????????Label(id,val)?????????id?????????sevrer???id
     *
     * @param serverIds ?????????id??????
     * @return hashmap
     */
    private HashMap<Long, List<String>> getLabelMap(Long[] serverIds) {
        log.info("ServerServiceImpl -> getLabelMap -> Long[] serverIds : {}", Arrays.toString(serverIds));
        HashMap<Long, List<String>> map = new HashMap<>();
        if (serverIds.length < 1) {
            return map;
        }
        List<Label> labelList = customLabelMapper.getLabelsBySourceIds(serverIds, LinkLabelEnum.LINK_LABEL_SERVER.getCode());
        int size = labelList.size();
        log.info("ServerServiceImpl -> getLabelMap -> labelList.size() : {}", size);
        boolean listIsEmpty = 0 == size;
        log.info("ServerServiceImpl -> getLabelMap -> boolean listIsEmpty : {}", listIsEmpty);
        if (listIsEmpty) {
            return map;
        }
        Long id;
        String val;
        for (Label label : labelList) {
            id = label.getId();
            val = label.getVal();
            List<String> labels = map.getOrDefault(id, new LinkedList<>());
            labels.add(val);
            map.put(id, labels);
        }
        return map;
    }

    /**
     * @param serverIds ??????id??????
     * @param labelIds  ??????id??????
     * @return ???????????????????????????id??????
     * @deprecated ??????"??????id??????"?????????"??????id??????"???
     * ????????????????????????link_label???source_id??????serverIds???????????????label_id??????labelIds???source_id
     * ??????????????????????????????????????????????????????serverIds??????
     */
    private List<Long> filterIdsWithLabelIds(Long[] serverIds, Long[] labelIds) {
        log.info("ServerServiceImpl -> filterIdsWithLabelIds -> Long[] serverIds : {} , Long[] labelIds : {}", Arrays.toString(serverIds), Arrays.toString(labelIds));
        List<Long> serverIdList = customServerMapper.filterIdsWithLabelIds(serverIds, labelIds);
        log.info("ServerServiceImpl -> filterIdsWithLabelIds -> Return -> List<Long> : {}", serverIdList);
        return serverIdList;
    }

    /**
     * ???????????????serverIds?????????????????????serverList
     * ?????????serverList????????????????????????
     *
     * @param serverList ???serverList
     * @param serverIds  ????????????serverIds
     * @return ??????????????????serverList
     */
    private List<Server> filterServerWithServerIds(List<Server> serverList, List<Long> serverIds) {
        log.info("ServerServiceImpl -> filterServerWithServerIds -> List<Server> serverList : {} , List<Long> serverIds : {}", serverList, serverIds);
        List<Server> filteredServerList = serverList.stream().filter(server -> serverIds.contains(server.getId())).collect(Collectors.toList());
        log.info("ServerServiceImpl -> filterServerWithServerIds -> Return -> List<Server> : {}", filteredServerList);
        return filteredServerList;
    }

    /**
     * ???????????????spec_area_id??????"????????????"
     *
     * @param specAreaId spec_area_id
     * @return ????????????
     */
    private String getSpecAreaById(Long specAreaId) {
        log.info("ServerServiceImpl -> getSpecAreaById -> Long specAreaId: {} ", specAreaId);
        String specArea = null;
        List<SelectConfig> list = customSelectConfigMapper.getSelectConfigByIds(ConstantEnum.NOT_DEL.getByte(), new Long[]{specAreaId});
        boolean listIsNotEmpty = ObjectUtil.isNotEmpty(list);
        log.info("ServerServiceImpl -> getSpecAreaById -> boolean listIsNotEmpty : {} ", listIsNotEmpty);
        if (listIsNotEmpty) {
            specArea = list.get(0).getVal();
        }
        log.info("ServerServiceImpl -> getSpecAreaById -> Return -> specArea : {} ", specArea);
        return specArea;
    }

    /**
     * ???????????????spec_amount_id??????"????????????"
     *
     * @param specAmountId spec_amount_id
     * @return ????????????
     */
    private String getSpecAmountById(Long specAmountId) {
        log.info("ServerServiceImpl -> getSpecAmountById -> Long specAmountId: {} ", specAmountId);
        String amount = null;
        List<SelectConfig> list = customSelectConfigMapper.getSelectConfigByIds(ConstantEnum.NOT_DEL.getByte(), new Long[]{specAmountId});
        boolean listIsNotEmpty = ObjectUtil.isNotEmpty(list);
        log.info("ServerServiceImpl -> getSpecAmountById -> boolean listIsNotEmpty : {} ", listIsNotEmpty);
        if (listIsNotEmpty) {
            amount = list.get(0).getVal();
        }
        log.info("ServerServiceImpl -> getSpecAmountById -> Return -> amount : {} ", amount);
        return amount;
    }


    /**
     * ????????????
     *
     * @param serverInsesrtReq
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertServer(ServerInsesrtReq serverInsesrtReq) {
        // todo ??????????????????vip??????
        boolean verify = memberService.verifyIsVip(serverInsesrtReq.getMemberIdPartyA());
        if(!verify){
            throw new BusinessException(ServerErrorEnum.VIP_SERVER_ERROR);
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(serverInsesrtReq.getMemberIdPartyA()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(ServerErrorEnum.SENDPER_NOTFOUND_ERROR);
        }
        Long organizationId = member.getOrganizationId();
        if (organizationId == null) {
            log.info(" ServerServiceImpl -> insertServer --- ????????????????????? ????????????----");
            throw new BusinessException(ServerErrorEnum.UNLAWFUL_SERVER_ERROR);
        }

        int imageType = serverInsesrtReq.getImageType();
        if (imageType == ServerEnum.VIDEO_TYPE.getValue()) {
            String thumb = serverInsesrtReq.getThumb();
            if (StringUtils.isEmpty(thumb)) {
                throw new BusinessException(ServerErrorEnum.IMAGENOTHUB_UPLOAD_ERROR);
            }
        }
        List<String> ur = serverInsesrtReq.getUrl();
        if (CollUtil.isNotEmpty(ur)) {
            if (ur.size() > 9) {
                throw new BusinessException(ServerErrorEnum.IMAGE_SIZE_ERROR);
            }
        }
        log.info(" ServerServiceImpl -> insertServer ---params:{}", serverInsesrtReq);
        String urls = null;
        List<Long> labelIds = serverInsesrtReq.getLabelId();
        List<String> url = serverInsesrtReq.getUrl();
        if (CollUtil.isNotEmpty(url)) {
            urls = String.join(",", url);
        }
        long serverId = snowflakeIdWorker53.nextId();
        Server server = new Server();
        server.setAddressAreaId(serverInsesrtReq.getAddressAreaId()
        ).setAddressAreaName(serverInsesrtReq.getAddressAreaName()).
                setAddressCityId(serverInsesrtReq.getAddressCityId()).
                setAddressCityName(serverInsesrtReq.getAddressCityName()).
                setAddressProvinceId(serverInsesrtReq.getAddressProvinceId()).
                setAddressProvinceName(serverInsesrtReq.getAddressProvinceName()).
                setDescription(serverInsesrtReq.getDescription()).
                setDelFlag(ConstantEnum.NOT_DEL.getByte()).
                setCreateTime(LocalDateTime.now()).setIndustryId(member.getIndustryId()).
                setTitle(serverInsesrtReq.getTitle()).
                setId(serverId).setSpecAreaId(serverInsesrtReq.getSpecAreaId()).
                setStatus(serverInsesrtReq.getStatus()).
                setSpecAmountId(serverInsesrtReq.getSpecAmountId()).
                setSolutionTime(LocalDateTime.of(serverInsesrtReq.getSolutionTime(), LocalTime.MIN)).
                setMemberIdPartyA(serverInsesrtReq.getMemberIdPartyA()).
                setOrganizedIdPartyA(organizationId).setType(serverInsesrtReq.getType());
        if (imageType != ServerEnum.TEXT_TYPE.getValue()) {
            server.setUrl(urls).setThumb(serverInsesrtReq.getThumb());
        }
        int type = serverInsesrtReq.getType();
        if (type != ServerEnum.STATUS_DRAFT.getValue()) {
            List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(serverInsesrtReq.getMemberIdPartyA());
            List<Long> industryIds = memberIndustry.stream().map(SelectConfigResp::getId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(industryIds)){
                //??????????????????
                LinkSelectExample linkSelectExample = new LinkSelectExample();
                linkSelectExample.createCriteria()
                        .andSourceIdEqualTo(serverId)
                        .andTypeEqualTo(DictConstantEnum.INDUSTRY.getId())
                        .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
                linkSelectMapper.updateByExampleSelective(LinkSelect.builder().delFlag(ConstantEnum.DELETED.getByte()).build() , linkSelectExample);

                List<LinkSelect> linkSelects = new ArrayList<>();
                industryIds.forEach(industryId -> {
                    linkSelects.add(LinkSelect.builder()
                            .id(snowflakeIdWorker53.nextId())
                            .delFlag(ConstantEnum.NOT_DEL.getByte())
                            .sourceId(serverId)
                            .selectConfigId(industryId)
                            .type(DictConstantEnum.INDUSTRY.getId())
                            .build());
                });
                linkSelectMapper.batchInsert(linkSelects);
            }
        }
        serverMapper.insertSelective(server);
        log.info(" ServerServiceImpl -> insertServer --- ??????????????? ---");
        if (CollUtil.isNotEmpty(labelIds)) {
            labelIds.stream().forEach(x -> {
                LinkLabel label = new LinkLabel();
                label.setSourceId(serverId).
                        setType(1).setDelFlag(ConstantEnum.NOT_DEL.getByte()).setId(snowflakeIdWorker53.nextId())
                        .setCreateTime(LocalDateTime.now()).setLabelId(x);
                linkLabelMapper.insertSelective(label);
            });
        }
        LinkSelect linkSelect = new LinkSelect();
        linkSelect.setType(0).setDelFlag(ConstantEnum.NOT_DEL.getByte()).setSourceId(serverId).setSelectConfigId(serverInsesrtReq.getSpecAreaId()).
                setCreateTime(LocalDateTime.now()).setId(snowflakeIdWorker53.nextId());
        linkSelectMapper.insertSelective(linkSelect);
    }


    /**
     * ????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void underServer(UnderServerReq underServerReq) {
        log.info(" ServerServiceImpl -> underServer ---??????????????? params:{}", underServerReq);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(underServerReq.getMemberId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(ServerErrorEnum.SENDPER_NOTFOUND_ERROR);
        }
        Long organizationId = member.getOrganizationId();
        if (organizationId == null) {
            log.info(" ServerServiceImpl -> insertServer --- ????????????????????? ????????????----");
            throw new BusinessException(ServerErrorEnum.UNLAWFUL_SERVER_ERROR);
        }
        ServerExample serverExample = new ServerExample();
        serverExample.createCriteria().andIdEqualTo(underServerReq.getServerId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Server server = serverMapper.selectOneByExample(serverExample);
        if (server == null) {
            throw new BusinessException(ServerErrorEnum.TARGET_NOTFOUND_ERROR);
        }
        if (server.getStatus().intValue() == ServerEnum.STATUS_ACCEPTED.getValue()) {
            throw new BusinessException(ServerErrorEnum.UNDERTAKING_SERVER_ERROR);
        }
        server.setStatus(ServerEnum.STATUS_ACCEPTED.getValue()).setOrganizedIdPartyB(organizationId).setMemberIdPartyB(member.getId());
        serverMapper.updateByExampleSelective(server, serverExample);
    }

    /**
     * ??????????????????????????????
     */
    @Override
    public PageInfo<InfoServerPageRep> infoServerPage(MemberServerReq req) {
        log.info(" ServerServiceImpl -> infoServerPage --- params {} ----", req);
        boolean sourceIdIsNull = req.getSourceId() == null;
        Query query = new Query();
        query.put("memberId", String.valueOf(sourceIdIsNull ? req.getMemberId() : req.getSourceId()));
        boolean verify = false;
        if (sourceIdIsNull || req.getSourceId().equals(req.getMemberId())) {
            verify = true;
        }
        query.put("verify", verify);
        PageHelper.startPage(req.getOffset(), req.getLimit());
        Page<InfoServerPageRep> servers = customServerMapper.infoServerPage(query);
        PageInfo<InfoServerPageRep> result = new PageInfo<>(servers);
        List<InfoServerPageRep> list = servers.getResult();
        log.info(" ServerServiceImpl -> infoServerPage --- InfoServerPageRep ???????????????----");
        if (CollUtil.isNotEmpty(list)) {
            for (InfoServerPageRep x : list) {
                if (StrUtil.isNotEmpty(x.getLabel())){
                    x.setLabelName(Arrays.asList(x.getLabel().split(",")));
                }
            }
        }
        log.info(" ServerServiceImpl -> infoServerPage --- InfoServerPageRep ---- end");
        return result;
    }


    @Override
    public PageInfo<InfoOrganizationServicePageRep> infoOrganizationServerPage(ServerPageDto req) {
        Query query = new Query();
        query.put("organizationId", String.valueOf(req.getOrganizationId()));
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andOrganizationIdEqualTo(req.getOrganizationId());
        List<Member> members = memberMapper.selectByExample(memberExample);
        List<Long> memberIds = members.stream().map(Member::getId).collect(Collectors.toList());
        query.put("verify", memberIds.contains(req.getMemberId()));
        PageHelper.startPage(req.getOffset(), req.getLimit());
        Page<InfoOrganizationServicePageRep> servers = customServerMapper.infoOrganizationServerPage(query);

        PageInfo<InfoOrganizationServicePageRep> result = new PageInfo<>(servers);
        List<InfoOrganizationServicePageRep> list = servers.getResult();
        log.info(" ServerServiceImpl -> infoServerPage --- InfoServerPageRep ???????????????----");
        if (CollUtil.isNotEmpty(list)) {
            for (InfoOrganizationServicePageRep x : servers.getResult()) {
                Long specAreaId = x.getSpecAreaId();
                SelectConfigExample selectConfigExample = new SelectConfigExample();
                selectConfigExample.createCriteria().andIdEqualTo(specAreaId).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
                SelectConfig selectConfig = selectConfigMapper.selectOneByExample(selectConfigExample);
                if (selectConfig != null) {
                    x.setSpecAreaName(selectConfig.getVal());
                }
                selectConfigExample.clear();
                List<LinkLabelVo> labels = customLinkLabelMapper.findLinkLabelBySourceId(x.getId());
                if (CollUtil.isEmpty(labels)) {
                    log.info(" ServerServiceImpl -> infoServerPage --- ???????????????----");
                } else {
                    log.info(" ServerServiceImpl -> infoServerPage --- ???????????????----");
                    List<String> labelName = labels.stream().map(label -> label.getVal()).collect(Collectors.toList());
                    x.setLabelName(labelName);
                }
            }
        }
        return result;
    }

    /**
     * ????????????
     */
    @Override
    public PageInfo<InfoOrganizationServicePageRep> infoOrganizationServerContentPage(ServerContentReq req) {
        Query query = new Query();
        query.put("industryId", String.valueOf(req.getIndustryId()));
        query.put("memberId" , req.getMemberId());
        PageHelper.startPage(req.getOffset(), req.getLimit());
        Page<InfoOrganizationServicePageRep> servers = customServerMapper.infoOrganizationServerContentPage(query);
        PageInfo<InfoOrganizationServicePageRep> res = new PageInfo<>(servers);
        List<InfoOrganizationServicePageRep> result = servers.getResult();
        log.info(" ServerServiceImpl -> infoServerPage --- infoOrganizationServerContentPage ???????????????----");
        if (CollUtil.isNotEmpty(result)) {
            for (InfoOrganizationServicePageRep x : result) {
                Long specAreaId = x.getSpecAreaId();
                SelectConfigExample selectConfigExample = new SelectConfigExample();
                selectConfigExample.createCriteria().andIdEqualTo(specAreaId).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
                SelectConfig selectConfig = selectConfigMapper.selectOneByExample(selectConfigExample);
                if (selectConfig != null) {
                    x.setSpecAreaName(selectConfig.getVal());
                }
                selectConfigExample.clear();
                List<LinkLabelVo> labels = customLinkLabelMapper.findLinkLabelBySourceId(x.getId());
                if (CollUtil.isEmpty(labels)) {
                    log.info(" ServerServiceImpl -> infoServerPage --- ???????????????----");
                } else {
                    log.info(" ServerServiceImpl -> infoServerPage --- ???????????????----");
                    List<String> labelName = labels.stream().map(label -> label.getVal()).collect(Collectors.toList());
                    x.setLabelName(labelName);
                }
            }
        }
        return res;
    }

    @Override
    public List<ServerIndustryRep> selectHaveIndustryServer() {

        ArrayList<ServerIndustryRep> serverIndustryReps = new ArrayList<>();
        List<ServerIndustryRep> serverIndustryRep = customServerMapper.selectHaveIndustryServer();
        if (CollUtil.isNotEmpty(serverIndustryRep)) {
            List<ServerIndustryRep> ParServerIndustryRep = serverIndustryRep.stream().filter(t -> t.getPid().intValue() == 0).collect(Collectors.toList());
            ParServerIndustryRep.stream().forEach(x -> {
                ServerIndustryRep serverIndustryRepDto = new ServerIndustryRep();
                serverIndustryRepDto.setId(x.getId()).setPid(x.getPid()).setSequence(x.getVal());
                serverIndustryReps.add(serverIndustryRepDto);
            });
            //?????????????????????
            List<ServerIndustryRep> SonServerIndustryRep = serverIndustryRep.stream().filter(s -> s.getPid().intValue() != 0).collect(Collectors.toList());
            serverIndustryReps.stream().forEach(p -> {
                ArrayList<ServerIndustryRep> sonServerIndustryReps = new ArrayList<>();
                SonServerIndustryRep.stream().forEach(y -> {
                    if (y.getPid().intValue() == p.getId().intValue()) {
                        sonServerIndustryReps.add(y);
                    }
                });
                p.setServerIndustryRep(sonServerIndustryReps);
            });
        }
        return serverIndustryReps;

    }

    /**
     * ????????????
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteServer(Long id) {
        Server server = serverMapper.selectByPrimaryKey(id);
        if (server == null) {
            throw new BusinessException(ServerErrorEnum.TARGET_NOTFOUND_ERROR);
        }
        server.setDelFlag((byte) ServerEnum.DELETE_FLAG_YES.getValue());
        ServerExample serverExample = new ServerExample();
        serverExample.createCriteria().andIdEqualTo(id);
        serverMapper.updateByExampleSelective(server, serverExample);
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    @Override
    public int findServerSize(Long memberId) {
        int serverSize = customServerMapper.findServerSize(null == memberId ? null : String.valueOf(memberId));
        return serverSize;
    }

    @Override
    public ServicePage<List<ServerPageRes>> getServerPage(ServerListReq req) {
        log.info("ServerServiceImpl -> getServerPage -> start -> params -> {}",req);
        ServicePage<List<ServerPageRes>> res = new ServicePage<>();
        Page<ServerPage> page = PageUtil.start(req, () -> customServerMapper.getServerList(ServerPageDo.builder().memberId(req.getMemberId()).industryIds(req.getIndustryId()).labels(ArrayUtil.isNotEmpty(req.getLabelIdArray()) ?  Arrays.asList(req.getLabelIdArray()) : null).specAreaId(req.getSpecAreaId()).build()));
        BeanUtils.copyProperties(page , res);
        List<ServerPage> result = page.getResult();
        List<ServerPageRes> serverPageRes = new ArrayList<>();
        result.forEach(serverPage -> {
            ServerPageRes serverRes = new ServerPageRes();
            BeanUtils.copyProperties(serverPage , serverRes);
            if (StrUtil.isNotEmpty(serverPage.getLabels())){
                serverRes.setLabelName(Arrays.asList(serverPage.getLabels().split(",")));
            }
            serverPageRes.add(serverRes);
        });
        res.setResult(serverPageRes);
        log.info("ServerServiceImpl -> getServerPage -> end");
        return res;
    }

    @Override
    public ServicePage<List<ServerMemberPageRes>> listServerMember(ServerMemberPageReq req) {
        ServicePage<List<ServerMemberPageRes>> res = new ServicePage<>();
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        if (ObjectUtil.isEmpty(member)){
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        if (member.getOrganizationId() == null){
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_ORGAN);
        }
        Page<InfoServerPageRep> page = PageUtil.start(req, () -> customServerMapper.listServerMemberId(member.getOrganizationId()));
        BeanUtils.copyProperties(page , res);
        List<InfoServerPageRep> result = page.getResult();
        List<ServerMemberPageRes> pageResList = new ArrayList<>();
        result.forEach(serverPage -> {
            ServerMemberPageRes pageRes = new ServerMemberPageRes();
            if (StrUtil.isNotEmpty(serverPage.getLabel())){
                pageRes.setLabelName(Arrays.asList(serverPage.getLabel().split(",")));
            }
            BeanUtils.copyProperties(serverPage , pageRes);
            pageResList.add(pageRes);
        });
        res.setResult(pageResList);
        return res;
    }
}
