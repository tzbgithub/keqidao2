package com.qidao.application.service.invoice.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.qidao.application.entity.invoice.Invoice;
import com.qidao.application.entity.invoice.InvoiceExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.order.CustomOrder;
import com.qidao.application.entity.order.Order;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.organization.OrganizationExample;
import com.qidao.application.mapper.invoice.InvoiceMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.order.CustomOrderMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.invoice.*;
import com.qidao.application.service.invoice.InvoiceService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service(value = "InvoiceService")
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private CustomOrderMapper customOrderMapper;

    @Autowired
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Override
    public Boolean insert(InvoiceAddReq invoiceAddReq) {
        log.info("InvoiceServiceImpl -> insert -> start -> invoiceAddReq : {}", invoiceAddReq);
        CustomOrder order = customOrderMapper.getOrderById(invoiceAddReq.getOrderId());
        log.info("InvoiceServiceImpl -> insert -> ObjectUtil.isEmpty(order) || order.getDelFlag() == ConstantEnum.DELETED.getByte() : {}"
                , (ObjectUtil.isEmpty(order) || order.getDelFlag() == ConstantEnum.DELETED.getByte()));
        if(ObjectUtil.isEmpty(order) || order.getDelFlag() == ConstantEnum.DELETED.getByte()){
            log.error("InvoiceServiceImpl -> insert -> end -> error : 该订单不存在");
            throw new BusinessException("该订单不存在");
        }
        Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
        log.info("InvoiceServiceImpl -> insert -> ObjectUtil.isEmpty(member) || member.getDelFlag()==ConstantEnum.DELETED.getByte() : {}"
                , (ObjectUtil.isEmpty(member) || member.getDelFlag()==ConstantEnum.DELETED.getByte()));
        if(ObjectUtil.isEmpty(member) || member.getDelFlag()==ConstantEnum.DELETED.getByte()){
            log.error("InvoiceServiceImpl -> insert -> end -> error : 该订单所属的用户已注销");
            throw new BusinessException("该订单所属的用户已注销");
        }
        InvoiceExample example = new InvoiceExample();
        InvoiceExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(invoiceAddReq.getOrderId());
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Invoice inQuery = invoiceMapper.selectOneByExample(example);
        log.info("InvoiceServiceImpl -> insert -> ObjectUtil.isNotEmpty(inQuery) : {}", (ObjectUtil.isNotEmpty(inQuery)));
        if(ObjectUtil.isNotEmpty(inQuery)){
            log.error("InvoiceServiceImpl -> insert -> end -> error : 该用户已经申请过此订单的发票，不能重复申请");
            throw new BusinessException("该用户已经申请过此订单的发票，不能重复申请");
        }
        Organization organization = organizationMapper.selectByPrimaryKey(member.getOrganizationId());
        log.info("InvoiceServiceImpl -> insert -> ObjectUtil.isEmpty(organization) || organization.getDelFlag() == ConstantEnum.DELETED.getByte() : {}"
                ,ObjectUtil.isEmpty(organization) || organization.getDelFlag() == ConstantEnum.DELETED.getByte());
        if(ObjectUtil.isEmpty(organization) || organization.getDelFlag() == ConstantEnum.DELETED.getByte()){
            Invoice invoice = Invoice.builder()
                    .invoiceHead(invoiceAddReq.getInvoiceHead())
                    .dutyParagraph(invoiceAddReq.getDutyParagraph())
                    .orderId(invoiceAddReq.getOrderId())
                    .remark(invoiceAddReq.getRemark())
                    .type(invoiceAddReq.getType())
                    .memberId(order.getMemberId())
                    .email(invoiceAddReq.getEmail())
                    .id(snowflakeIdWorker53.nextId())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .status(InvoiceEnum.STATUS_ING_PROCESS.getValue())
                    .delFlag(ConstantEnum.NOT_DEL.getByte())
                    .build();
            log.info("InvoiceServiceImpl -> insert -> end");
            return invoiceMapper.insert(invoice) > 0;
        }else {
            Invoice invoice = Invoice.builder()
                    .invoiceHead(invoiceAddReq.getInvoiceHead())
                    .dutyParagraph(invoiceAddReq.getDutyParagraph())
                    .orderId(invoiceAddReq.getOrderId())
                    .organizationId(organization.getId())
                    .remark(invoiceAddReq.getRemark())
                    .type(invoiceAddReq.getType())
                    .memberId(order.getMemberId())
                    .email(invoiceAddReq.getEmail())
                    .id(snowflakeIdWorker53.nextId())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .status(InvoiceEnum.STATUS_ING_PROCESS.getValue())
                    .delFlag(ConstantEnum.NOT_DEL.getByte())
                    .build();
            log.info("InvoiceServiceImpl -> insert -> end");
            return invoiceMapper.insert(invoice) > 0;
        }

    }

    @Override
    public ServicePage<List<InvoiceRes>> getListDo(InvoiceQuery invoiceQuery) {
        log.info("InvoiceServiceImpl -> getListDo -> start -> invoiceQuery : {}", invoiceQuery);
        InvoiceExample example = new InvoiceExample();
        InvoiceExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        criteria.andStatusEqualTo(InvoiceEnum.STATUS_ED_PROCESS.getValue());
        criteria.andMemberIdEqualTo(invoiceQuery.getMemberId());
        Page<Invoice> page = PageUtil.start(invoiceQuery, () -> invoiceMapper.selectByExample(example));
        log.info("InvoiceServiceImpl -> getListDo -> CollUtil.isNotEmpty(page) : {}" , (CollUtil.isNotEmpty(page)));
        if(CollUtil.isNotEmpty(page)){
            ServicePage<List<InvoiceRes>> servicePage = new ServicePage<>();
            servicePage.setEndRow(page.getEndRow());
            servicePage.setPageNum(page.getPageNum());
            servicePage.setPages(page.getPages());
            servicePage.setPageSize(page.getPageSize());
            servicePage.setTotal(page.getTotal());
            servicePage.setStartRow(page.getStartRow());
            List<Invoice> invoiceList = page.getResult();
            List<InvoiceRes> invoiceResList = new ArrayList<>();
            for(Invoice invoice : invoiceList) {
                Member imember = memberMapper.selectByPrimaryKey(invoice.getMemberId());
                log.info("InvoiceServiceImpl -> getListDo -> ObjectUtil.isEmpty(imember) || imember.getDelFlag() == ConstantEnum.DELETED.getByte() : {}", (ObjectUtil.isEmpty(imember) || imember.getDelFlag() == ConstantEnum.DELETED.getByte()));
                if(ObjectUtil.isEmpty(imember) || imember.getDelFlag() == ConstantEnum.DELETED.getByte()){
                    log.error("InvoiceServiceImpl -> getListDo -> error ：该用户不存在或已删除");
                    throw new BusinessException("该用户不存在或已删除");
                }
                Organization organization = organizationMapper.selectByPrimaryKey(imember.getOrganizationId());
                log.info("InvoiceServiceImpl -> getListDo -> ObjectUtil.isNotEmpty(organization) : {}", (ObjectUtil.isNotEmpty(organization)));
                if(ObjectUtil.isNotEmpty(organization)){
                    InvoiceRes inRes = InvoiceRes.builder()
                            .createTime(invoice.getCreateTime())
                            .dutyParagraph(invoice.getDutyParagraph())
                            .email(invoice.getEmail())
                            .invoiceHead(invoice.getInvoiceHead())
                            .memberName(memberMapper.selectByPrimaryKey(invoice.getMemberId()).getName())
                            .organizationName(organization.getName())
                            .orderId(invoice.getOrderId())
                            .remark(invoice.getRemark())
                            .status(invoice.getStatus())
                            .type(invoice.getType())
                            .build();
                    invoiceResList.add(inRes);
                }else{
                    InvoiceRes inRes = InvoiceRes.builder()
                            .createTime(invoice.getCreateTime())
                            .dutyParagraph(invoice.getDutyParagraph())
                            .email(invoice.getEmail())
                            .invoiceHead(invoice.getInvoiceHead())
                            .memberName(memberMapper.selectByPrimaryKey(invoice.getMemberId()).getName())
                            .organizationName(null)
                            .orderId(invoice.getOrderId())
                            .remark(invoice.getRemark())
                            .status(invoice.getStatus())
                            .type(invoice.getType())
                            .build();
                    invoiceResList.add(inRes);
                }
            }
            servicePage.setResult(invoiceResList);
            log.info("InvoiceServiceImpl -> getListDo -> end");
            return servicePage;
        }
        log.info("InvoiceServiceImpl -> getListDo -> end");
        return null;
    }

    @Override
    public ServicePage<List<InvoiceRes>> getListUndo(InvoiceQuery invoiceQuery) {
        log.info("InvoiceServiceImpl -> getListUndo -> invoiceQuery : {}", invoiceQuery);
        InvoiceExample example = new InvoiceExample();
        InvoiceExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        criteria.andStatusEqualTo(InvoiceEnum.STATUS_ING_PROCESS.getValue());
        criteria.andMemberIdEqualTo(invoiceQuery.getMemberId());
        Page<Invoice> page = PageUtil.start(invoiceQuery, () -> invoiceMapper.selectByExample(example));
        log.info("InvoiceServiceImpl -> getListUndo -> CollUtil.isNotEmpty(page) : {}" , (CollUtil.isNotEmpty(page)));
        if(CollUtil.isNotEmpty(page)){
            ServicePage<List<InvoiceRes>> servicePage = new ServicePage<>();
            servicePage.setEndRow(page.getEndRow());
            servicePage.setPageNum(page.getPageNum());
            servicePage.setPages(page.getPages());
            servicePage.setPageSize(page.getPageSize());
            servicePage.setTotal(page.getTotal());
            servicePage.setStartRow(page.getStartRow());
            List<Invoice> invoiceList = page.getResult();
            List<InvoiceRes> invoiceResList = new ArrayList<>();
            for(Invoice invoice : invoiceList) {
                Member imember = memberMapper.selectByPrimaryKey(invoice.getMemberId());
                log.info("InvoiceServiceImpl -> getListUndo -> ObjectUtil.isEmpty(imember) || imember.getDelFlag() == ConstantEnum.DELETED.getByte() : {}", (ObjectUtil.isEmpty(imember) || imember.getDelFlag() == ConstantEnum.DELETED.getByte()));
                if(ObjectUtil.isEmpty(imember) || imember.getDelFlag() == ConstantEnum.DELETED.getByte()){
                    log.error("InvoiceServiceImpl -> getListUndo -> error ：该用户不存在或已删除");
                    throw new BusinessException("该用户不存在或已删除");
                }
                Organization organization = organizationMapper.selectByPrimaryKey(imember.getOrganizationId());
                log.info("InvoiceServiceImpl -> getListUndo -> ObjectUtil.isNotEmpty(organization) : {}", (ObjectUtil.isNotEmpty(organization)));
                if(ObjectUtil.isNotEmpty(organization)){
                    InvoiceRes inRes = InvoiceRes.builder()
                            .createTime(invoice.getCreateTime())
                            .dutyParagraph(invoice.getDutyParagraph())
                            .email(invoice.getEmail())
                            .invoiceHead(invoice.getInvoiceHead())
                            .memberName(memberMapper.selectByPrimaryKey(invoice.getMemberId()).getName())
                            .organizationName(organization.getName())
                            .orderId(invoice.getOrderId())
                            .remark(invoice.getRemark())
                            .status(invoice.getStatus())
                            .type(invoice.getType())
                            .build();
                    invoiceResList.add(inRes);
                }else{
                    InvoiceRes inRes = InvoiceRes.builder()
                            .createTime(invoice.getCreateTime())
                            .dutyParagraph(invoice.getDutyParagraph())
                            .email(invoice.getEmail())
                            .invoiceHead(invoice.getInvoiceHead())
                            .memberName(memberMapper.selectByPrimaryKey(invoice.getMemberId()).getName())
                            .organizationName(null)
                            .orderId(invoice.getOrderId())
                            .remark(invoice.getRemark())
                            .status(invoice.getStatus())
                            .type(invoice.getType())
                            .build();
                    invoiceResList.add(inRes);
                }
            }
            servicePage.setResult(invoiceResList);
            log.info("InvoiceServiceImpl -> getListUndo -> end");
            return servicePage;
        }
        log.info("InvoiceServiceImpl -> getListUndo -> end");
        return null;
    }

    @Override
    public Boolean deleteById(BaseIdQuery baseIdQuery) {
        log.info("InvoiceServiceImpl -> deleteById -> start -> baseIdQuery : {}", baseIdQuery);
        Invoice invoice = invoiceMapper.selectByPrimaryKey(baseIdQuery.getId());
        log.info("InvoiceServiceImpl -> deleteById -> ObjectUtil.isNotEmpty(invoice) : {}", (ObjectUtil.isNotEmpty(invoice)));
        if(ObjectUtil.isNotEmpty(invoice)){
            if(invoice.getDelFlag() == ConstantEnum.NOT_DEL.getByte()){
                invoice.setDelFlag(ConstantEnum.DELETED.getByte());
                invoiceMapper.updateByPrimaryKey(invoice);
                log.info("InvoiceServiceImpl -> deleteById -> end");
                return true;
            }else{
                log.error("InvoiceServiceImpl -> deleteById -> end -> error : 该发票已经删除");
                throw new BusinessException("该发票已经删除");
            }
        }
        log.info("InvoiceServiceImpl -> deleteById -> end");
        return false;
    }

    @Override
    public Boolean update(InvoiceUpdateReq invoiceUpdateReq) {
        log.info("InvoiceServiceImpl -> update -> start -> invoiceUpdateReq : {}", invoiceUpdateReq);
        Invoice invoice = invoiceMapper.selectByPrimaryKey(invoiceUpdateReq.getId());
        log.info("InvoiceServiceImpl -> update -> ObjectUtil.isNotEmpty(invoice) : {}", (ObjectUtil.isNotEmpty(invoice)));
        if(ObjectUtil.isNotEmpty(invoice)){
            Invoice newOne = new Invoice();
            BeanUtils.copyProperties(invoice, newOne);
            log.info("InvoiceServiceImpl -> update -> newOne.getDelFlag() == ConstantEnum.NOT_DEL.getByte() : {}", (newOne.getDelFlag() == ConstantEnum.NOT_DEL.getByte()));
            if(newOne.getDelFlag() == ConstantEnum.NOT_DEL.getByte()){
                newOne.setInvoiceHead(invoiceUpdateReq.getInvoiceHead());
                newOne.setDutyParagraph(invoiceUpdateReq.getDutyParagraph());
                newOne.setEmail(invoiceUpdateReq.getEmail());
                newOne.setRemark(invoiceUpdateReq.getRemark());
                newOne.setType(invoiceUpdateReq.getType());
                newOne.setUpdateTime(LocalDateTime.now());
                log.info("InvoiceServiceImpl -> update -> end");
                return invoiceMapper.updateByPrimaryKey(newOne) > 0;
            }else{
                log.error("InvoiceServiceImpl -> update -> end -> error : 该发票已经删除");
                throw new BusinessException("该发票已经删除");
            }
        }else{
            log.error("InvoiceServiceImpl -> update -> end -> error : 该发票不存在");
            throw new BusinessException("该发票不存在");
        }
    }

}
