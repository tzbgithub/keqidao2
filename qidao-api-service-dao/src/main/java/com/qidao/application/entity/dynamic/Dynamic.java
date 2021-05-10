package com.qidao.application.entity.dynamic;

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
public class Dynamic {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Long memberId;

    private LocalDateTime publishTime;

    private String title;

    private String url;

    private String video;

    private String thumb;

    private Integer commentNum;

    private String summary;

    private Integer likeNum;

    private Integer verifyStatus;

    private String verifyReason;

    private Long verifyUserId;

    private String img;

    private Integer needVip;

    private Byte hot;

    private Long recheckId;

    private Long technologyMaturity;

    private Long cooperationType;

    private Integer type;

    private String content;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        memberId("member_id", "memberId", "BIGINT", false),
        publishTime("publish_time", "publishTime", "TIMESTAMP", false),
        title("title", "title", "VARCHAR", false),
        url("url", "url", "VARCHAR", false),
        video("video", "video", "VARCHAR", false),
        thumb("thumb", "thumb", "VARCHAR", false),
        commentNum("comment_num", "commentNum", "INTEGER", false),
        summary("summary", "summary", "VARCHAR", false),
        likeNum("like_num", "likeNum", "INTEGER", false),
        verifyStatus("verify_status", "verifyStatus", "INTEGER", false),
        verifyReason("verify_reason", "verifyReason", "VARCHAR", false),
        verifyUserId("verify_user_id", "verifyUserId", "BIGINT", false),
        img("img", "img", "VARCHAR", false),
        needVip("need_vip", "needVip", "INTEGER", false),
        hot("hot", "hot", "TINYINT", false),
        recheckId("recheck_id", "recheckId", "BIGINT", false),
        technologyMaturity("technology_maturity", "technologyMaturity", "BIGINT", false),
        cooperationType("cooperation_type", "cooperationType", "BIGINT", false),
        type("type", "type", "INTEGER", false),
        content("content", "content", "LONGVARCHAR", false);

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