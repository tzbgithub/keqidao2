package com.qidao.application.entity.config;

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
public class LogOper {
    private Long id;

    private String title;

    private Integer businessType;

    private String method;

    private String requestMethod;

    private Integer operatorType;

    private Long operUserId;

    private Integer thirdType;

    private String thirdId;

    private String operUrl;

    private String operIp;

    private String operLocation;

    private String operParam;

    private String jsonResult;

    private Integer status;

    private String errorMsg;

    private LocalDateTime operTime;

    private String remark;

    private String version;

    public enum Column {
        id("id", "id", "BIGINT", false),
        title("title", "title", "VARCHAR", false),
        businessType("business_type", "businessType", "INTEGER", false),
        method("method", "method", "VARCHAR", false),
        requestMethod("request_method", "requestMethod", "VARCHAR", false),
        operatorType("operator_type", "operatorType", "INTEGER", false),
        operUserId("oper_user_id", "operUserId", "BIGINT", false),
        thirdType("third_type", "thirdType", "INTEGER", false),
        thirdId("third_id", "thirdId", "VARCHAR", false),
        operUrl("oper_url", "operUrl", "VARCHAR", false),
        operIp("oper_ip", "operIp", "VARCHAR", false),
        operLocation("oper_location", "operLocation", "VARCHAR", false),
        operParam("oper_param", "operParam", "VARCHAR", false),
        jsonResult("json_result", "jsonResult", "VARCHAR", false),
        status("status", "status", "INTEGER", false),
        errorMsg("error_msg", "errorMsg", "VARCHAR", false),
        operTime("oper_time", "operTime", "TIMESTAMP", false),
        remark("remark", "remark", "VARCHAR", false),
        version("version", "version", "VARCHAR", false);

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