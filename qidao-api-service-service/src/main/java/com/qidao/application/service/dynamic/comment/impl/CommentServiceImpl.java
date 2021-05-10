package com.qidao.application.service.dynamic.comment.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import com.qidao.application.entity.comment.Comment;
import com.qidao.application.entity.comment.CommentExample;
import com.qidao.application.entity.dynamic.Dynamic;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.relation.LogBehaveDynamic;
import com.qidao.application.entity.relation.LogBehaveDynamicExample;
import com.qidao.application.mapper.comment.CommentMapper;
import com.qidao.application.mapper.comment.CustomCommentMapper;
import com.qidao.application.mapper.dynamic.DynamicMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.relation.LogBehaveDynamicMapper;
import com.qidao.application.model.dynamic.DynamicExceptionEnum;
import com.qidao.application.model.dynamic.comment.CommentAgreeReq;
import com.qidao.application.model.dynamic.comment.CommentPageReq;
import com.qidao.application.model.dynamic.comment.CommentPushReq;
import com.qidao.application.model.dynamic.comment.CommentRes;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dynamic.DynamicConstantEnum;
import com.qidao.application.model.dynamic.comment.enums.CommentErrorEnum;
import com.qidao.application.service.dynamic.comment.CommentService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("commentService")
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CustomCommentMapper customCommentMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private LogBehaveDynamicMapper logBehaveDynamicMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker;

    @Resource
    private DynamicMapper dynamicMapper;

    /**
     * 发表评论
     * @param req
     * @return
     */
    @Override
    public Boolean pushComment(CommentPushReq req) {
        log.info("CommentServiceImpl -> pushComment -> start -> params:{}" , req);
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andMemberIdEqualTo(req.getMemberId())
                .andDynamicIdEqualTo(req.getDynamicId());
        Comment queryComment = commentMapper.selectOneByExample(commentExample);
        log.info("CommentServiceImpl -> pushComment -> queryComment != null -> {}" , queryComment != null);
        if (queryComment != null){
            log.error("CommentServiceImpl -> pushComment -> error -> {}",DynamicExceptionEnum.AFTER_COMMENT.getMessage());
            throw new BusinessException(DynamicExceptionEnum.AFTER_COMMENT);
        }
        String content = req.getContent();
        log.info("CommentServiceImpl -> pushComment -> 判断评论内容是否大于100字 字数:{}" , content.length());
        if (content.length() > 100){
            log.error("CommentServiceImpl -> pushComment -> error -> 评论内容不能大于100字");
            throw new BusinessException("评论内容不能大于100字");
        }
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        Comment comment = new Comment();
        BeanUtils.copyProperties(req , comment);
        comment.setMemberName(member.getName());
        comment.setId(snowflakeIdWorker.nextId());
        comment.setMemberHeadImg(member.getHeadImage());
        comment.setCommentTime(LocalDateTime.now());
        comment.setVerifyStatus(DynamicConstantEnum.COMMENT_VERIFY_INIT.getCode());
        log.info("CommentServiceImpl -> pushComment -> end");
        return commentMapper.insertSelective(comment) > 0;
    }

    /**
     * 删除评论
     * @param commentId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId , Long memberId) {
        log.info("CommentServiceImpl -> deleteComment -> start -> commentId:{} , memberId:{}" , commentId , memberId);
        int commentPublisher = customCommentMapper.checkCommentPublisher(memberId, commentId);
        int dynamicPublisher = customCommentMapper.checkDynamicPublisher(memberId, commentId);
        log.info("CommentServiceImpl -> deleteComment -> 判断数据是否小于等于0 -> commentPublisher：{},dynamicPublisher:{}",commentPublisher , dynamicPublisher);
        if (dynamicPublisher <= 0 && commentPublisher <= 0){
            throw new BusinessException("只有动态发布者或评论发布者才可以删除该评论");
        }
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(commentId);
        Comment comment = commentMapper.selectOneByExample(example);
        log.info("CommentServiceImpl -> deleteComment -> 判断查询的comment是否为空 -> comment:{}",comment);
        if (comment == null){
            throw new BusinessException("评论已被删除");
        }
        comment.setDelFlag(ConstantEnum.DELETED.getByte());
        if (comment.getVerifyStatus().equals(DynamicConstantEnum.COMMENT_VERIFY_PASS.getCode())){
            Dynamic dynamic = dynamicMapper.selectByPrimaryKey(comment.getDynamicId());
            dynamic.setCommentNum(dynamic.getCommentNum()-1);
            dynamicMapper.updateByPrimaryKeySelective(dynamic);
        }
        commentMapper.updateByPrimaryKeySelective(comment);
        log.info("CommentServiceImpl -> deleteComment -> end ");
    }

    /**
     * 根据动态ID分页查询评论
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<CommentRes>> getCommentByDynamicId(CommentPageReq req) {
        log.info("CommentServiceImpl -> getCommentByDynamicId -> start -> param:{}",req);
        Long dynamicId = req.getDynamicId();
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("like_num desc");
        commentExample.createCriteria()
                .andVerifyStatusEqualTo(DynamicConstantEnum.COMMENT_VERIFY_PASS.getCode())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andDynamicIdEqualTo(dynamicId);
        Page<Comment> page = PageUtil.start(req , () -> commentMapper.selectByExampleWithBLOBs(commentExample));
        log.info("CommentServiceImpl -> getCommentByDynamicId -> 判断page是否为空page：{}",page);
        if (page != null){
            ServicePage<List<CommentRes>> commentPage = new ServicePage<>();
            BeanUtils.copyProperties(page , commentPage);
            List<Comment> commentList = page.getResult();
            log.info("CommentServiceImpl -> getCommentByDynamicId -> 判断commentList是否为空commentList：{}",commentList);
            if (CollUtil.isNotEmpty(commentList)){
                List<CommentRes> commentVos = new ArrayList<>();
                for (Comment comment : commentList) {
                    CommentRes commentVo = new CommentRes();
                    BeanUtils.copyProperties(comment , commentVo);
                    commentVo.setAgreeStatus(agreeStatus(commentVo.getId() , req.getMemberId()));
                    commentVos.add(commentVo);
                }
                commentPage.setResult(commentVos);
            }
            log.info("CommentServiceImpl -> getCommentByDynamicId -> end");
            return commentPage;
        }
        log.info("CommentServiceImpl -> getCommentByDynamicId -> end");
        return null;
    }

    /**
     * 点赞
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean agree(CommentAgreeReq req) {
        log.info("CommentServiceImpl -> agree -> start -> param:{}" , req);
        Long commentId = req.getCommentId();
        Long memberId = req.getMemberId();
        LogBehaveDynamicExample example = new LogBehaveDynamicExample();
        example.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_COMMENT.getCode())
                .andSourceIdEqualTo(commentId);
        LogBehaveDynamic behaveDynamic = logBehaveDynamicMapper.selectOneByExample(example);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        log.info("CommentServiceImpl -> agree -> member == null :{}", member == null);
        if (member == null){
            log.error("CommentServiceImpl -> agree -> error :{}", DynamicExceptionEnum.NOT_EXIST.getMessage());
            throw new BusinessException(DynamicExceptionEnum.NOT_EXIST);
        }
        log.info("CommentServiceImpl -> agree -> behaveDynamic != null -> behaveDynamic:{}",behaveDynamic);
        if (behaveDynamic != null) {
            log.info("CommentServiceImpl -> agree -> DelFlag == 1 -> DelFlag:{}",behaveDynamic.getDelFlag());
            if (behaveDynamic.getDelFlag() == ConstantEnum.DELETED.getByte()){
                behaveDynamic.setDelFlag(ConstantEnum.NOT_DEL.getByte());
                int i = logBehaveDynamicMapper.updateByPrimaryKeySelective(behaveDynamic);
                comment.setLikeNum(comment.getLikeNum()+1);
                int update = commentMapper.updateByPrimaryKeySelective(comment);
                log.info("CommentServiceImpl -> agree -> end -> res:{}",i + update == 2);
                return i + update == 2;
            }
            log.info("CommentServiceImpl -> agree -> DelFlag == 0 -> DelFlag:{}",behaveDynamic.getDelFlag());
            if (behaveDynamic.getDelFlag() == ConstantEnum.NOT_DEL.getByte()){
                log.error("CommentServiceImpl -> agree -> error -> msg:{}",CommentErrorEnum.AGREE.getMessage());
                throw new BusinessException(CommentErrorEnum.AGREE);
            }
        }
        int insert = logBehaveDynamicMapper.insertSelective(LogBehaveDynamic.builder()
                .id(snowflakeIdWorker.nextId())
                .memberId(memberId)
                .delFlag(ConstantEnum.NOT_DEL.getByte())
                .type(DynamicConstantEnum.LIKE_TYPE_COMMENT.getCode())
                .sourceId(commentId)
                .build());
        comment.setLikeNum(comment.getLikeNum()+1);
        int update = commentMapper.updateByPrimaryKeySelective(comment);
        log.info("CommentServiceImpl -> agree -> end -> res:{}", insert + update == 2);
        return insert + update == 2;
    }

    /**
     * 取消赞
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disagree(CommentAgreeReq req) {
        log.info("CommentServiceImpl -> disagree -> start -> param:{}" , req);
        Long commentId = req.getCommentId();
        Long memberId = req.getMemberId();
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdEqualTo(commentId).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Comment comment = commentMapper.selectOneByExample(commentExample);
        log.info("CommentServiceImpl -> disagree -> comment != null :{}",ObjectUtil.isEmpty(comment));
        if (ObjectUtil.isEmpty(comment)){
            log.error("CommentServiceImpl -> disagree -> error -> message：{}",CommentErrorEnum.COMMENT_DELETED.getMessage());
            throw new BusinessException(CommentErrorEnum.COMMENT_DELETED);
        }
        LogBehaveDynamicExample example = new LogBehaveDynamicExample();
        example.createCriteria()
                .andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_COMMENT.getCode())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andSourceIdEqualTo(commentId)
                .andMemberIdEqualTo(memberId);
        LogBehaveDynamic behaveDynamic = logBehaveDynamicMapper.selectOneByExample(example);
        log.info("CommentServiceImpl -> disagree -> behaveDynamic == null -> behaveDynamic:{}",behaveDynamic);
        if (behaveDynamic == null){
            log.error("CommentServiceImpl -> disagree -> error -> message：{}",CommentErrorEnum.NOT_AGREE.getMessage());
            throw new BusinessException(CommentErrorEnum.NOT_AGREE);
        }
        behaveDynamic.setDelFlag(ConstantEnum.DELETED.getByte());
        logBehaveDynamicMapper.updateByPrimaryKeySelective(behaveDynamic);
        boolean res = commentMapper.updateByPrimaryKeySelective(Comment.builder().id(commentId).likeNum(comment.getLikeNum()-1).build())>0;
        log.info("CommentServiceImpl -> disagree -> end -> res:{}",res);
        return res;
     }

    /**
     * 验证用户是否有删除评论的权限
     * @param commentId
     * @param memberId
     * @return
     */
    @Override
    public int checkRole(Long commentId, Long memberId) {
        log.info("CommentServiceImpl -> checkRole -> start -> commentId:{} , memberId:{}" , commentId , memberId);
        int commentPublisher = customCommentMapper.checkCommentPublisher(memberId, commentId);
        int dynamicPublisher = customCommentMapper.checkDynamicPublisher(memberId, commentId);
        log.info("CommentServiceImpl -> checkRole -> 判断查询的结果是否为0 -> commentPublisher:{} , dynamicPublisher:{}" , commentPublisher , dynamicPublisher);
        if (commentPublisher > 0 || dynamicPublisher > 0){
            log.info("CommentServiceImpl -> checkRole -> end");
            return 0;
        }
        log.info("CommentServiceImpl -> checkRole -> end");
        return 1;
    }

    /**
     * 获取评论点赞状态
     * @param commentId
     * @param memberId
     * @return
     */
    private Boolean agreeStatus(Long commentId , Long memberId){
        LogBehaveDynamicExample example = new LogBehaveDynamicExample();
        example.createCriteria()
                .andTypeEqualTo(DynamicConstantEnum.LIKE_TYPE_COMMENT.getCode())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andSourceIdEqualTo(commentId)
                .andMemberIdEqualTo(memberId);
        LogBehaveDynamic behaveDynamic = logBehaveDynamicMapper.selectOneByExample(example);
        return ObjectUtil.isNotEmpty(behaveDynamic);
    }
}
