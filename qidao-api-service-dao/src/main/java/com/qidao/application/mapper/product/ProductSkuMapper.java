package com.qidao.application.mapper.product;

import com.qidao.application.entity.product.ProductSku;
import com.qidao.application.entity.product.ProductSkuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSkuMapper {
    long countByExample(ProductSkuExample example);

    int deleteByExample(ProductSkuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductSku record);

    int insertSelective(@Param("record") ProductSku record, @Param("selective") ProductSku.Column ... selective);

    ProductSku selectOneByExample(ProductSkuExample example);

    List<ProductSku> selectByExample(ProductSkuExample example);

    ProductSku selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductSku record, @Param("example") ProductSkuExample example, @Param("selective") ProductSku.Column ... selective);

    int updateByExample(@Param("record") ProductSku record, @Param("example") ProductSkuExample example);

    int updateByPrimaryKeySelective(@Param("record") ProductSku record, @Param("selective") ProductSku.Column ... selective);

    int updateByPrimaryKey(ProductSku record);

    int batchInsert(@Param("list") List<ProductSku> list);

    int batchInsertSelective(@Param("list") List<ProductSku> list, @Param("selective") ProductSku.Column ... selective);
}