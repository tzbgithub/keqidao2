package com.qidao.application.mapper.member;

import com.qidao.application.entity.member.Subscribe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/26 2:07 PM
 */
public interface CustomSubscribeMapper {

    /**
     * 根据不同的组织类别，返回不同的 关注/屏蔽列表
     * 注意:这里特指 关注/屏蔽"组织"，不包括 关注/屏蔽"个人"
     *
     * @param subscribe        查询条件
     * @param delFlag          逻辑删除标识符
     * @param organizationType 过滤的组织类别 (实验室/公司)
     * @return
     */
    List<Subscribe> getSubscribeWithOrganizationType(@Param("subscribe") Subscribe subscribe,@Param("delFlag") Integer delFlag, @Param("organizationType") Integer organizationType);
}
