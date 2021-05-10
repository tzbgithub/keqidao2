package com.qidao.application.entity.order;

import java.math.BigDecimal;
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
public class Order {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Long no;

    private Long productSkuId;

    private Long memberId;

    private Integer status;

    private String memberName;

    private LocalDateTime payTime;

    private BigDecimal payPrice;

    private BigDecimal orderPrice;

    private Integer quantity;

    private Integer payWay;

    private String productSkuName;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private Byte physicalFlag;

    private String thirdOrderNo;

    private LocalDateTime finalVipEndTime;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        no("no", "no", "BIGINT", false),
        productSkuId("product_sku_id", "productSkuId", "BIGINT", false),
        memberId("member_id", "memberId", "BIGINT", false),
        status("status", "status", "INTEGER", false),
        memberName("member_name", "memberName", "VARCHAR", false),
        payTime("pay_time", "payTime", "TIMESTAMP", false),
        payPrice("pay_price", "payPrice", "DECIMAL", false),
        orderPrice("order_price", "orderPrice", "DECIMAL", false),
        quantity("quantity", "quantity", "INTEGER", false),
        payWay("pay_way", "payWay", "INTEGER", false),
        productSkuName("product_sku_name", "productSkuName", "VARCHAR", false),
        vipStartTime("vip_start_time", "vipStartTime", "TIMESTAMP", false),
        vipEndTime("vip_end_time", "vipEndTime", "TIMESTAMP", false),
        physicalFlag("physical_flag", "physicalFlag", "TINYINT", false),
        thirdOrderNo("third_order_no", "thirdOrderNo", "VARCHAR", false);

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