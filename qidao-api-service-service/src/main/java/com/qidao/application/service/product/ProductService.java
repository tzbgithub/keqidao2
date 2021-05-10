package com.qidao.application.service.product;

import com.qidao.application.model.product.ProductPageReq;
import com.qidao.application.model.product.ProductPageRes;
import com.qidao.application.vo.ProductSkuModifyReq;
import com.qidao.application.vo.ProductSkuReq;
import com.qidao.framework.service.ServicePage;

import java.util.List;

public interface ProductService {

    /**
     * 分页获取产品列表
     * @param req 入参 {@link com.qidao.application.model.product.ProductPageReq }
     * @return
     */
    ServicePage<List<ProductPageRes>> findProductSku(ProductPageReq req);

    /**
     * 添加产品
     * @param productSkuReq
     */
    void  saveProductSku(ProductSkuReq productSkuReq);
    /**
     * 修改产品
     * @param productSkuModifyReq
     */
    void  modifyProductSku(ProductSkuModifyReq productSkuModifyReq);

    /**
     * 检查 产品ID 和 第三方产品ID 是束是同一产品
     * @param productSkuId 产品ID
     * @param thirdProductId 第三方产品ID
     * @return true-是 false-否
     */
    Boolean isOneProduct(Long productSkuId, String thirdProductId);
}
