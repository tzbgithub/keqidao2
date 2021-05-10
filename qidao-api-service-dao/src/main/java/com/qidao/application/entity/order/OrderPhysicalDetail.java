package com.qidao.application.entity.order;

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
public class OrderPhysicalDetail {
    private Long id;

    private Long orderId;

    private String addressProvinceCode;

    private String addressProvinceName;

    private String addressCityCode;

    private String addressCityName;

    private String addressAreaCode;

    private String addressAreaName;

    private String addressDetail;

    private String recipientName;

    private String recipientPhone;

    private Integer logisticsType;

    private String logisticsNo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    public enum Column {
        id("id", "id", "BIGINT", false),
        orderId("order_id", "orderId", "BIGINT", false),
        addressProvinceCode("address_province_code", "addressProvinceCode", "VARCHAR", false),
        addressProvinceName("address_province_name", "addressProvinceName", "VARCHAR", false),
        addressCityCode("address_city_code", "addressCityCode", "VARCHAR", false),
        addressCityName("address_city_name", "addressCityName", "VARCHAR", false),
        addressAreaCode("address_area_code", "addressAreaCode", "VARCHAR", false),
        addressAreaName("address_area_name", "addressAreaName", "VARCHAR", false),
        addressDetail("address_detail", "addressDetail", "VARCHAR", false),
        recipientName("recipient_name", "recipientName", "VARCHAR", false),
        recipientPhone("recipient_phone", "recipientPhone", "VARCHAR", false),
        logisticsType("logistics_type", "logisticsType", "INTEGER", false),
        logisticsNo("logistics_no", "logisticsNo", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false);

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