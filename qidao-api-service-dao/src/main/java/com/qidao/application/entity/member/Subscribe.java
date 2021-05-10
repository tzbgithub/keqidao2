package com.qidao.application.entity.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscribe {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Long memberId;

    private Long subscribeId;

    private LocalDateTime subscribeTime;

    private Integer type;

    private String subscribeHeadImg;

    private String subscribeName;

    private String subscribePosition;

    private String subscribeOrganizationName;

    private Integer subscribeType;

    private String subscribeEducation;

    private String subscribeBelong;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        memberId("member_id", "memberId", "BIGINT", false),
        subscribeId("subscribe_id", "subscribeId", "BIGINT", false),
        subscribeTime("subscribe_time", "subscribeTime", "TIMESTAMP", false),
        type("type", "type", "INTEGER", false),
        subscribeHeadImg("subscribe_head_img", "subscribeHeadImg", "VARCHAR", false),
        subscribeName("subscribe_name", "subscribeName", "VARCHAR", false),
        subscribePosition("subscribe_position", "subscribePosition", "VARCHAR", false),
        subscribeOrganizationName("subscribe_organization_name", "subscribeOrganizationName", "VARCHAR", false),
        subscribeType("subscribe_type", "subscribeType", "INTEGER", false),
        subscribeEducation("subscribe_education", "subscribeEducation", "VARCHAR", false),
        subscribeBelong("subscribe_belong", "subscribeBelong", "VARCHAR", false);

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