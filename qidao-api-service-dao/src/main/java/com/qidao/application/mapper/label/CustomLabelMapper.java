package com.qidao.application.mapper.label;

import com.qidao.application.entity.label.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CustomLabelMapper {
    List<Label> getByMemberId(Long memberId);

    /**
     * 通过组织查询所属组织标签
     * @param organizationId 组织ID
     * @return
     */
    List<Label> listOrganizationLabel(Long organizationId);


    /**
     * 根据给定的 源id 集合(组织机构id、需求id....)
     * 获取对应的所有标签(物理存在，且未被逻辑删除的)
     * @param sourceIds 源id集合
     * @param type (标签类别：0-动态；1-服务；2-频道；3-用户；4-组织机构 5成果文章)
     * @return 标签List
     */
    List<Label> getLabelsBySourceIds(@Param("sourceIds") Long[] sourceIds,@Param("type") Integer type);
    List<Label> listAchievementLabel(Long memberId);

    /**
     * 查询标签
     * <p>注意，标签可能重复</p>
     */
    String selectLinkLabel (@Param("memberId")Long id,@Param("type")Integer type);
}
