package com.qidao.application.service.event.member;

import cn.hutool.core.util.StrUtil;
import com.qidao.application.entity.comment.Comment;
import com.qidao.application.entity.comment.CommentExample;
import com.qidao.application.entity.member.Subscribe;
import com.qidao.application.entity.member.SubscribeExample;
import com.qidao.application.mapper.comment.CommentMapper;
import com.qidao.application.mapper.member.SubscribeMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.member.MemberUpdateEventParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * 会员信息修改事件监听
 **/
@Component
@Slf4j
public class MemberUpdateEventListen implements ApplicationListener<MemberUpdateEvent> {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SubscribeMapper subscribeMapper;

    @Override
    public void onApplicationEvent(MemberUpdateEvent memberUpdateEvent) {
        log.info("MemberUpdateEventListen -> ApplicationListener -> 事件监听 -> onApplicationEvent");


        MemberUpdateEventParam source = memberUpdateEvent.getSourceToParam();
        //会员注销事件
        if (MemberUpdateEventParam.MemberUpdateEventEnum.CANCELLATION_MEMBER.equals(source.getMemberUpdateEnum())) {
            //todo 是否清理会员数据
            log.info("MemberUpdateEventListen -> ApplicationListener -> 会员注销事件 -> source:{}", source);
            return;
        }

        //更新评论信息
        if (StrUtil.isNotBlank(source.getMemberName()) || StrUtil.isNotBlank(source.getMemberHeadImg())) {
            Comment comment = new Comment();
            comment.setMemberName(source.getMemberName());
            comment.setMemberHeadImg(source.getMemberHeadImg());
            CommentExample example = new CommentExample();
            example.createCriteria()
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                    .andMemberIdEqualTo(source.getMemberId());
            int i = commentMapper.updateByExampleSelective(comment, example);
            log.info("MemberUpdateEventListen -> ApplicationListener -> 更新评论信息 -> source:{} -> size:{}", source, i);
        }

        //更新 关注
        Subscribe subscribe = new Subscribe();
        subscribe.setSubscribeHeadImg(source.getMemberHeadImg());
        subscribe.setSubscribeName(source.getMemberName());
        subscribe.setSubscribePosition(source.getMemberPosition());
        subscribe.setSubscribeOrganizationName(source.getMemberOrganizationName());
        subscribe.setSubscribeType(source.getMemberType());
        subscribe.setSubscribeEducation(source.getEducation());
        subscribe.setSubscribeBelong(source.getBelong());

        if (new Subscribe().equals(subscribe)) {
            return;
        }

        SubscribeExample example = new SubscribeExample();
        example.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andSubscribeIdEqualTo(source.getMemberId());
        int i = subscribeMapper.updateByExampleSelective(subscribe, example);
        log.info("MemberUpdateEventListen -> ApplicationListener -> 更新关注信息 -> source:{} -> size:{}", source, i);
    }

}
