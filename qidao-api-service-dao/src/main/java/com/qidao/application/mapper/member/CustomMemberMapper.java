package com.qidao.application.mapper.member;

import com.qidao.application.entity.member.MemberDetailed;
import com.qidao.application.entity.member.MemberInfo;
import com.qidao.application.entity.relation.LogBehaveDynamic;
import com.qidao.application.vo.MemberDynamicCountVo;
import com.qidao.application.vo.MemberVo;
import com.qidao.application.vo.SelectConfigResp;
import com.qidao.application.vo.TutorInfoReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomMemberMapper {

    MemberInfo getMemberByMemberId(Long memberId);

    MemberInfo getMemberByUnionId(String unionId);

    /**
     * <p>查询用户详情</p>
     * <p>会连接多张表进行查询</p>
     *
     * @param memberId 会员ID
     * @return {@link MemberDetailed}
     */
    MemberDetailed getDetailedById(Long memberId);

    MemberDetailed getDetailedByUnionId(String unionId);

    MemberVo findMemberById(Long memebeId);

    List<TutorInfoReq> findOrganizationMember(Long organizationId);

    List<TutorInfoReq> findOrganizationMembers(Long organizationId);

    /**
     * 根据动态ID查询评论用户头像
     *
     * @param dynamicId
     * @return 返回头像结果集
     */
    List<String> findAgreeHeadImages(Long dynamicId);

    /**
     * 根据动态ID查询评论用户头像
     * @param dynamicIds
     * @return
     */
    List<String> findAgreeHeadImagesList(@Param("dynamicIds") Long[] dynamicIds);



    /**
     * 根据会员id查询 动态数量（审核通过的数据 、未删除）
     *
     * @param memberId         会员id
     * @param isByVerifyStatus true查询条件增加审核状态=3 false查询条件不增加审核状态
     * @return java.util.List<com.qidao.application.entity.dynamic.DynamicCount>
     **/
    List<MemberDynamicCountVo> dynamicCountByMemberId(Long memberId, boolean isByVerifyStatus);

    /**
     * 获取用户行业
     *
     * @param memberId 会员ID
     * @return 行业集合 {@link SelectConfigResp}
     */
    List<SelectConfigResp> getMemberIndustry(Long memberId);

    /**
     * 删除公司同事
     *
     * @param id
     * @return
     */
    int kickOutMember(Long id);

    /**
     * 获取全部用户id
     * lastMemberId、limit为空查询全部
     *
     * @param lastMemberId 分批查询时 分批最后一个会员id
     * @param limit        分批查询时 每批限制
     * @return List<Long> 会员id列表
     **/
    List<Long> getMemberIdAll(Long lastMemberId, Integer limit);


    /**
     * 获取全部vip用户id
     * lastMemberId、limit为空查询全部
     *
     * @param lastMemberId 分批查询时 分批最后一个会员id
     * @param limit        分批查询时 每批限制
     * @return List<Long> 会员id列表
     **/
    List<Long> getAllVipMemberIdAll(Long lastMemberId, Integer limit);

    /**
     * 获取全部vip count(用户id) 数量
     *
     * @return long
     **/
    long getAllVipMemberIdAllCount();

    /**
     * 获取 所有实验室用户及其助手（审核中和审核通过）用户id
     * lastMemberId、limit为空查询全部
     *
     * @param lastMemberId 分批查询时 分批最后一个会员id
     * @param limit        分批查询时 每批限制
     * @return List<Long> 会员id列表
     **/
    List<Long> getAllLaboratoryAssistantMemberIdAll(Long lastMemberId, Integer limit);

    /**
     * 获取 所有实验室用户及其助手（审核中和审核通过）用户id 数量
     *
     * @return long
     **/
    long getAllLaboratoryAssistantMemberIdAllCount();

    /**
     * 获取 所有实验室用户及其助手 （审核通过）用户id
     * lastMemberId、limit为空查询全部
     *
     * @param lastMemberId 分批查询时 分批最后一个会员id
     * @param limit        分批查询时 每批限制
     * @return List<Long> 会员id列表
     **/
    List<Long> getAdoptLaboratoryAssistantMemberIdAll(Long lastMemberId, Integer limit);

    /**
     * 获取 所有实验室用户及其助手 （审核通过）用户id 数量
     *
     * @return long
     **/
    long getAdoptLaboratoryAssistantMemberIdAllCount();

    /**
     * 获取 所有企业用户用户id
     * lastMemberId、limit为空查询全部
     *
     * @param lastMemberId 分批查询时 分批最后一个会员id
     * @param limit        分批查询时 每批限制
     * @return List<Long> 会员id列表
     **/
    List<Long> getAllEnterpriseMemberIdAll(Long lastMemberId, Integer limit);

    /**
     * 获取 所有企业用户用户id 数量
     *
     * @return long
     **/
    long getAllEnterpriseMemberIdAllCount();

    /**
     * 获取 所有vip企业用户id
     * lastMemberId、limit为空查询全部
     *
     * @param lastMemberId 分批查询时 分批最后一个会员id
     * @param limit        分批查询时 每批限制
     * @return List<Long> 会员id列表
     **/
    List<Long> getAllEnterpriseVipMemberIdAll(Long lastMemberId, Integer limit);

    /**
     * 获取 所有vip企业用户id 数量
     *
     * @return long
     **/
    long getAllEnterpriseVipMemberIdAllCount();


    /**
     * 获取 所有未修改名称&头像 用户id
     * lastMemberId、limit为空查询全部
     *
     * @param lastMemberId 分批查询时 分批最后一个会员id
     * @param limit        分批查询时 每批限制
     * @return List<Long> 会员id列表
     **/
    List<Long> getNotUpdateNameAndHeadImg(Long lastMemberId, Integer limit);

    /**
     * 获取 所有未修改名称&头像 用户id 数量
     *
     * @return long
     **/
    long getNotUpdateNameAndHeadImgCount();
}
