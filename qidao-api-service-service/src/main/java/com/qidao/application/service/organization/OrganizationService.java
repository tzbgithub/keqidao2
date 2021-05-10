package com.qidao.application.service.organization;


import com.qidao.application.entity.organization.MemberOrganizationTypeDO;
import com.qidao.application.model.dto.*;
import com.qidao.application.model.member.EnterpriseMemberReq;
import com.qidao.application.model.member.EnterpriseMemberRes;
import com.qidao.application.model.organization.KickOutMemberReq;
import com.qidao.application.model.organization.SignOutEnterpriseReq;
import com.qidao.application.model.organization.OrganizationDetailRes;
import com.qidao.application.vo.OrganizationBaseDataRep;
import com.qidao.application.vo.OrganizationSpaceRes;
import com.qidao.application.vo.TutorInfoReq;
import com.qidao.framework.service.ServicePage;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrganizationService {
    /**
     * 添加组织机构
     */
    String save(HttpServletRequest httpServletRequest, OrganizationDto organizationDto, int type) throws IOException;

    /**
     * 修改组织机构
     */
    String update(UpdateOriganizationDto updateOriganizationDto) throws IOException;

    /**
     * 销售员添加组织机构
     */
    String consoleSave(OrganizationDto organizationDto, HttpServletRequest request, int type) throws IOException;

    int delete(Long id);

    /**
     * 根据所属单位查询实验室
     */
    ArrayList<Map> findOrganizationByBelong(String belong, Integer type);

    /**
     * 查询实验室详情
     */
    ReturnOrganizationDto findByOrganizationId(@RequestParam("id") Long id);

    /**
     * 实验室认证
     */
    void authOrganization(AuthOrganizationDto authOrganizationDto);

    /**
     * 实验室空间
     */
    OrganizationSpaceRes findOrganizationSpace(Long organizationId);

    /**
     * 展示所有组织成员
     */
    List<TutorInfoReq> findOrganizationMembers(Long organizationId);

    /**
     * 实验室资料
     */
    OrganizationBaseDataRep findOrganizationData(Long organizationId);

    /**
     * 生成一个公司
     *
     * @return
     */
    Long generateCompany(GenerateCompanyReq generateCompanyReq);

    /**
     * 公司入驻信息更新
     */
    void modifyCompany(ModifyCompanyReq modifyCompanyReq);


    /**
     * 查询组织机构类型
     *
     * @param memberIds 会员ID集合
     * @return
     */
    List<MemberOrganizationTypeDO> queryOrganizationType(List<Long> memberIds);

    /**
     * 查询企业下所有会员信息
     * @param req
     * @return {@link EnterpriseMemberRes} 企业会员信息集合
     */
    ServicePage<List<EnterpriseMemberRes>> findEnterpriseMember(EnterpriseMemberReq req);

    /**
     * 退出企业
     * @param req {@link SignOutEnterpriseReq} 退出企业请求对象
     */
    void signOutEnterprise(SignOutEnterpriseReq req);


    void addCompany(AddCompanyReq obj);

    @Deprecated
    Boolean hasOrganization(Long memberId);

    OrganizationDetailRes findOrganizationDetail(Long id);

    /**
     * 企业管理员删除同事
     * @param req {@link KickOutMemberReq}
     */
    void kickOutMember (KickOutMemberReq req);
}
