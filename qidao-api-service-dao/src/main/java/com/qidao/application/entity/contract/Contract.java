package com.qidao.application.entity.contract;

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
public class Contract {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Long serverId;

    private Long organizationIdPartyA;

    private Long organizationIdPartyB;

    private Long memberIdPartyA;

    private Long memberIdPartB;

    private String no;

    private LocalDateTime signTime;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Integer status;

    private Integer signAddressProvinceId;

    private String signAddressProvinceName;

    private Integer signAddressCityId;

    private String signAddressCityName;

    private Integer signAddressAreaId;

    private String signAddressAreaName;

    private LocalDateTime confirmTime;

    private String serverTitle;

    public enum Column {
        id("id", "id", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        createBy("create_by", "createBy", "BIGINT", false),
        updateBy("update_by", "updateBy", "BIGINT", false),
        delFlag("del_flag", "delFlag", "TINYINT", false),
        serverId("server_id", "serverId", "BIGINT", false),
        organizationIdPartyA("organization_id_party_a", "organizationIdPartyA", "BIGINT", false),
        organizationIdPartyB("organization_id_party_b", "organizationIdPartyB", "BIGINT", false),
        memberIdPartyA("member_id_party_a", "memberIdPartyA", "BIGINT", false),
        memberIdPartB("member_id_part_b", "memberIdPartB", "BIGINT", false),
        no("no", "no", "VARCHAR", false),
        signTime("sign_time", "signTime", "TIMESTAMP", false),
        beginTime("begin_time", "beginTime", "TIMESTAMP", false),
        endTime("end_time", "endTime", "TIMESTAMP", false),
        status("status", "status", "INTEGER", false),
        signAddressProvinceId("sign_address_province_id", "signAddressProvinceId", "INTEGER", false),
        signAddressProvinceName("sign_address_province_name", "signAddressProvinceName", "VARCHAR", false),
        signAddressCityId("sign_address_city_id", "signAddressCityId", "INTEGER", false),
        signAddressCityName("sign_address_city_name", "signAddressCityName", "VARCHAR", false),
        signAddressAreaId("sign_address_area_id", "signAddressAreaId", "INTEGER", false),
        signAddressAreaName("sign_address_area_name", "signAddressAreaName", "VARCHAR", false),
        confirmTime("confirm_time", "confirmTime", "TIMESTAMP", false),
        serverTitle("server_title", "serverTitle", "VARCHAR", false);

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