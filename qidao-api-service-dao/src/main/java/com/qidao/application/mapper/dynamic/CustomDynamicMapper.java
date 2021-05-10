package com.qidao.application.mapper.dynamic;

import com.qidao.application.common.Query;
import com.qidao.application.entity.dynamic.*;
import com.qidao.application.vo.DynamicPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CustomDynamicMapper {

    int getTypeByMemberId(Long memberId);

    DynamicDetailed getDynamicDetailed(Long dynamicId);

    List<DynamicFollow> getDynamicFollow(Long memberId);

    List<MyComment> getMyComment(Long memberId);

    List<MyAgree> getMyAgree(Long memberId);

    /**
     * 参数改用对象接收
     * @param dynamicPageVo
     * @return 获取动态列表 {@link DynamicFollow}
     */
    List<DynamicFollow> getDynamicPage(DynamicPageVo dynamicPageVo);

    List<DynamicFollow> getFavorDynamic(Long memberId);

    List<DynamicFollow> getHotDynamic(List<Long> ids , List<Long> subscribeIds);

    /**
     * 获取我发布的动态 <br>
     * 以创建时间倒序排序
     * @param query 查询条件
     * @return 集合 ： {@link DynamicFollow}
     */
    List<DynamicFollow> getMyDynamic(Query query);

    List<DynamicFollow> getMyDynamicByArticle(MyDynamicArticleDo dynamicArticleDo);

    List<DynamicFollow> getOrganizationDynamicByArticle(OrganizationDynamicArticleDo organizationDynamicArticleDo);

    String findDynamicLabels(Long dynamicId);

    /**
     * 获取用于推荐计算的动态实体对象
     * @param publishTimeStart limit 条目数
     * @return
     */
    List<DynamicRecommendCacheDO> listRefreshRecommendCache(String publishTimeStart);

    /**
     * 获取随机动态
     * @return {@link DynamicFollow} 集合
     */
    List<DynamicFollow> getRandomDynamic(@Param("limit") Integer limit);

    /**
     * 获取老师或助手发布的动态<br>
     *     仅限于自己查询，未审核的动态也可以查出来
     * @param query 查询条件
     * @return {@link DynamicFollow}
     */
    List<DynamicFollow> myselfDynamicByArticle(Query query);
}
