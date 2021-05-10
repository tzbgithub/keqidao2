package com.qidao.application.entity.organization;

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
public class Organization {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Integer type;

    private Long responsibleMemberId;

    private String backendImage;

    private String name;

    private String summary;

    private Long industryId;

    private String industryRemark;

    private String fundsId;

    private Long scaleId;

    private LocalDateTime signTime;

    private String license;

    private String qualifications;

    private String addressProvinceId;

    private String addressProvinceName;

    private String addressCityId;

    private String addressCityName;

    private String addressAreaId;

    private String addressAreaName;

    private String addressDetail;

    private Long techScaleId;

    private Integer status;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private Integer verifyStatus;

    private String verifyReason;

    private String belong;

    private Long salesmanId;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        type("type", "type", "INTEGER", false),
        responsibleMemberId("responsible_member_id", "responsibleMemberId", "BIGINT", false),
        backendImage("backend_image", "backendImage", "VARCHAR", false),
        name("name", "name", "VARCHAR", false),
        summary("summary", "summary", "VARCHAR", false),
        industryId("industry_id", "industryId", "BIGINT", false),
        industryRemark("industry_remark", "industryRemark", "VARCHAR", false),
        fundsId("funds_id", "fundsId", "VARCHAR", false),
        scaleId("scale_id", "scaleId", "BIGINT", false),
        signTime("sign_time", "signTime", "TIMESTAMP", false),
        license("license", "license", "VARCHAR", false),
        qualifications("qualifications", "qualifications", "VARCHAR", false),
        addressProvinceId("address_province_id", "addressProvinceId", "VARCHAR", false),
        addressProvinceName("address_province_name", "addressProvinceName", "VARCHAR", false),
        addressCityId("address_city_id", "addressCityId", "VARCHAR", false),
        addressCityName("address_city_name", "addressCityName", "VARCHAR", false),
        addressAreaId("address_area_id", "addressAreaId", "VARCHAR", false),
        addressAreaName("address_area_name", "addressAreaName", "VARCHAR", false),
        addressDetail("address_detail", "addressDetail", "VARCHAR", false),
        techScaleId("tech_scale_id", "techScaleId", "BIGINT", false),
        status("status", "status", "INTEGER", false),
        vipStartTime("vip_start_time", "vipStartTime", "TIMESTAMP", false),
        vipEndTime("vip_end_time", "vipEndTime", "TIMESTAMP", false),
        verifyStatus("verify_status", "verifyStatus", "INTEGER", false),
        verifyReason("verify_reason", "verifyReason", "VARCHAR", false),
        belong("belong", "belong", "VARCHAR", false),
        salesmanId("salesman_id", "salesmanId", "BIGINT", false);

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