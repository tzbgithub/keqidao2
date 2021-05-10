package com.qidao.application.service.order.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.order.CustomOrder;
import com.qidao.application.entity.order.Order;
import com.qidao.application.entity.order.OrderDescription;
import com.qidao.application.entity.order.OrderPhysicalDetail;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.product.ProductSku;
import com.qidao.application.entity.product.ProductSkuExample;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.order.CustomOrderMapper;
import com.qidao.application.mapper.order.OrderPhysicalDetailMapper;
import com.qidao.application.mapper.organization.CustomOrganizationMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.mapper.product.ProductSkuMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dto.GenerateCompanyReq;
import com.qidao.application.model.member.BecomeVipEventParam;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.sign.MemberInfoDTO;
import com.qidao.application.model.msg.listen.QidaoSchedulingConfigurer;
import com.qidao.application.model.order.*;
import com.qidao.application.model.order.enums.OrderConstantEnum;
import com.qidao.application.model.order.enums.OrderExceptionEnum;
import com.qidao.application.model.order.enums.OrderStatusEnum;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.model.pay.enums.PayExceptionEnum;
import com.qidao.application.model.pay.enums.PayWayEnum;
import com.qidao.application.model.product.enums.ProductErrorEnum;
import com.qidao.application.model.product.enums.ProductPermissionConstantEnum;
import com.qidao.application.model.product.enums.ProductTypeEnum;
import com.qidao.application.service.event.PublishEventComponent;
import com.qidao.application.service.event.member.BecomeVipEvent;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.order.OrderService;
import com.qidao.application.service.organization.OrganizationService;
import com.qidao.application.service.redis.MemberRedissonService;
import com.qidao.application.vo.OrganizationRep;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service("orderService")
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Resource
    private MemberRedissonService memberRedissonService;
    /**
     * 自动关闭订单时间 ： 分钟
     */
    @Value("${order.auto.close.minter}")
    private Integer autoCloseMinter;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private CustomOrderMapper customOrderMapper;
    @Resource
    private MemberService memberService;
    @Resource
    private OrganizationService organizationService;
    @Resource
    private CustomOrganizationMapper customOrganizationMapper;
    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private QidaoSchedulingConfigurer qidaoSchedulingConfigurer;
    @Resource
    private PublishEventComponent publishEventComponent;
    @Resource
    private OrderPhysicalDetailMapper orderPhysicalDetailMapper;

    /**
     * 根据会员ID分页查询订单
     *
     * @param req
     * @return
     */
    @Override
    public OrderPageRes getOrderByMemberId(OrderPageReq req) {
        log.info("OrderServiceImpl -> getOrderByMemberId -> start -> req:{}", req);
        Page<CustomOrder> page = PageUtil.start(req, () -> customOrderMapper.getOrderByMemberId(req.getMemberId(), req.getPhysicalFlag()));
        ServicePage<List<OrderVo>> orderPage = new ServicePage<>();
        BeanUtils.copyProperties(page, orderPage);
        List<CustomOrder> orderList = page.getResult();
        String memberVipEndTime = null;
        log.info("OrderServiceImpl -> getOrderByMemberId -> 判断orderList是否为空 -> orderList:{}", orderList);
        if (CollUtil.isNotEmpty(orderList)) {
            memberVipEndTime = LocalDateTimeUtil.format(orderList.get(0).getFinalVipEndTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN);
            List<OrderVo> orderVos = new ArrayList<>();
            for (CustomOrder order : orderList) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order, orderVo);
                orderVo.setVipStartTime(LocalDateTimeUtil.format(order.getVipStartTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                orderVo.setVipEndTime(LocalDateTimeUtil.format(order.getVipEndTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                orderVo.setPayTime(LocalDateTimeUtil.format(order.getPayTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                orderVos.add(orderVo);
            }
            orderPage.setResult(orderVos);
        }

        OrderPageRes res = new OrderPageRes();
        res.setList(orderPage);
        res.setMemberVipEndTime(memberVipEndTime);
        log.info("OrderServiceImpl -> getOrderByMemberId -> end");
        return res;
    }

    @Override
    public OrderCreateOneProductRes orderCreateOneProduct(OrderCreateOneProductReq req) {
        log.info("OrderServiceImpl -> orderCreateOneProduct -> req -> {}", req);
        Long productSkuId = req.getProductSkuId();
        // 产品验证
        ProductSkuExample productExample = new ProductSkuExample();
        productExample.createCriteria()
                .andIdEqualTo(productSkuId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        ProductSku product = productSkuMapper.selectOneByExample(productExample);
        if (null == product) {
            log.info("OrderServiceImpl -> orderCreateOneProduct -> product fail");
            throw new BusinessException(PayExceptionEnum.PRODUCT);
        }
        // 实物订单
        boolean isPhysicalOrder = OrderConstantEnum.PHYSICAL_ORDER.getInteger().equals(product.getProductType());
        if (isPhysicalOrder) {
            // 验证实物订单必填参数
            OrderAddressDTO address = req.getAddress();
            if (null == address || address.hasBlankVar()) {
                throw new BusinessException(PayExceptionEnum.ADDRESS);
            }
        }
        // 可购买权限验证
        Long memberId = req.getMemberId();
        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (!ableBuy(product.getPermission(), member)) {
            log.info("OrderServiceImpl -> orderCreateOneProduct -> permission fail -> productSkuId -> {} memberId -> {}", productSkuId, memberId);
            throw new BusinessException(PayExceptionEnum.PERMISSION);
        }

        // 生成订单流水号
        Long orderNo = snowflakeIdWorker53.nextId();

        // 计算订单金额
        BigDecimal orderPrice = product.getSalePrice().multiply(new BigDecimal(req.getQuantity()));
        // 实付价格
        BigDecimal payPrice = calPayPrice(orderPrice, memberId);

        Long orderId = snowflakeIdWorker53.nextId();

        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .memberName(member.getName())
                .orderPrice(orderPrice)
                .payPrice(payPrice)
                .productSkuId(productSkuId)
                .no(orderNo)
                .physicalFlag(OrderConstantEnum.getPhysicalFlag(isPhysicalOrder))
                .status(OrderStatusEnum.UNPAID.getType())
                .build();

        customOrderMapper.insertSelective(order);

        // 生成收货人信息
        if (isPhysicalOrder) {
            OrderPhysicalDetail orderPhysicalDetail = OrderPhysicalDetail.builder()
                    .id(snowflakeIdWorker53.nextId())
                    .orderId(orderId)
                    .build();
            BeanUtils.copyProperties(req.getAddress(), orderPhysicalDetail);
            orderPhysicalDetailMapper.insertSelective(orderPhysicalDetail);
        }

        // 超时关闭
        qidaoSchedulingConfigurer.addTask(orderId, () -> {
            this.updateStatusClosed(orderId);
        }, LocalDateTime.now().plusMinutes(autoCloseMinter));

        OrderCreateOneProductRes res = OrderCreateOneProductRes.builder()
                .orderId(orderId)
                .orderNo(orderNo)
                .build();
        log.info("OrderServiceImpl -> orderCreateOneProduct -> end -> res -> {}", res);
        return res;
    }

    /**
     * 计算实付金额
     *
     * @param orderPrice
     * @param memberId
     * @return
     */
    private BigDecimal calPayPrice(BigDecimal orderPrice, Long memberId) {
        return orderPrice;
    }

    /**
     * 可购买权限验证
     *
     * @param permission 产品设置的购买权限
     * @param member     {@link Member} 会员对象
     * @return true : 可以购买 ； false : 不可购买
     * @see ProductPermissionConstantEnum
     */
    private boolean ableBuy(Integer permission, @NotNull Member member) {
        OrganizationRep organizationInfo = memberService.findOrganizationByMemberId(member.getId());
        switch (ProductPermissionConstantEnum.valueOf(permission)) {
            case PERMISSION_ALL: {
                Integer type = organizationInfo.getType();
                // 实验室需要审核通过才可购买
                boolean isLab = OrganizationEnum.TYPE_LABORATORY.getValue().equals(type);
                if (isLab) {
                    return MemberEnum.VERIFY_STATUS_SUCCESS.getValue().equals(member.getVerifyStatus());
                }
                return true;
            }
            case PERMISSION_LABORATORY: {
                // 实验室需要审核通过才可购买
                return MemberEnum.VERIFY_STATUS_SUCCESS.getValue().equals(member.getVerifyStatus())
                        && OrganizationEnum.TYPE_LABORATORY.getValue().equals(organizationInfo.getType());
            }
            case PERMISSION_ENTERPRISE: {
                return OrganizationEnum.TYPE_COMPANY.getValue().equals(organizationInfo.getType());
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public OrderDescriptionRes getOrderDescription(OrderDescriptionReq req) {
        log.info("OrderServiceImpl -> getOrderDescription -> start -> param:{}", req);
        OrderDescription orderDescription = customOrderMapper.getOrderDescription(req.getOrderNo(), req.getMemberId());
        OrderDescriptionRes res = new OrderDescriptionRes();
        log.info("OrderServiceImpl -> getOrderDescription -> orderDescription == null :{}", orderDescription == null);
        if (orderDescription == null) {
            log.info("OrderServiceImpl -> getOrderDescription -> end -> return:null");
            return null;
        }
        BeanUtils.copyProperties(orderDescription, res);
        res.setVipStartTime(LocalDateTimeUtil.format(orderDescription.getVipStartTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        res.setVipEndTime(LocalDateTimeUtil.format(orderDescription.getVipEndTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        res.setPayTime(LocalDateTimeUtil.format(orderDescription.getPayTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        log.info("OrderServiceImpl -> getOrderDescription -> end -> return:{}", res);
        return res;
    }

    @Override
    public boolean updateStatusPayment(Long id) {
        log.info("OrderServiceImpl -> updateStatusPayment -> start -> param:{}", id);
        Order order = customOrderMapper.queryOrderStatus(id);
        log.info("OrderServiceImpl -> updateStatusPayment -> order == null:{}", order == null);
        if (order == null) {
            log.error("OrderServiceImpl -> updateStatusPayment -> error:{}", OrderExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(OrderExceptionEnum.DELETE_TRUE);
        }
        boolean equalsZero = !order.getStatus().equals(OrderStatusEnum.UNPAID.getType());
        log.info("OrderServiceImpl -> updateStatusPayment -> order.getStatus() != 0 :{}", equalsZero);
        if (equalsZero) {
            log.error("OrderServiceImpl -> updateStatusPayment -> error:{}", OrderExceptionEnum.UNPAID.getMessage());
            throw new BusinessException(OrderExceptionEnum.UNPAID);
        }
        boolean res = customOrderMapper.updateStatusPayment(id) > 0;
        log.info("OrderServiceImpl -> updateStatusPayment -> end -> return:{}", res);
        return res;
    }

    @Override
    public boolean updateStatusClosed(Long id) {
        log.info("OrderServiceImpl -> updateStatusClosed -> start -> param:{}", id);
        Order order = customOrderMapper.queryOrderStatus(id);
        log.info("OrderServiceImpl -> updateStatusClosed -> order == null:{}", order == null);
        if (order == null) {
            log.error("OrderServiceImpl -> updateStatusClosed -> error:{}", OrderExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(OrderExceptionEnum.DELETE_TRUE);
        }
        boolean equalsZero = !order.getStatus().equals(OrderStatusEnum.UNPAID.getType());
        log.info("OrderServiceImpl -> updateStatusClosed -> order.getStatus() != 0 :{}", equalsZero);
        if (equalsZero) {
            log.error("OrderServiceImpl -> updateStatusClosed -> error:{}", OrderExceptionEnum.UNPAID.getMessage());
            throw new BusinessException(OrderExceptionEnum.UNPAID);
        }
        boolean res = customOrderMapper.updateStatusClosed(id) > 0;
        log.info("OrderServiceImpl -> updateStatusClosed -> end -> return:{}", res);
        return res;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderDone(Long orderNo, Integer payWay) {
        orderDone(orderNo, payWay, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderDone(Long orderNo, Integer payWay, Boolean isLabOrder) {
        orderDone(orderNo, payWay, false, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderDone(Long orderNo, Integer payWay, Boolean isLabOrder, String thirdOrderNo) {
        orderDone(orderNo, payWay, false, null,false);
    }

    @Override
    public void orderDone(Long orderNo, Integer payWay, Boolean isLabOrder, String thirdOrderNo, Boolean isSandBox) {
        log.info("OrderServiceImpl -> orderDone -> start -> param -> {}", orderNo);
        // 第三方订单惟一性
        boolean hasThirdOrderNo = hasThirdOrderNo(thirdOrderNo);
        if (hasThirdOrderNo) {
            log.warn("OrderServiceImpl -> orderDone -> order -> 第三方重复订单 -> {}", thirdOrderNo);
            throw new BusinessException(PayExceptionEnum.REPEAT);
        }
        CustomOrder order = selectByOrderNo(orderNo);
        if (null == order) {
            log.warn("OrderServiceImpl -> orderDone -> order -> is null");
            throw new BusinessException(PayExceptionEnum.ORDER_NULL);
        }
        boolean unableChange = !OrderStatusEnum.contains(order.getStatus(), OrderStatusEnum.UNPAID, OrderStatusEnum.CLOSE);
        if (unableChange) {
            log.warn("OrderServiceImpl -> orderDone -> unableChange -> orderNo -> {}", orderNo);
            throw new BusinessException(PayExceptionEnum.ORDER);
        }

        // 检查此订单对应的产品信息
        ProductSkuExample productSkuExample = new ProductSkuExample();
        productSkuExample.createCriteria()
                .andIdEqualTo(order.getProductSkuId());
        ProductSku product = productSkuMapper.selectOneByExample(productSkuExample);
        if (null == product) {
            log.warn("OrderServiceImpl -> orderDone -> product -> is null");
            throw new BusinessException(ProductErrorEnum.NOTFOUND_PRODUCT_ERROR);
        }

        Integer serverVal = product.getServerVal();
        Integer type = product.getType();

        log.info("OrderServiceImpl -> orderDone -> orderNo -> {} productType -> {} productServerVal -> {}", orderNo, type, serverVal);

        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andIdEqualTo(order.getMemberId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        Long memberId = member.getId();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime vipStartTime = null;
        LocalDateTime vipEndTime = null;

        // 实验室专属0元订单验证
        Integer organizationType = customOrganizationMapper.getOrganizationType(memberId);
        if (isLabOrder) {
            LocalDateTime oldEndTime = member.getVipEndTime();
            boolean isVip = MemberEnum.LEVEL_VIP.getValue().equals(member.getLevel()) && oldEndTime.isAfter(now);
            if (isVip) {
                throw new BusinessException("您已是VIP用户，无需重复下单");
            }

            if (!OrganizationEnum.TYPE_LABORATORY.getValue().equals(organizationType)) {
                throw new BusinessException("您已不是实验室用户");
            }

            BigDecimal zero = BigDecimal.ZERO;
            if (zero.compareTo(product.getSalePrice()) != 0) {
                throw new BusinessException("实验室订单有误");
            }
        }
        LocalDateTime organizationVipEndTime = null;
        if (ProductTypeEnum.TYPE_TIME.getCode().equals(type)) {
            boolean isOrganizationVip = false;

            Long organizationId = member.getOrganizationId();
            // 如果会员组织信息为空 ： 说明没有相关的信息 ： 创建企业
            // 1.1 功能确认： 企业被创建后可以移交，但不会被删除
            if (ObjectUtil.isNull(organizationId)) {
                // 新增企业
                GenerateCompanyReq generateCompanyReq = new GenerateCompanyReq(memberId);
                organizationId = organizationService.generateCompany(generateCompanyReq);
                member.setOrganizationId(organizationId);
                isOrganizationVip = true;
            } else {
                isOrganizationVip =  OrganizationEnum.TYPE_COMPANY.getValue().equals(organizationType);
            }

            if(isOrganizationVip){
                /*
                 * 为组织机构购买会员
                 */
                Organization organization = organizationMapper.selectByPrimaryKey(organizationId);
                Organization updateBean = new Organization();
                updateBean.setId(organizationId);

                // 是否已是会员
                // 定时器执行时有时间偏差：可能会出现会员已过期但状态还是1的情况  所以使用双值判断
                LocalDateTime oldEndTime = organization.getVipEndTime();
                boolean isVip = null != oldEndTime  && oldEndTime.isAfter(now) ;

                log.info("OrderServiceImpl -> orderDone -> renewal -> orderNo -> {} isVip -> {}", orderNo, isVip);

                if (isVip) {
                    vipStartTime = oldEndTime;
                    vipEndTime = oldEndTime.plusMinutes(serverVal);
                    log.info("OrderServiceImpl -> orderDone -> renewal -> orderNo -> {} memberId -> {} oldTime -> {} newTime -> {}",
                            orderNo, memberId, member.getVipEndTime(), vipEndTime);
                    updateBean.setVipEndTime(vipEndTime);
                } else {
                    vipStartTime = now;
                    vipEndTime = now.plusMinutes(serverVal);
                    log.info("OrderServiceImpl -> orderDone -> open -> orderNo -> {} memberId -> {} startTime -> {} endTime -> {}",
                            orderNo, memberId, vipStartTime, vipEndTime);
                    updateBean.setVipStartTime(vipStartTime);
                    updateBean.setVipEndTime(vipEndTime);
                }
                organizationVipEndTime = vipEndTime;
                organizationMapper.updateByPrimaryKeySelective(updateBean);
            } else {
                /*
                    为会员增加时间
                */

                // 是否已是会员
                // 定时器执行时有时间偏差：可能会出现会员已过期但状态还是1的情况  所以使用双值判断
                LocalDateTime oldEndTime = member.getVipEndTime();
                boolean isVip = MemberEnum.LEVEL_VIP.getValue().equals(member.getLevel()) && oldEndTime.isAfter(now);
                log.info("OrderServiceImpl -> orderDone -> renewal -> orderNo -> {} isVip -> {}", orderNo, isVip);

                if (isVip) {
                    vipStartTime = oldEndTime;
                    vipEndTime = oldEndTime.plusMinutes(serverVal);
                    log.info("OrderServiceImpl -> orderDone -> renewal -> orderNo -> {} memberId -> {} oldTime -> {} newTime -> {}",
                            orderNo, memberId, member.getVipEndTime(), vipEndTime);
                    member.setVipEndTime(vipEndTime);
                } else {
                    vipStartTime = now;
                    vipEndTime = now.plusMinutes(serverVal);
                    log.info("OrderServiceImpl -> orderDone -> open -> orderNo -> {} memberId -> {} startTime -> {} endTime -> {}",
                            orderNo, memberId, vipStartTime, vipEndTime);
                    member.setVipStartTime(vipStartTime);
                    member.setVipEndTime(vipEndTime);
                    member.setLevel(MemberEnum.LEVEL_VIP.getValue());
                }
            }

        } else {
            // 计次类型： 因需求变更 ， 无限期搁浅
            throw new BusinessException(OrderExceptionEnum.SUPPORT);
        }
        // 如果是企业用户 - 无论是新增企业还是已有企业，设置为审核通过
        // 如果是实验室 - 未审核通过 的无法下订单
        // 修改用户的审核状态为通过
        member.setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());
        memberMapper.updateByPrimaryKeySelective(member);

        // 是否实物订单
        boolean isPhysical = order.getPhysicalFlag().equals(Byte.valueOf("1"));

        // 实物订单改成已支付 ； 虚拟订单改成已完成
        boolean updateFail;
        if (isPhysical) {
            updateFail = customOrderMapper.updateStatusPaidPhysical(order.getId(), payWay, vipStartTime, vipEndTime, product.getName(), thirdOrderNo,isSandBox) != 1;
            // 更新库存
            product.setStock(product.getStock() - order.getQuantity());
            productSkuMapper.updateByPrimaryKeySelective(product);
        } else {
            updateFail = customOrderMapper.updateStatusDone(order.getId(), payWay, vipStartTime, vipEndTime, product.getName(), thirdOrderNo,isSandBox) != 1;
        }

        if (updateFail) {
            log.warn("OrderServiceImpl -> orderDone -> updateFail -> orderNo -> {} ", orderNo);
            throw new BusinessException(OrderExceptionEnum.STATUS);
        }

        // 刷新缓存
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        BeanUtils.copyProperties(member, memberInfoDTO);
        memberInfoDTO.setOrganizationVipEndTime(organizationVipEndTime);

        RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberInfoDTO.getId());
        bucket.set(JSONUtil.toJsonStr(memberInfoDTO), 120L, TimeUnit.MINUTES);

        // 提交事件
        publishEventComponent.publishEvent(new BecomeVipEvent(BecomeVipEventParam.builder()
                .memberId(memberId)
                .vipStartTime(vipStartTime)
                .vipEndTime(vipEndTime)
                .build()));
    }


    @Override
    public CustomOrder selectByOrderNo(Long id) {
        log.info("OrderServiceImpl -> selectByOrderNo -> start -> param -> {}", id);
        return customOrderMapper.selectByOrderNo(id);
    }

    @Override
    public boolean isOrderDone(Long orderNo) {
        Integer status = customOrderMapper.getOrderStatusByOrderNo(orderNo);
        return OrderStatusEnum.DONE.getType().equals(status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void labOrderDone(LabOrderDoneReq req) {
        orderDone(req.getOrderNo(), PayWayEnum.UNKNOWN.getType(), true);
    }

    /**
     * 检查第三方订单是否重复
     *
     * @param thirdOrderNo 第三方订单号
     * @return true-数据库中已有此订单 false-数据库中没有
     */
    private boolean hasThirdOrderNo(String thirdOrderNo) {
        return customOrderMapper.countThirdOrderNo(thirdOrderNo) > 0;
    }
}
