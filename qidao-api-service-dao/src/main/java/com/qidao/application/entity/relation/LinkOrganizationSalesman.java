package com.qidao.application.entity.relation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
public class LinkOrganizationSalesman {
    private Long id;

    private Long organizationId;

    private Long salesmanId;

    private String salesmanName;

    private Long createBy;

    private Long updateBy;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer type;

    private Byte delFlag;

    private String organizationName;

    private Long memberId;

    private String memberName;

    private Integer authoorized;

    private String authorizedImg;

    private Long authorizedSalesman;

    public enum Column {
        id("id", "id", "BIGINT", false),
        organizationId("organization_id", "organizationId", "BIGINT", false),
        salesmanId("salesman_id", "salesmanId", "BIGINT", false),
        salesmanName("salesman_name", "salesmanName", "VARCHAR", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        type("type", "type", "INTEGER", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        organizationName("organization_name", "organizationName", "VARCHAR", false),
        memberId("member_id", "memberId", "BIGINT", false),
        memberName("member_name", "memberName", "VARCHAR", false),
        authoorized("authoorized", "authoorized", "INTEGER", false),
        authorizedImg("authorized_img", "authorizedImg", "VARCHAR", false),
        authorizedSalesman("authorized_salesman", "authorizedSalesman", "BIGINT", false);

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