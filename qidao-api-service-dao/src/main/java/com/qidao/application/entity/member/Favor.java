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
public class Favor {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Long memberId;

    private Long dynamicPushMemberId;

    private String dynamicTitle;

    private String dynamicImg;

    private String dynamicSummary;

    private String dynamicLabelStr;

    private Integer dynamicLikeNum;

    private Integer dynamicCommentNum;

    private LocalDateTime dynamicPushTime;

    private Long dynamicId;

    private Byte checkFavorl;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        memberId("member_id", "memberId", "BIGINT", false),
        dynamicPushMemberId("dynamic_push_member_id", "dynamicPushMemberId", "BIGINT", false),
        dynamicTitle("dynamic_title", "dynamicTitle", "VARCHAR", false),
        dynamicImg("dynamic_img", "dynamicImg", "VARCHAR", false),
        dynamicSummary("dynamic_summary", "dynamicSummary", "VARCHAR", false),
        dynamicLabelStr("dynamic_label_str", "dynamicLabelStr", "VARCHAR", false),
        dynamicLikeNum("dynamic_like_num", "dynamicLikeNum", "INTEGER", false),
        dynamicCommentNum("dynamic_comment_num", "dynamicCommentNum", "INTEGER", false),
        dynamicPushTime("dynamic_push_time", "dynamicPushTime", "TIMESTAMP", false),
        dynamicId("dynamic_id", "dynamicId", "BIGINT", false);

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