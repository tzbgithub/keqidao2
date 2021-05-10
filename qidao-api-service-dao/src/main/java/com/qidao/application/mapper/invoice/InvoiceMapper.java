package com.qidao.application.mapper.invoice;

import com.qidao.application.entity.invoice.Invoice;
import com.qidao.application.entity.invoice.InvoiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InvoiceMapper {
    long countByExample(InvoiceExample example);

    int deleteByExample(InvoiceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Invoice record);

    int insertSelective(@Param("record") Invoice record, @Param("selective") Invoice.Column ... selective);

    Invoice selectOneByExample(InvoiceExample example);

    List<Invoice> selectByExample(InvoiceExample example);

    Invoice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Invoice record, @Param("example") InvoiceExample example, @Param("selective") Invoice.Column ... selective);

    int updateByExample(@Param("record") Invoice record, @Param("example") InvoiceExample example);

    int updateByPrimaryKeySelective(@Param("record") Invoice record, @Param("selective") Invoice.Column ... selective);

    int updateByPrimaryKey(Invoice record);

    int batchInsert(@Param("list") List<Invoice> list);

    int batchInsertSelective(@Param("list") List<Invoice> list, @Param("selective") Invoice.Column ... selective);
}