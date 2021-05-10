package com.qidao.application.service.invoice;

import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.invoice.InvoiceAddReq;
import com.qidao.application.model.invoice.InvoiceQuery;
import com.qidao.application.model.invoice.InvoiceRes;
import com.qidao.application.model.invoice.InvoiceUpdateReq;
import com.qidao.framework.service.ServicePage;

import java.util.List;

/**
 * @author liu Le
 * @create 2021-01-09 15:48
 */
public interface InvoiceService {

    /**
    * 添加发票 <br>
    * @param invoiceAddReq {@link InvoiceAddReq} - 待添加的发票对象
    * @return  true-添加成功 false-添加失败
    */
    Boolean insert(InvoiceAddReq invoiceAddReq);

    /**
    * 分页查询该用户申请且已被处理所有发票 <br>
    * @param invoiceQuery {@link InvoiceQuery} - 发票分页对象
    * @return 集合 {@link InvoiceRes}
    */
    ServicePage<List<InvoiceRes>> getListDo(InvoiceQuery invoiceQuery);

    /**
    * 分页查询该用户申请且未被处理的所有发票 <br>
    * @param invoiceQuery {@link InvoiceQuery} - 发票分页对象
    * @return 集合 {@link InvoiceRes}
    */
    ServicePage<List<InvoiceRes>> getListUndo(InvoiceQuery invoiceQuery);

    /**
    * 根据发票ID删除该发票相关信息 <br>
    * @param  baseIdQuery {@link BaseIdQuery} - 发票ID（主键）
    * @return  true-删除成功 false-删除失败
    */
    Boolean deleteById(BaseIdQuery baseIdQuery);

    /**
    * 修改发票相关信息 <br>
    * @param  invoiceUpdateReq {@link InvoiceUpdateReq} - 待修改发票对象
    * @return true-修改成功 false-修改失败
    */
    Boolean update(InvoiceUpdateReq invoiceUpdateReq);
}
