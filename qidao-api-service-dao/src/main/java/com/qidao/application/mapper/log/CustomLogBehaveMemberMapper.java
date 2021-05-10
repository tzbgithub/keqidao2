package com.qidao.application.mapper.log;

import com.qidao.application.entity.log.LogBehaveMember;
import com.qidao.application.entity.log.LogBehaveMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomLogBehaveMemberMapper {
    /**
     * 清空浏览记录
     * @param id
     * @return
     */
    boolean deleteBehaveMember(Long id);

}