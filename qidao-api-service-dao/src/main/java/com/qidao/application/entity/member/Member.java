package com.qidao.application.entity.member;

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
public class Member {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private String no;

    private Integer level;

    private Long organizationId;

    private Integer role;

    private Long positionId;

    private Long educationId;

    private String phone;

    private Integer pushStatus;

    private String belong;

    private String password;

    private String headImage;

    private String backendImage;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private Long industryId;

    private String name;

    private String email;

    private String unionId;

    private String qualifications;

    private Integer verifyStatus;

    private String verifyReason;

    private String license;

    private Long verifyUserId;

    private String imEasemobUsername;

    private Integer imEasemobActive;

    private String jobPosition;

    private Long teacherId;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        no("no", "no", "VARCHAR", false),
        level("level", "level", "INTEGER", false),
        organizationId("organization_id", "organizationId", "BIGINT", false),
        role("role", "role", "INTEGER", false),
        positionId("position_id", "positionId", "BIGINT", false),
        educationId("education_id", "educationId", "BIGINT", false),
        phone("phone", "phone", "CHAR", false),
        pushStatus("push_status", "pushStatus", "INTEGER", false),
        belong("belong", "belong", "VARCHAR", false),
        password("password", "password", "VARCHAR", false),
        headImage("head_image", "headImage", "VARCHAR", false),
        backendImage("backend_image", "backendImage", "VARCHAR", false),
        vipStartTime("vip_start_time", "vipStartTime", "TIMESTAMP", false),
        vipEndTime("vip_end_time", "vipEndTime", "TIMESTAMP", false),
        industryId("industry_id", "industryId", "BIGINT", false),
        name("name", "name", "VARCHAR", false),
        email("email", "email", "VARCHAR", false),
        unionId("union_id", "unionId", "VARCHAR", false),
        qualifications("qualifications", "qualifications", "VARCHAR", false),
        verifyStatus("verify_status", "verifyStatus", "INTEGER", false),
        verifyReason("verify_reason", "verifyReason", "VARCHAR", false),
        license("license", "license", "VARCHAR", false),
        verifyUserId("verify_user_id", "verifyUserId", "BIGINT", false),
        imEasemobUsername("im_easemob_username", "imEasemobUsername", "VARCHAR", false),
        imEasemobActive("im_easemob_active", "imEasemobActive", "INTEGER", false),
        jobPosition("job_position", "jobPosition", "VARCHAR", false),
        teacherId("teacher_id", "teacherId", "BIGINT", false);

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