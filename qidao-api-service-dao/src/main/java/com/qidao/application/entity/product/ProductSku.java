package com.qidao.application.entity.product;

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
public class ProductSku {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private String name;

    private String summary;

    private String imgs;

    private BigDecimal salePrice;

    private BigDecimal marketPrice;

    private Integer type;

    private Integer serverVal;

    private Integer permission;

    private Integer stock;

    private Integer status;

    private String url;

    private Integer sequence;

    private String extra;

    private Integer canalPermission;

    private Integer productType;

    private String thirdProductId;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        name("name", "name", "VARCHAR", false),
        summary("summary", "summary", "VARCHAR", false),
        imgs("imgs", "imgs", "VARCHAR", false),
        salePrice("sale_price", "salePrice", "DECIMAL", false),
        marketPrice("market_price", "marketPrice", "DECIMAL", false),
        type("type", "type", "INTEGER", false),
        serverVal("server_val", "serverVal", "INTEGER", false),
        permission("permission", "permission", "INTEGER", false),
        stock("stock", "stock", "INTEGER", false),
        status("status", "status", "INTEGER", false),
        url("url", "url", "VARCHAR", false),
        sequence("sequence", "sequence", "INTEGER", false),
        extra("extra", "extra", "VARCHAR", false),
        canalPermission("canal_permission", "canalPermission", "INTEGER", false),
        productType("product_type", "productType", "INTEGER", false),
        thirdProductId("third_product_id", "thirdProductId", "VARCHAR", false);

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