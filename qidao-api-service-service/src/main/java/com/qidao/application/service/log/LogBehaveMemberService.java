package com.qidao.application.service.log;

import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.vo.BehaveMemeberReq;

public interface LogBehaveMemberService {

    void  saveBehaveMemeber(BehaveMemeberReq behaveMemeberReq);

    boolean deleteBehaveMember(BaseIdQuery baseIdQuery);
}
