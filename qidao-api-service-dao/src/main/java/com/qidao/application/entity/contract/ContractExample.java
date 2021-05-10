package com.qidao.application.entity.contract;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContractExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public ContractExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        rows = null;
        offset = null;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        return this.rows;
    }

    public ContractExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public ContractExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public ContractExample page(Integer page, Integer pageSize) {
        this.offset = page * pageSize;
        this.rows = pageSize;
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Long value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Long value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Long value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Long value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Long value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Long value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Long> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Long> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Long value1, Long value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Long value1, Long value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(Long value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(Long value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(Long value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(Long value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(Long value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(Long value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<Long> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<Long> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(Long value1, Long value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(Long value1, Long value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Byte value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Byte value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Byte value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Byte value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Byte value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Byte> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Byte> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Byte value1, Byte value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNull() {
            addCriterion("server_id is null");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNotNull() {
            addCriterion("server_id is not null");
            return (Criteria) this;
        }

        public Criteria andServerIdEqualTo(Long value) {
            addCriterion("server_id =", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotEqualTo(Long value) {
            addCriterion("server_id <>", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThan(Long value) {
            addCriterion("server_id >", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("server_id >=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThan(Long value) {
            addCriterion("server_id <", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThanOrEqualTo(Long value) {
            addCriterion("server_id <=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdIn(List<Long> values) {
            addCriterion("server_id in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotIn(List<Long> values) {
            addCriterion("server_id not in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdBetween(Long value1, Long value2) {
            addCriterion("server_id between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotBetween(Long value1, Long value2) {
            addCriterion("server_id not between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyAIsNull() {
            addCriterion("organization_id_party_a is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyAIsNotNull() {
            addCriterion("organization_id_party_a is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyAEqualTo(Long value) {
            addCriterion("organization_id_party_a =", value, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyANotEqualTo(Long value) {
            addCriterion("organization_id_party_a <>", value, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyAGreaterThan(Long value) {
            addCriterion("organization_id_party_a >", value, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyAGreaterThanOrEqualTo(Long value) {
            addCriterion("organization_id_party_a >=", value, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyALessThan(Long value) {
            addCriterion("organization_id_party_a <", value, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyALessThanOrEqualTo(Long value) {
            addCriterion("organization_id_party_a <=", value, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyAIn(List<Long> values) {
            addCriterion("organization_id_party_a in", values, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyANotIn(List<Long> values) {
            addCriterion("organization_id_party_a not in", values, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyABetween(Long value1, Long value2) {
            addCriterion("organization_id_party_a between", value1, value2, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyANotBetween(Long value1, Long value2) {
            addCriterion("organization_id_party_a not between", value1, value2, "organizationIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBIsNull() {
            addCriterion("organization_id_party_b is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBIsNotNull() {
            addCriterion("organization_id_party_b is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBEqualTo(Long value) {
            addCriterion("organization_id_party_b =", value, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBNotEqualTo(Long value) {
            addCriterion("organization_id_party_b <>", value, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBGreaterThan(Long value) {
            addCriterion("organization_id_party_b >", value, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBGreaterThanOrEqualTo(Long value) {
            addCriterion("organization_id_party_b >=", value, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBLessThan(Long value) {
            addCriterion("organization_id_party_b <", value, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBLessThanOrEqualTo(Long value) {
            addCriterion("organization_id_party_b <=", value, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBIn(List<Long> values) {
            addCriterion("organization_id_party_b in", values, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBNotIn(List<Long> values) {
            addCriterion("organization_id_party_b not in", values, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBBetween(Long value1, Long value2) {
            addCriterion("organization_id_party_b between", value1, value2, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdPartyBNotBetween(Long value1, Long value2) {
            addCriterion("organization_id_party_b not between", value1, value2, "organizationIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyAIsNull() {
            addCriterion("member_id_party_a is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyAIsNotNull() {
            addCriterion("member_id_party_a is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyAEqualTo(Long value) {
            addCriterion("member_id_party_a =", value, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyANotEqualTo(Long value) {
            addCriterion("member_id_party_a <>", value, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyAGreaterThan(Long value) {
            addCriterion("member_id_party_a >", value, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyAGreaterThanOrEqualTo(Long value) {
            addCriterion("member_id_party_a >=", value, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyALessThan(Long value) {
            addCriterion("member_id_party_a <", value, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyALessThanOrEqualTo(Long value) {
            addCriterion("member_id_party_a <=", value, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyAIn(List<Long> values) {
            addCriterion("member_id_party_a in", values, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyANotIn(List<Long> values) {
            addCriterion("member_id_party_a not in", values, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyABetween(Long value1, Long value2) {
            addCriterion("member_id_party_a between", value1, value2, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyANotBetween(Long value1, Long value2) {
            addCriterion("member_id_party_a not between", value1, value2, "memberIdPartyA");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBIsNull() {
            addCriterion("member_id_part_b is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBIsNotNull() {
            addCriterion("member_id_part_b is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBEqualTo(Long value) {
            addCriterion("member_id_part_b =", value, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBNotEqualTo(Long value) {
            addCriterion("member_id_part_b <>", value, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBGreaterThan(Long value) {
            addCriterion("member_id_part_b >", value, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBGreaterThanOrEqualTo(Long value) {
            addCriterion("member_id_part_b >=", value, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBLessThan(Long value) {
            addCriterion("member_id_part_b <", value, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBLessThanOrEqualTo(Long value) {
            addCriterion("member_id_part_b <=", value, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBIn(List<Long> values) {
            addCriterion("member_id_part_b in", values, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBNotIn(List<Long> values) {
            addCriterion("member_id_part_b not in", values, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBBetween(Long value1, Long value2) {
            addCriterion("member_id_part_b between", value1, value2, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartBNotBetween(Long value1, Long value2) {
            addCriterion("member_id_part_b not between", value1, value2, "memberIdPartB");
            return (Criteria) this;
        }

        public Criteria andNoIsNull() {
            addCriterion("no is null");
            return (Criteria) this;
        }

        public Criteria andNoIsNotNull() {
            addCriterion("no is not null");
            return (Criteria) this;
        }

        public Criteria andNoEqualTo(String value) {
            addCriterion("no =", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotEqualTo(String value) {
            addCriterion("no <>", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThan(String value) {
            addCriterion("no >", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThanOrEqualTo(String value) {
            addCriterion("no >=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThan(String value) {
            addCriterion("no <", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThanOrEqualTo(String value) {
            addCriterion("no <=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLike(String value) {
            addCriterion("no like", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotLike(String value) {
            addCriterion("no not like", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoIn(List<String> values) {
            addCriterion("no in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotIn(List<String> values) {
            addCriterion("no not in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoBetween(String value1, String value2) {
            addCriterion("no between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotBetween(String value1, String value2) {
            addCriterion("no not between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNull() {
            addCriterion("sign_time is null");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNotNull() {
            addCriterion("sign_time is not null");
            return (Criteria) this;
        }

        public Criteria andSignTimeEqualTo(LocalDateTime value) {
            addCriterion("sign_time =", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotEqualTo(LocalDateTime value) {
            addCriterion("sign_time <>", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThan(LocalDateTime value) {
            addCriterion("sign_time >", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("sign_time >=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThan(LocalDateTime value) {
            addCriterion("sign_time <", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("sign_time <=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeIn(List<LocalDateTime> values) {
            addCriterion("sign_time in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotIn(List<LocalDateTime> values) {
            addCriterion("sign_time not in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("sign_time between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("sign_time not between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("begin_time is null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeEqualTo(LocalDateTime value) {
            addCriterion("begin_time =", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotEqualTo(LocalDateTime value) {
            addCriterion("begin_time <>", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThan(LocalDateTime value) {
            addCriterion("begin_time >", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("begin_time >=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThan(LocalDateTime value) {
            addCriterion("begin_time <", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("begin_time <=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIn(List<LocalDateTime> values) {
            addCriterion("begin_time in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotIn(List<LocalDateTime> values) {
            addCriterion("begin_time not in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("begin_time between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("begin_time not between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(LocalDateTime value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(LocalDateTime value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(LocalDateTime value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(LocalDateTime value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<LocalDateTime> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<LocalDateTime> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdIsNull() {
            addCriterion("sign_address_province_id is null");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdIsNotNull() {
            addCriterion("sign_address_province_id is not null");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdEqualTo(Integer value) {
            addCriterion("sign_address_province_id =", value, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdNotEqualTo(Integer value) {
            addCriterion("sign_address_province_id <>", value, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdGreaterThan(Integer value) {
            addCriterion("sign_address_province_id >", value, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_address_province_id >=", value, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdLessThan(Integer value) {
            addCriterion("sign_address_province_id <", value, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdLessThanOrEqualTo(Integer value) {
            addCriterion("sign_address_province_id <=", value, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdIn(List<Integer> values) {
            addCriterion("sign_address_province_id in", values, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdNotIn(List<Integer> values) {
            addCriterion("sign_address_province_id not in", values, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdBetween(Integer value1, Integer value2) {
            addCriterion("sign_address_province_id between", value1, value2, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_address_province_id not between", value1, value2, "signAddressProvinceId");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameIsNull() {
            addCriterion("sign_address_province_name is null");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameIsNotNull() {
            addCriterion("sign_address_province_name is not null");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameEqualTo(String value) {
            addCriterion("sign_address_province_name =", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameNotEqualTo(String value) {
            addCriterion("sign_address_province_name <>", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameGreaterThan(String value) {
            addCriterion("sign_address_province_name >", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameGreaterThanOrEqualTo(String value) {
            addCriterion("sign_address_province_name >=", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameLessThan(String value) {
            addCriterion("sign_address_province_name <", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameLessThanOrEqualTo(String value) {
            addCriterion("sign_address_province_name <=", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameLike(String value) {
            addCriterion("sign_address_province_name like", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameNotLike(String value) {
            addCriterion("sign_address_province_name not like", value, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameIn(List<String> values) {
            addCriterion("sign_address_province_name in", values, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameNotIn(List<String> values) {
            addCriterion("sign_address_province_name not in", values, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameBetween(String value1, String value2) {
            addCriterion("sign_address_province_name between", value1, value2, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressProvinceNameNotBetween(String value1, String value2) {
            addCriterion("sign_address_province_name not between", value1, value2, "signAddressProvinceName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdIsNull() {
            addCriterion("sign_address_city_id is null");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdIsNotNull() {
            addCriterion("sign_address_city_id is not null");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdEqualTo(Integer value) {
            addCriterion("sign_address_city_id =", value, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdNotEqualTo(Integer value) {
            addCriterion("sign_address_city_id <>", value, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdGreaterThan(Integer value) {
            addCriterion("sign_address_city_id >", value, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_address_city_id >=", value, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdLessThan(Integer value) {
            addCriterion("sign_address_city_id <", value, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("sign_address_city_id <=", value, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdIn(List<Integer> values) {
            addCriterion("sign_address_city_id in", values, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdNotIn(List<Integer> values) {
            addCriterion("sign_address_city_id not in", values, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdBetween(Integer value1, Integer value2) {
            addCriterion("sign_address_city_id between", value1, value2, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_address_city_id not between", value1, value2, "signAddressCityId");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameIsNull() {
            addCriterion("sign_address_city_name is null");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameIsNotNull() {
            addCriterion("sign_address_city_name is not null");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameEqualTo(String value) {
            addCriterion("sign_address_city_name =", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameNotEqualTo(String value) {
            addCriterion("sign_address_city_name <>", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameGreaterThan(String value) {
            addCriterion("sign_address_city_name >", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("sign_address_city_name >=", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameLessThan(String value) {
            addCriterion("sign_address_city_name <", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameLessThanOrEqualTo(String value) {
            addCriterion("sign_address_city_name <=", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameLike(String value) {
            addCriterion("sign_address_city_name like", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameNotLike(String value) {
            addCriterion("sign_address_city_name not like", value, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameIn(List<String> values) {
            addCriterion("sign_address_city_name in", values, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameNotIn(List<String> values) {
            addCriterion("sign_address_city_name not in", values, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameBetween(String value1, String value2) {
            addCriterion("sign_address_city_name between", value1, value2, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressCityNameNotBetween(String value1, String value2) {
            addCriterion("sign_address_city_name not between", value1, value2, "signAddressCityName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdIsNull() {
            addCriterion("sign_address_area_id is null");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdIsNotNull() {
            addCriterion("sign_address_area_id is not null");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdEqualTo(Integer value) {
            addCriterion("sign_address_area_id =", value, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdNotEqualTo(Integer value) {
            addCriterion("sign_address_area_id <>", value, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdGreaterThan(Integer value) {
            addCriterion("sign_address_area_id >", value, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_address_area_id >=", value, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdLessThan(Integer value) {
            addCriterion("sign_address_area_id <", value, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdLessThanOrEqualTo(Integer value) {
            addCriterion("sign_address_area_id <=", value, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdIn(List<Integer> values) {
            addCriterion("sign_address_area_id in", values, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdNotIn(List<Integer> values) {
            addCriterion("sign_address_area_id not in", values, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdBetween(Integer value1, Integer value2) {
            addCriterion("sign_address_area_id between", value1, value2, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_address_area_id not between", value1, value2, "signAddressAreaId");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameIsNull() {
            addCriterion("sign_address_area_name is null");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameIsNotNull() {
            addCriterion("sign_address_area_name is not null");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameEqualTo(String value) {
            addCriterion("sign_address_area_name =", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameNotEqualTo(String value) {
            addCriterion("sign_address_area_name <>", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameGreaterThan(String value) {
            addCriterion("sign_address_area_name >", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("sign_address_area_name >=", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameLessThan(String value) {
            addCriterion("sign_address_area_name <", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameLessThanOrEqualTo(String value) {
            addCriterion("sign_address_area_name <=", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameLike(String value) {
            addCriterion("sign_address_area_name like", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameNotLike(String value) {
            addCriterion("sign_address_area_name not like", value, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameIn(List<String> values) {
            addCriterion("sign_address_area_name in", values, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameNotIn(List<String> values) {
            addCriterion("sign_address_area_name not in", values, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameBetween(String value1, String value2) {
            addCriterion("sign_address_area_name between", value1, value2, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andSignAddressAreaNameNotBetween(String value1, String value2) {
            addCriterion("sign_address_area_name not between", value1, value2, "signAddressAreaName");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNull() {
            addCriterion("confirm_time is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNotNull() {
            addCriterion("confirm_time is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeEqualTo(LocalDateTime value) {
            addCriterion("confirm_time =", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotEqualTo(LocalDateTime value) {
            addCriterion("confirm_time <>", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThan(LocalDateTime value) {
            addCriterion("confirm_time >", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("confirm_time >=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThan(LocalDateTime value) {
            addCriterion("confirm_time <", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("confirm_time <=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIn(List<LocalDateTime> values) {
            addCriterion("confirm_time in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotIn(List<LocalDateTime> values) {
            addCriterion("confirm_time not in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("confirm_time between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("confirm_time not between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andServerTitleIsNull() {
            addCriterion("server_title is null");
            return (Criteria) this;
        }

        public Criteria andServerTitleIsNotNull() {
            addCriterion("server_title is not null");
            return (Criteria) this;
        }

        public Criteria andServerTitleEqualTo(String value) {
            addCriterion("server_title =", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleNotEqualTo(String value) {
            addCriterion("server_title <>", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleGreaterThan(String value) {
            addCriterion("server_title >", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleGreaterThanOrEqualTo(String value) {
            addCriterion("server_title >=", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleLessThan(String value) {
            addCriterion("server_title <", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleLessThanOrEqualTo(String value) {
            addCriterion("server_title <=", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleLike(String value) {
            addCriterion("server_title like", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleNotLike(String value) {
            addCriterion("server_title not like", value, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleIn(List<String> values) {
            addCriterion("server_title in", values, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleNotIn(List<String> values) {
            addCriterion("server_title not in", values, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleBetween(String value1, String value2) {
            addCriterion("server_title between", value1, value2, "serverTitle");
            return (Criteria) this;
        }

        public Criteria andServerTitleNotBetween(String value1, String value2) {
            addCriterion("server_title not between", value1, value2, "serverTitle");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}