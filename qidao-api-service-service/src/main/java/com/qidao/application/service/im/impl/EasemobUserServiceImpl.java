package com.qidao.application.service.im.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qidao.application.entity.organization.MemberOrganizationTypeDO;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.easemob.*;
import com.qidao.application.model.easemob.enums.EasemobExceptionEnum;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberInfoRes;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.service.im.EasemobUserService;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.organization.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EasemobUserServiceImpl implements EasemobUserService {
    @Autowired
    private EasemobUtil easemobUtil;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MemberService memberService;

    @Override
    public EasemobBaseDTO registerNoToken(EasemobRegisterNoTokenReq req) {
        try {
            log.info("EasemobUserServiceImpl -> registerNoToken -> start -> req -> {}", req);
            String reqBody = new Gson().toJson(req.getUsers());
            String url = easemobUtil.register();
            EasemobBaseDTO result = easemobUtil.post(url, reqBody, EasemobBaseDTO.class);
            log.info("EasemobUserServiceImpl -> registerNoToken -> end -> url -> {} result -> {}", url, result);
            return result;
        } catch (BusinessException e) {
            if(EasemobExceptionEnum.REPEAT.getCode().equals(e.getCode())) {
                Long username = req.getUsers().get(0).getUsername();
                log.info("EasemobUserServiceImpl -> registerNoToken -> 用户重复注册 -> {}",username);
                EasemobBaseDTO result = new EasemobBaseDTO();
                result.setActivated(true);
                result.setUsername(String.valueOf(username));
                return result;
            } else {
                log.info("EasemobUserServiceImpl -> registerNoToken -> business exception ", e);
                throw e;
            }
        }
    }

    @Override
    public EasemobBaseDTO register(EasemobRegisterReq req) {
        try{
            log.info("EasemobUserServiceImpl -> register -> start -> req -> {}", req);
            String reqBody = new Gson().toJson(req.getUsers());
            String url = easemobUtil.register();
            EasemobBaseDTO result = easemobUtil.post(url, reqBody, EasemobBaseDTO.class);
            log.info("EasemobUserServiceImpl -> register -> end -> url -> {} result -> {}", url, result);
            return result;
        } catch (BusinessException e) {
            if(EasemobExceptionEnum.REPEAT.getCode().equals(e.getCode())) {
                return new EasemobBaseDTO();
            } else {
                throw e;
            }
        }
    }

    @Override
    public List<EasemobBaseDTO> registerBatch(EasemobRegisterBatchReq req) {
        //TODO 实际还需要考虑重复注册同一个用户的情况，这里说暂时不需要考虑 (ashiamd)[2021-03-03 ~ ]
        log.info("EasemobUserServiceImpl -> register -> start -> req -> {}", req);
        String reqBody = new Gson().toJson(req.getUsers());
        String url = easemobUtil.register();
        List<EasemobBaseDTO> result = easemobUtil.postForList(url, reqBody, EasemobBaseDTO.class);
        log.info("EasemobUserServiceImpl -> register -> end -> url -> {} result -> {}", url, result);
        return result;
    }

    @Override
    public EasemobBaseDTO getUser(String username) {
        log.info("EasemobUserServiceImpl -> getUser -> start -> param -> {}", username);
        String url = easemobUtil.getUser(username);
        EasemobBaseDTO easemobBaseDTO = easemobUtil.get(url);
        log.info("EasemobUserServiceImpl -> getUser -> url -> {} result -> {}", url, easemobBaseDTO);
        return easemobBaseDTO;
    }

    @Override
    public List<EasemobBaseDTO> getUserBatch(EasemobGetUserBatchReq req) {
        //TODO 这里暂时没使用到 cursor和limit参数 (ashiamd)[2021-03-23 ~ ]
        log.info("EasemobUserServiceImpl -> getUserBatch -> start -> req -> {}", req);
        String url = easemobUtil.getUserBatch();
        List<EasemobBaseDTO> result = easemobUtil.getForList(url, EasemobBaseDTO.class);
        log.info("EasemobUserServiceImpl -> getUserBatch -> end -> url -> {} result -> {}", url, result);
        return result;
    }

    @Override
    public EasemobBaseDTO deleteUser(String username) {
        //TODO 实际还需要考虑重复删除同一个/不存在的用户的情况，这里说暂时不需要考虑 (ashiamd)[2021-03-03 ~ ]
        log.info("EasemobUserServiceImpl -> deleteUser -> start -> param -> {}", username);
        String url = easemobUtil.deleteUser(username);
        EasemobBaseDTO easemobBaseDTO = easemobUtil.delete(url);
        log.info("EasemobUserServiceImpl -> deleteUser -> url -> {} result -> {}", url, easemobBaseDTO);
        return easemobBaseDTO;
    }

    @Override
    public Object deleteUserBatch() {
        // todo 实现 (Autuan)[21.2.15]
        return null;
//        Map<String, Object> param = new HashMap<>();
//        String respondStr = HttpUtil.post(host + "/{org_name}/{app_name}/token", param);
//        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
//        JSONObject respondObj = JSONObject.parseObject(respondStr);
//        return respondObj;
    }

    @Override
    public EasemobUpdatePwdDTO updatePwd(EasemobUpdatePwdReq req) {
        //TODO 实际还需要修改不存在的用户的密码的情况，这里说暂时不需要考虑 (ashiamd)[2021-03-03 ~ ]
        log.info("EasemobUserServiceImpl -> updatePwd -> start -> EasemobUpdatePwdReq req -> {}", req);
        String url = easemobUtil.updatePwd(String.valueOf(req.getUsername()));
        easemobUtil.putForList(url, req.newPasswordJson());
        EasemobUpdatePwdDTO result = EasemobUpdatePwdDTO.builder()
                .updateSuccess(true)
                .build();
        log.info("EasemobUserServiceImpl -> updatePwd -> result -> {}", result);
        return result;
    }

    @Override
    public EasemobBaseDTO updateNickName(EasemobUpdateNicknameReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里说暂时不需要考虑 (ashiamd)[2021-03-04 ~ ]
        log.info("EasemobUserServiceImpl -> updateNickName -> start -> EasemobUpdateNickNameReq req -> {}", req);
        String url = easemobUtil.updateNickName(String.valueOf(req.getUsername()));
        EasemobBaseDTO result = easemobUtil.put(url, req.nicknameJson(), EasemobBaseDTO.class);
        log.info("EasemobUserServiceImpl -> updateNickName -> result -> {}", result);
        return result;
    }

    @Override
    public EasemobBaseDTO notifyShowType(EasemobNotifyShowTypeReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里说暂时不需要考虑 (ashiamd)[2021-03-04 ~ ]
        log.info("EasemobUserServiceImpl -> notifyShowType -> start -> EasemobNotifyShowTypeReq req -> {}", req);
        String url = easemobUtil.notifyShowType(String.valueOf(req.getUsername()));
        EasemobBaseDTO result = easemobUtil.put(url, req.notificationDisplayStyleJson(), EasemobBaseDTO.class);
        log.info("EasemobUserServiceImpl -> notifyShowType -> result -> {}", result);
        return result;
    }

    @Override
    public EasemobBaseDTO setNotDisturb(EasemobSetNotDisturbReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里说暂时不需要考虑 (ashiamd)[2021-03-04 ~ ]
        log.info("EasemobUserServiceImpl -> setNotDisturb -> start -> EasemobSetNotDisturbReq req -> {}", req);
        String url = easemobUtil.setNotDisturb(String.valueOf(req.getUsername()));
        EasemobBaseDTO result;
        if (req.getNotificationNoDisturbing() == 1) {
            result = easemobUtil.put(url, req.openJson(), EasemobBaseDTO.class);
        } else {
            result = easemobUtil.put(url, req.closeJson(), EasemobBaseDTO.class);
        }
        log.info("EasemobUserServiceImpl -> setNotDisturb -> result -> {}", result);
        return result;
    }

    @Override
    public EasemobFriendRes addFriend(EasemobAddFriendReq req) {
        log.info("EasemobUserServiceImpl -> addFriend -> req -> {}", req);
        Long friendMemberId = req.getFriendMemberId();
        String url = easemobUtil.addFriend(req.getMemberId(), friendMemberId);
        EasemobBaseDTO result = easemobUtil.post(url);
        log.info("EasemobUserServiceImpl -> addFriend -> url -> {} result -> {}", url, result);
        List<MemberOrganizationTypeDO> queryList = organizationService.queryOrganizationType(Arrays.asList(friendMemberId));
        return queryList.stream()
                .map(this::convertToEasemobFriend)
                .findFirst().orElse(new EasemobFriendRes());
    }

    @Override
    public EasemobFriendRes registerAndAddFriend(EasemobAddFriendReq req) {
        Long friendMemberId = req.getFriendMemberId();
        boolean needRegister = false;
        MemberInfoRes friendInfo = memberService.getMemberByMemberId(friendMemberId);
        if(null == friendInfo || null == friendInfo.getId()) {
            throw new BusinessException(EasemobExceptionEnum.FRIEND);
        }
        Integer organizationType = friendInfo.getOrganizationType();

        if(OrganizationEnum.TYPE_LABORATORY.getValue().equals(organizationType) ) {
            boolean isVerify =MemberEnum.VERIFY_STATUS_SUCCESS.getValue().equals(friendInfo.getVerifyStatus()) ;
            if(!isVerify) {
                log.info("EasemobUserServiceImpl -> registerAndAddFriend -> 实验室未审核通过");
                throw new BusinessException(EasemobExceptionEnum.FRIEND);
            }
        }
        try {
            EasemobBaseDTO user = getUser(String.valueOf(friendMemberId));
            log.info("EasemobUserServiceImpl -> registerAndAddFriend -> user -> {}",user);
        } catch (BusinessException e) {
            if (e.getCode().equals(EasemobExceptionEnum.USER.getCode())) {
                needRegister = true;
            } else {
                throw e;
            }
        }

        log.info("EasemobUserServiceImpl -> registerAndAddFriend -> needRegister -> {}",needRegister);
        boolean firstAdd = false;
        if (needRegister) {
            EasemobRegisterReq registerReq = new EasemobRegisterReq();
            EasemobRegisterReq.User user = new EasemobRegisterReq.User();
            user.setUsername(friendMemberId);
            user.setPassword(Md5Util.getCrypt(String.valueOf(friendMemberId)));
            registerReq.setUsers(Arrays.asList(user));
            register(registerReq);

            firstAdd=true;
        } else {
            List<EasemobFriendRes> friendList = getFriendList(req.getMemberId());
            firstAdd = friendList.stream()
                    .noneMatch(item -> item.getMemberId().equals(req.getFriendMemberId()));
        }

        log.info("EasemobUserServiceImpl -> registerAndAddFriend -> addFriend -> start ");
        EasemobFriendRes res = addFriend(req);

        log.info("EasemobUserServiceImpl -> registerAndAddFriend -> firstAdd -> {} ",firstAdd);
        res.setFirstAdd(firstAdd);
        return res;
    }

    @Override
    public EasemobBaseDTO removeFriend(EasemobRemoveFriendReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里说暂时不需要考虑 (ashiamd)[2021-03-04 ~ ]
        log.info("EasemobUserServiceImpl -> removeFriend -> start -> EasemobRemoveFriendReq req -> {}", req);
        String url = easemobUtil.removeFriend(req.getOwnerUsername(),req.getFriendUsername());
        EasemobBaseDTO result = easemobUtil.delete(url, null, EasemobBaseDTO.class);
        log.info("EasemobUserServiceImpl -> removeFriend -> result -> {}", result);
        return result;
    }

    @Override
    public List<EasemobFriendRes> getFriendList(Long memberId) {
        log.info("EasemobUserServiceImpl -> getFriendList -> param -> {}", memberId);
        String url = easemobUtil.getFriendList(memberId);
        List<String> friendIdList = easemobUtil.getForList(url, String.class);

        log.info("EasemobUserServiceImpl -> getFriendList -> url -> {} friendIdList -> {}", url, friendIdList);
        if (CollUtil.isEmpty(friendIdList)) {
            return new ArrayList<>();
        }

        List<Long> idList = friendIdList.stream()
                .map(Long::parseLong).collect(Collectors.toList());

        List<MemberOrganizationTypeDO> queryList = organizationService.queryOrganizationType(idList);

        return queryList.stream()
                .map(this::convertToEasemobFriend)
                .collect(Collectors.toList());
    }

    @Override
    public EasemobStatusRes statusOne(String username) {
        log.info("EasemobUserServiceImpl -> statusOne -> start -> param -> {}", username);
        String url = easemobUtil.getStatusUrl(username);
        List<JSONObject> result = easemobUtil.getForList(url, JSONObject.class);
        log.info("EasemobUserServiceImpl -> statusOne -> url -> {} result -> {}", url, result);
        if (CollUtil.isNotEmpty(result)) {
            String string = result.get(0).getString(username);
            boolean online = "online".equals(string);
            return new EasemobStatusRes(username, online);
        }
        return EasemobStatusRes.offline(username);
    }

    @Override
    public List<EasemobStatusRes> statusBatch(EasemobStatusBatchReq req) {
        log.info("EasemobUserServiceImpl -> statusBatch -> start -> param -> {}", req);
        String url = easemobUtil.statusBatchUrl();
        /*
        [{"155":"offline"},{"1555":"offline"}]
         */
        List<JSONObject> queryResponse = easemobUtil.postForList(url, JSONUtil.toJsonStr(req), JSONObject.class);
        List<EasemobStatusRes> result = new ArrayList<>();
        queryResponse.forEach(obj -> obj.forEach((key, val) -> {
            result.add(new EasemobStatusRes(key, "online".equals(val)));
        }));
//        result.forEach((obj -> {
//                obj.keySet()
//            });
        log.info("EasemobUserServiceImpl -> statusBatch -> url -> {} result -> {}", url, result);
        return result;
    }

    @Override
    public List<EasemobOfflineCountRes> getOfflineMsgCount(String username) {
        log.info("EasemobUserServiceImpl -> statusOne -> start -> param -> {}", username);
        String url = easemobUtil.getOffLineMsgCountUrl(username);
        List<JSONObject> queryResponse = easemobUtil.getForList(url, JSONObject.class);
        List<EasemobOfflineCountRes> result = new ArrayList<>();
        queryResponse.forEach(obj -> obj.forEach((key, val) -> {
            result.add(new EasemobOfflineCountRes(key, obj.getInteger(key)));
        }));
        log.info("EasemobUserServiceImpl -> statusOne -> url -> {} result -> {}", url, result);
        return result;
    }

    @Override
    public Object getOfflineMsgStatus(EasemobUserOffLineMsgStatusReq req) {
        log.info("EasemobUserServiceImpl -> statusOne -> start -> param -> {}", req);
        String url = easemobUtil.generatorOffLineMsgStatusUrl(req.getUsername(), req.getMsgId());
        List<JSONObject> result = easemobUtil.getForList(url, JSONObject.class);
        return result;
    }

    @Override
    public Object disconnect(String username) {
        try{
            log.info("EasemobUserServiceImpl -> disconnect -> start -> username -> {}", username);
            String url = easemobUtil.disconnect(username);
            EasemobBaseDTO result = easemobUtil.get(url);
            return result;
        } catch (Exception e) {
            log.warn("EasemobUserServiceImpl -> disconnect -> exception",e);
        }
        return null;
    }


    /**
     * 实体对象转换
     *
     * @return
     */
    private EasemobFriendRes convertToEasemobFriend(MemberOrganizationTypeDO bean) {
        EasemobFriendRes res = new EasemobFriendRes();
        BeanUtils.copyProperties(bean, res);
        return res;
    }
}
