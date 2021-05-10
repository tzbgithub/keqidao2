package com.qidao.application.controller.product;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.product.ProductPageReq;
import com.qidao.application.model.product.ProductPageRes;
import com.qidao.application.service.product.ProductService;
import com.qidao.application.vo.ProductSkuModifyReq;
import com.qidao.application.vo.ProductSkuReq;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "产品接口")
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("获取产品列表")
    @PostMapping("/getProduct")
    public ResponseResult<ServicePage<List<ProductPageRes>>> getProduct(@RequestBody @Validated ProductPageReq req) {
        log.info("ProductController -> getProduct -> start -> param:{}", req);
        ServicePage<List<ProductPageRes>> productList = productService.findProductSku(req);
        log.info("ProductController -> getProduct -> end");
        return Result.ok(productList);
    }

    @OperLog(title = "添加产品", businessType = BusinessType.OTHER)
    @ApiOperation("添加产品")
    @PostMapping("/saveProductSku")
    public ResponseResult saveProductSku(@RequestBody @Validated ProductSkuReq productSkuReq) {
        productService.saveProductSku(productSkuReq);
        return Result.ok();
    }

    @OperLog(title = "修改产品", businessType = BusinessType.OTHER)
    @ApiOperation("修改产品")
    @PostMapping("/modifyProductSku")
    public ResponseResult modifyProductSku(@RequestBody @Validated ProductSkuModifyReq productSkuModifyReq) {
        productService.modifyProductSku(productSkuModifyReq);
        return Result.ok();
    }
}
