package com.qidao.application.entity.relation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
/**
 * 标签中间表
 */
@ApiModel(value = "object")
public class LinkLabel {
    @ApiModelProperty(name = "id", value = "主键", required = true,example = "23234234234234234")
    private Long id;
    @ApiModelProperty(name = "createTime", value = "创建时间", required = true,example = "2020-02-02 20:20:20")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateTime", value = "修改", required = true,example = "2020-02-02 20:20:20")
    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;
    @ApiModelProperty(name = "labelId", value = "标签ID", required = true)
    private Long labelId;
    @ApiModelProperty(name = "type", value = "标签类别：0-动态；1-服务；2-频道；3-用户；4-组织机构", required = true)
    private Integer type;
    @ApiModelProperty(name = "sourceId", value = "关联对象的主键ID（eg：动态ID、用户ID等）", required = true)
    private Long sourceId;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        labelId("label_id", "labelId", "BIGINT", false),
        type("type", "type", "INTEGER", false),
        sourceId("source_id", "sourceId", "BIGINT", false);

        private static final String BEGINNING_DELIMITER = "\"";

        private static final String ENDING_DELIMITER = "\"";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}