package com.qidao.application.service.dynamic.dynamic;

import com.qidao.application.model.dynamic.*;
import com.qidao.application.model.member.favor.FavorPageReq;
import com.qidao.application.model.member.favor.FavorPageRes;
import com.qidao.framework.service.ServicePage;

import java.time.LocalDateTime;
import java.util.List;

public interface DynamicService {

    /**
     * 发布动态
     *
     * @param req
     * @return
     */
    Boolean pushDynamic(DynamicPushReq req);

    /**
     * 分页获取动态列表
     *
     * @param req
     * @return
     */
    ServicePage<List<DynamicPageRes>> getDynamic(DynamicPageReq req);

    /**
     * 根据动态ID删除动态及评论
     *
     * @param dynamicId
     */
    void deleteDynamicById(Long dynamicId, Long memberId);

    /**
     * 获取动态详情
     *
     * @param dynamicId
     * @param memberId
     * @return
     */
    DynamicDetailedRes getDynamicDetailed(Long dynamicId, Long memberId);

    /**
     * 点赞
     *
     * @param req
     * @return
     */
    Boolean agreeDynamic(DynamicAgreeReq req);


    /**
     * 动态取消点赞
     *
     * @param req {@link DynamicDisagreeReq 请求对象}
     * @return true-成功 false-失败
     */
    Boolean disagreeDynamic(DynamicDisagreeReq req);

    /**
     * 获取动态关注列表
     *
     * @param req {@link DynamicFollowReq 请求对象}
     * @return {@link DynamicFollowRes 分页列表}
     */
    ServicePage<List<DynamicFollowRes>> getDynamicFollow(DynamicFollowReq req);

    /**
     * 获取我的评论
     *
     * @param req
     * @return
     */
    ServicePage<List<MyCommentRes>> getMyComment(MyCommentReq req);

    /**
     * 获取我的点赞
     *
     * @param req
     * @return
     */
    ServicePage<List<MyAgreeRes>> getMyAgree(MyAgreeReq req);

    /**
     * 收藏列表
     *
     * @param req
     * @return
     */
    ServicePage<List<FavorPageRes>> getByMemberId(FavorPageReq req);

    /**
     * 获取热门动态列表
     *
     * @param req
     * @return
     */
    ServicePage<List<DynamicHotRes>> getHotDynamic(DynamicHotReq req);

    /**
     * 获取我发布的动态 ： 未审核通过的动态也可以在这里查询出来
     *
     * @param req {@link MyDynamicReq}
     * @return 分页集合 : {@link MyDynamicRes}
     */
    ServicePage<List<MyDynamicRes>> getMyDynamic(MyDynamicReq req);

    /**
     * 根据发布类型获取个人空间动态<br> 自己看自己的空间未审核也可以看见<br> 别人看只能看见审核过的<br>
     *
     * @param req 请求对象 {@link MyDynamicArticleReq}
     * @return 分页集合 {@link MyDynamicArticlePageRes}
     */
    ServicePage<List<MyDynamicArticlePageRes>> getMyDynamicArticle(MyDynamicArticleReq req);

    /**
     * 根据发布类型获取实验室空间动态<br> 实验室内部成员未审核也可以看见 <br>别人看只能看见审核过的<br>
     *
     * @param req 请求对象 {@link OrganizationDynamicArticleReq}
     * @return 分页集合 {@link OrganizationDynamicArticleRes}
     */
    ServicePage<List<OrganizationDynamicArticleRes>> getOrganizationDynamicArticle(OrganizationDynamicArticleReq req);

    /**
     * 更新用于推荐的动态缓存信息 <br>
     *
     * @param howMany 数量
     */
    void refreshRecommendCache(LocalDateTime howMany);

    /**
     * 根据获取动态列表
     *
     * @param dynamicIdList 动态ID 集合
     * @return 动态列表
     */
    List<DynamicPageRes> getDynamicList(List<Long> dynamicIdList, Long memberId);

    /**
     * 随机动态
     *
     * @param req {@link RandomDynamicReq}
     * @return {@link RandomDynamicRes}
     */
    List<RandomDynamicRes> getRandomDynamic(RandomDynamicReq req);

    /**
     * 老师或助手查看自己发布的动态未审核的也可以看到
     *
     * @param req 请求对象 {@link MyselfDynamicArticleReq}
     * @return 分页集合 {@link MyDynamicArticlePageRes}
     */
    ServicePage<List<MyDynamicArticlePageRes>> myselfMyDynamicArticle(MyselfDynamicArticleReq req);
}
