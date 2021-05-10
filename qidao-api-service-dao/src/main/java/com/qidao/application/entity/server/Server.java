package com.qidao.application.entity.server;

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
public class Server {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Long organizedIdPartyA;

    private Long organizedIdPartyB;

    private Long memberIdPartyA;

    private Long memberIdPartyB;

    private Integer status;

    private String title;

    private Integer addressProvinceId;

    private String addressProvinceName;

    private Integer addressCityId;

    private String addressCityName;

    private Integer addressAreaId;

    private String addressAreaName;

    private Long specAreaId;

    private Long specAmountId;

    private String description;

    private String thumb;

    private String url;

    private LocalDateTime solutionTime;

    private Integer type;

    private Long industryId;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        organizedIdPartyA("organized_id_party_a", "organizedIdPartyA", "BIGINT", false),
        organizedIdPartyB("organized_id_party_b", "organizedIdPartyB", "BIGINT", false),
        memberIdPartyA("member_id_party_a", "memberIdPartyA", "BIGINT", false),
        memberIdPartyB("member_id_party_b", "memberIdPartyB", "BIGINT", false),
        status("status", "status", "INTEGER", false),
        title("title", "title", "VARCHAR", false),
        addressProvinceId("address_province_id", "addressProvinceId", "INTEGER", false),
        addressProvinceName("address_province_name", "addressProvinceName", "VARCHAR", false),
        addressCityId("address_city_id", "addressCityId", "INTEGER", false),
        addressCityName("address_city_name", "addressCityName", "VARCHAR", false),
        addressAreaId("address_area_id", "addressAreaId", "INTEGER", false),
        addressAreaName("address_area_name", "addressAreaName", "VARCHAR", false),
        specAreaId("spec_area_id", "specAreaId", "BIGINT", false),
        specAmountId("spec_amount_id", "specAmountId", "BIGINT", false),
        description("description", "description", "VARCHAR", false),
        thumb("thumb", "thumb", "VARCHAR", false),
        url("url", "url", "VARCHAR", false),
        solutionTime("solution_time", "solutionTime", "TIMESTAMP", false),
        type("type", "type", "INTEGER", false),
        industryId("industry_id", "industryId", "BIGINT", false);

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