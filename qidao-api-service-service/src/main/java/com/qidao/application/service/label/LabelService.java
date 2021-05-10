package com.qidao.application.service.label;

import com.qidao.application.model.label.LabelDTO;
import com.qidao.application.model.label.ListLabelReq;
import com.qidao.application.model.mp.label.ListLabelRes;

import java.util.List;

/**
 * @author Autuan.Yu
 */
public interface LabelService {
    /**
     * 内容标签查询 (发布成果 选择内容标签)
     * 查询组织机构标签
     * @param req 会员ID
     * @return 标签封闭集合
     */
    List<ListLabelRes> listAchievementLabel(ListLabelReq req);
}
