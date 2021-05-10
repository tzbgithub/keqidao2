package com.qidao.application.service.member;

import com.qidao.application.entity.member.Member;
import com.qidao.application.model.dto.UpdateOriganizationDto;
import com.qidao.application.model.member.*;
import com.qidao.application.vo.*;
import com.qidao.application.model.member.*;
import com.qidao.application.vo.MemberDetailVo;
import com.qidao.application.vo.MemberVo;
import com.qidao.application.vo.OrganizationRep;
import com.qidao.application.vo.SelectConfigResp;
import com.qidao.framework.web.ResponseResult;
import org.springframework.web.bind.annotation.RequestParam;

;import java.util.List;

public interface MemberService {
    /**
     * 根据unionId查询用户
     */
     Member getOrganizationByUnionId(String unionId);
    /**
     * 根据会员ID获取会员基本信息
     */
    MemberInfoRes getMemberByMemberId(Long memberId);
    /**
     * 根据unionId获取会员基本信息
     */
    MemberInfoRes getMemberByUnionId(String unionId);
    /**
     * 根据会员ID获取会员详细信息
     */
    MemberDetailedRes getDetailedByMemberId(Long memberId);
    /**
     * 根据unionID获取会员详细信息
     */
    MemberDetailedRes getDetailedByUnionId(String unionId);
    /**
     * 修改会员信息
     */
    void update(MemberInfoUpdateReq req);

    /**
     * 查询用户名片
     */
    MemberVo findMemberMessage(Long memberId);
    /**
     * 查询用户信息详情
     */
    MemberDetailVo  findMemberMessageDetail(Long memberId);
    /**
     * 根据实验室标识查询成果发布标题栏
     */
    List<SelectConfigResp> findSelectConfigByOrganization(Long organizationId);
    /**
     * 修改实验室
     */
    void  updateOrganization(UpdateOriganizationDto updateOriganizationDto);
    /**
     * 修改用户个人信息
     */
    boolean  updateMemberMessage(MemberDetailVo memberDetailVo);
    /**
     * 开关推送按钮
     */
    void  offPush(Integer status ,Long memberId);
    /**
     * 根据会员ID查询组织机构信息
     */
    OrganizationRep findOrganizationByMemberId(Long memberId);

    /**
     * 推荐科研人员 <br>
     * 按照职称从高到低顺序排列: <br>
     * 教授、研究员、高级实验师、副教授，副研究员，讲师、助理研究员、实验师、助教<br>
     * 职称相同情况下，按照入驻时间先后排列，先入驻的排在前面。<br>
     *
     * 不会推荐已关注或已屏蔽的会员
     * 已经推荐的
     * @param howMany
     * @param blockList
     * @return
     */
    List<Long> listMemberByPositionAndTime( int howMany, List<Long> blockList);
    /**
     * 获取所有手机号： 没有分页
     * 在用户数据大规模推广后，请注意修改
     * @return id : 用户id  mobile: 手机号  其他不返回
     */
    List<MemberInfoBashRes> listAllMemberPhone();

    /**
     * 绑定行业
     * @param req
     */
    public void  memberBindingIndustry(BindIndustryReq req);

    /**
     * 绑定邮箱
     * @param memberId
     * @param email
     */
    public void  memberBindingEmail(Long memberId,String email);

    public  void  modifyMemberBasic(MemberBasicReq memberBasicReq);

    /**
     * 通过用户ID查询组织机构表的审核状态
     * @param memberId 入参：用户ID
     * @return verify_status -- 0-未审核 1-审核失败 2-审核成功    入驻审核
     */

    public Long findMemberStatus(Long memberId);

    /**
     * 上传验证证件接口
     */
    void memberAuthentication(MemberAuthenticationReq memberAuthenticationReq);

    /**
     * 获取会员ID集合 : 排除当前会员已关注或屏蔽的人
     * @param memberId 会员ID
     * @return
     */
    List<Long> listMemberWithoutSubscribe(Long memberId);

    /**
     * 根据会员ID集合获取会员简介信息集合
     * @param ids
     * @return
     */
    List<MemberSummaryRes> listMemberSummaryByMemberId(List<Long> ids);

    /**
     * 查询用户是否完善信息
     */
    boolean findMemberIsPrefect(Long memberId);

    /**
     * 为会员开通VIP权限 <br>
     * 如果会员组织信息为空，<strong>不会</strong>为会员创建企业<br>
     * <ul>
     * <li>已是VIP：为会员增加VIP服务时间</li>
     * <li>非VIP：开通VIP，开通时间为当前时间</li>
     * </ul>
     * @param memberId 会员ID 未对会员进行非空验证，调用时请传入合法的会员ID
     * @param serverTime VIP 服务时长 单位 ：分钟
     */
    void openVipAuthorizedTimed(Long memberId,Long serverTime);

    Boolean hasOrganization(Long memberId);

    /**
     * 获取用户的行业
     * @param memberId 用户ID
     * @return 行业返回对象集合（最多10个无分页） {@link MemberIndustryRes}
     */
    List<MemberIndustryRes> findMemberIndustry(Long memberId);

    /**
     * 注销用户信息
     * @param req 用户ID
     * @return java.lang.Boolean
     **/
    Boolean cancellationMember(MemberCancellationReq req);
}
