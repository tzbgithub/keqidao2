package com.qidao.application.mapper.product;

import com.qidao.application.common.Query;
import com.qidao.application.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomProductMapper {

    List<ProductSku> getProduct(Query query);

}
