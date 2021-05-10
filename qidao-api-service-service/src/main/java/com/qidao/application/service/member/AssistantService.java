package com.qidao.application.service.member;

import com.qidao.application.model.member.assistant.AssistantBaseInfoDTO;
import com.qidao.application.model.member.assistant.AssistantInfoRes;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 老师 、 助手 相关
 */
public interface AssistantService {
    /**
     * 移除助手 <br>
     * 移除后助手会变更为<strong>普通会员</strong>身份 <br>
     * VIP 信息会被 <strong>清空</strong>
     *
     * @param teacherId   导师ID
     * @param assistantId 助手ID
     * @return true-操作成功 false-操作失败
     */
    Boolean removeAssistant(@NotNull Long teacherId, @NotNull Long assistantId);

    /**
     * 老师添加助手<br>
     * 会将导师的VIP信息赋给助手
     *
     * @param teacherId   导师ID
     * @param assistantId 助手ID : 必须是当前系统已存在的  普通用户
     * @return true-操作成功 false-操作失败
     */
    Boolean addAssistant(@NotNull Long teacherId, @NotNull Long assistantId);

    /**
     * 查询实验室老师下的所有助手信息<br>
     * 助手有上限限制，此接口不进行分页
     *
     * @param teacherId @NotNull 导师 会员ID
     * @return 助手基本信息集合 {@link AssistantBaseInfoDTO}
     */
    List<AssistantBaseInfoDTO> listAllAssistant(@NotNull Long teacherId);

    /**
     * 根据助手id查询老师以及同老师下助手信息
     * @param memberId 助手id
     * @return 返回对象 {@link AssistantInfoRes}
     */
    AssistantInfoRes listTeacherAssistant(@NotNull Long memberId);

}
