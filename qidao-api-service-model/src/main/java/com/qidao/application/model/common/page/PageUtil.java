package com.qidao.application.model.common.page;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qidao.application.model.common.req.BasePageQuery;

/**
 * 分页语句查询封装
 * @author Autuan.Yu
 */
public class PageUtil {
    public static <T> Page<T> start(BasePageQuery pageReq, ISelect select){
        return PageHelper.startPage(pageReq.getOffset(), pageReq.getLimit()).doSelectPage(select);
    }
}
