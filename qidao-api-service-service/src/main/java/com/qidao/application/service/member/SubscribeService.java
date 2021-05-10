package com.qidao.application.service.member;

import com.qidao.application.model.member.subscribe.*;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/14 3:49 PM
 */
public interface SubscribeService {

    /**
     * <pre>
     * 提供给其他Controller、Service调用
     * (1) 用户修改个人信息时，调用该方法，更新"其他用户"的关注/屏蔽列表中显示的(被关注/屏蔽)的用户信息；
     * (2) 组织机构修改个体信息时，调用该方法，更新"其他用户"的关注/屏蔽列表中显示的(被关注/屏蔽)的组织机构信息；
     * 这里传递的是subscribeId，而不是memberId，因为修改信息的（用户/组织机构）充当别人列表中的"关注/屏蔽"对象。
     * serviceImpl不再对传入的参数校验是否为null，所以如果调用该方法，必须保证传入的参数req不为null
     * 这里根据SubscribeEditReq中不为null的字段进行更新(subscribeId必须不为null,其他字段若不为null则更新)
     * 测试代码如下：
     *      (1) 用户修改信息
     *         subscribeService.updateAllSubscribe(
     *                                  SubscribeUpdateAllReq.builder()
     *                                          .subscribeId(130899165380610L)
     *                                          .subscribeHeadImg("<img>")
     *                                          .subscribeName("subscribeName")
     *                                          .subscribeEducation("博士")
     *                                          .subscribePosition("position")
     *                                          .subscribeOrganizationName("organization")
     *                                          .build()
     *                          );
     *      (2) 组织机构修改信息
     *         subscribeService.updateAllSubscribe(
     *                 SubscribeUpdateAllReq.builder()
     *                         .subscribeId(130899165380610L)
     *                         .subscribeOrganizationName("organization-XX实验室等")
     *                         .subscribeBelong("所属机构-XX学校等")
     *                         .build()
     *         );
     * </pre>
     *
     * @param req [请求]更新关注屏蔽表的用户冗余信息
     * @return SubscribeEditDTO，包含sqlCount和success两个用于判断update是否成功的字段。sqlCount=数据库update影响的语句数量，success=(sqlCount>0)
     */
    SubscribeEditDTO updateAllSubscribe(SubscribeUpdateAllReq req);

    /**
     * 分页获取用户的"我的关注"列表
     */
    SubscribeDTO getFollowList(SubscribeGetFollowListReq req);

    /**
     * 用户A关注用户B 或 用户A 关注 组织A
     */
    SubscribeDTO addFollow(SubscribeAddFollowReq req);

    /**
     * 用户A取消对用户B的关注
     */
    SubscribeDTO deleteFollow(SubscribeDeleteFollowReq req);

    /**
     * 用户A清空所有关注(取消所有关注)
     */
    SubscribeDTO deleteAllFollow(SubscribeDeleteAllFollowReq req);

    /**
     * 用户获取"我屏蔽的人"列表
     */
    SubscribeDTO getBlockList(SubscribeGetBlockListReq req);

    /**
     * 用户A屏蔽用户B
     */
    SubscribeDTO addBlock(SubscribeAddBlockReq req);

    /**
     * 用户A取消对用户B的屏蔽
     */
    SubscribeDTO deleteBlock(SubscribeDeleteBlockReq req);

    /**
     * 关注组织机构
     */
    void   attentionOrganization(AttentionOrganizationReq attentionOrganizationReq);
    /**
     * 查询用户是否关注组织或个人
     */
    Boolean  findMemberWhetherAttention(Long memberId,Integer type,Long  subscribeId);

}