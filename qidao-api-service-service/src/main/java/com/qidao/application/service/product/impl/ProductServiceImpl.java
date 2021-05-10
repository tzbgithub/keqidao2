package com.qidao.application.service.product.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.qidao.application.common.Query;
import com.qidao.application.entity.product.ProductSku;
import com.qidao.application.entity.product.ProductSkuExample;
import com.qidao.application.mapper.organization.CustomOrganizationMapper;
import com.qidao.application.mapper.product.CustomProductMapper;
import com.qidao.application.mapper.product.ProductSkuMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.product.ProductPageReq;
import com.qidao.application.model.product.ProductPageRes;
import com.qidao.application.model.product.enums.ProductErrorEnum;
import com.qidao.application.service.product.ProductService;
import com.qidao.application.vo.ProductSkuModifyReq;
import com.qidao.application.vo.ProductSkuReq;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductSkuMapper productMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Resource
    private CustomProductMapper customProductMapper;

    @Resource
    private CustomOrganizationMapper customOrganizationMapper;

    @Override
    public ServicePage<List<ProductPageRes>> findProductSku(ProductPageReq req) {
        log.info("ProductServiceImpl -> findProductSku -> start -> param:{}", req);
        Query query = new Query();
        Integer organizationType = customOrganizationMapper.getOrganizationType(req.getMemberId());
        log.info("ProductServiceImpl -> findProductSku -> organizationType != null :{}", organizationType != null);
        if (organizationType != null) {
            organizationType += 1;
        }
        query.put("permission", organizationType);
        query.put("canalPermission", null == req.getCanalPermission() ? 1 : req.getCanalPermission());
        query.put("productType", null == req.getProductType() ? 0 : req.getProductType());
        log.info("ProductServiceImpl -> findProductSku -> query -> {}", query);
        Page<ProductSku> page = PageUtil.start(req, () -> customProductMapper.getProduct(query));
        ServicePage<List<ProductPageRes>> res = new ServicePage<>();
        BeanUtils.copyProperties(page, res);
        List<ProductSku> result = page.getResult();
        List<ProductPageRes> products = new ArrayList<>();
        result.forEach(productSku -> {
            ProductPageRes product = new ProductPageRes();
            BeanUtils.copyProperties(productSku, product);
            if (StrUtil.isNotBlank(productSku.getExtra())) {
                product.setExtra(Arrays.asList(productSku.getExtra().split(",")));
            }
            products.add(product);
        });
        res.setResult(products);
        log.info("ProductServiceImpl -> findProductSku -> end -> return -> {}", JSONUtil.toJsonStr(res));
        return res;
    }

    /**
     * 添加产品
     *
     * @param productSkuReq
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProductSku(ProductSkuReq productSkuReq) {
        String name = productSkuReq.getName();
        ProductSkuExample productSkuExample = new ProductSkuExample();
        productSkuExample.createCriteria().andNameEqualTo(name).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        ProductSku productSkus = productMapper.selectOneByExample(productSkuExample);
        if (productSkus != null) {
            throw new BusinessException(ProductErrorEnum.RERETITION_PRODUCT_ERROR);
        }
        ProductSku productSku = new ProductSku();
        List<String> imgs = productSkuReq.getImgs();
        String img = imgs.stream().collect(Collectors.joining(","));
        productSku.setDelFlag(ConstantEnum.NOT_DEL.getByte());
        productSku.setImgs(img);
        productSku.setName(productSkuReq.getName());
        productSku.setId(snowflakeIdWorker53.nextId());
        productSku.setStock(productSkuReq.getStock());
        productSku.setMarketPrice(productSkuReq.getMarketPrice());
        productSku.setSalePrice(productSkuReq.getSalePrice());
        productSku.setSummary(productSkuReq.getSummary());
        productSku.setPermission(productSkuReq.getPermission() == null ? 0 : productSkuReq.getPermission());
        productSku.setServerVal(productSkuReq.getServerVal());
        productSku.setType(productSkuReq.getType());
        productMapper.insertSelective(productSku);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyProductSku(ProductSkuModifyReq productSkuModifyReq) {
        Long id = productSkuModifyReq.getId();
        ProductSkuExample productSkuExample = new ProductSkuExample();

        productSkuExample.createCriteria().andIdEqualTo(id).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        ProductSku productSkus = productMapper.selectOneByExample(productSkuExample);
        if (productSkus == null) {
            throw new BusinessException(ProductErrorEnum.NOTFOUND_PRODUCT_ERROR);
        }
        if (!productSkus.getName().equals(productSkuModifyReq.getName())) {
            productSkuExample.clear();
            productSkuExample.createCriteria().andNameEqualTo(productSkuModifyReq.getName()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            ProductSku productSku = productMapper.selectOneByExample(productSkuExample);
            if (productSku != null) {
                throw new BusinessException(ProductErrorEnum.RERETITION_PRODUCT_ERROR);
            }
            productSkuExample.clear();
            productSkuExample.createCriteria().andIdEqualTo(id).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        }
        ProductSku productSku = new ProductSku();
        List<String> imgs = productSkuModifyReq.getImgs();
        String img = imgs.stream().collect(Collectors.joining(","));
        productSku.setDelFlag(ConstantEnum.NOT_DEL.getByte());
        productSku.setImgs(img);
        productSku.setId(productSkuModifyReq.getId());
        productSku.setName(productSkuModifyReq.getName());
        productSku.setStock(productSkuModifyReq.getStock());
        productSku.setMarketPrice(productSkuModifyReq.getMarketPrice());
        productSku.setSalePrice(productSkuModifyReq.getSalePrice());
        productSku.setSummary(productSkuModifyReq.getSummary());
        productSku.setPermission(productSkuModifyReq.getPermission() == null ? 0 : productSkuModifyReq.getPermission());
        productSku.setServerVal(productSkuModifyReq.getServerVal());
        productSku.setType(productSkuModifyReq.getType());
        productMapper.updateByExampleSelective(productSku, productSkuExample);
    }

    @Override
    public Boolean isOneProduct(Long productSkuId, String thirdProductId) {
        ProductSkuExample example = new ProductSkuExample();
        example.createCriteria()
                .andIdEqualTo(productSkuId)
                .andThirdProductIdEqualTo(thirdProductId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        return productMapper.countByExample(example) == 1;
    }
}
