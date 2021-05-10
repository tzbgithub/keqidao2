package com.qidao.application.entity.server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public ServerExample() {
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

    public ServerExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public ServerExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public ServerExample page(Integer page, Integer pageSize) {
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

        public Criteria andOrganizedIdPartyAIsNull() {
            addCriterion("organized_id_party_a is null");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyAIsNotNull() {
            addCriterion("organized_id_party_a is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyAEqualTo(Long value) {
            addCriterion("organized_id_party_a =", value, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyANotEqualTo(Long value) {
            addCriterion("organized_id_party_a <>", value, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyAGreaterThan(Long value) {
            addCriterion("organized_id_party_a >", value, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyAGreaterThanOrEqualTo(Long value) {
            addCriterion("organized_id_party_a >=", value, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyALessThan(Long value) {
            addCriterion("organized_id_party_a <", value, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyALessThanOrEqualTo(Long value) {
            addCriterion("organized_id_party_a <=", value, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyAIn(List<Long> values) {
            addCriterion("organized_id_party_a in", values, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyANotIn(List<Long> values) {
            addCriterion("organized_id_party_a not in", values, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyABetween(Long value1, Long value2) {
            addCriterion("organized_id_party_a between", value1, value2, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyANotBetween(Long value1, Long value2) {
            addCriterion("organized_id_party_a not between", value1, value2, "organizedIdPartyA");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBIsNull() {
            addCriterion("organized_id_party_b is null");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBIsNotNull() {
            addCriterion("organized_id_party_b is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBEqualTo(Long value) {
            addCriterion("organized_id_party_b =", value, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBNotEqualTo(Long value) {
            addCriterion("organized_id_party_b <>", value, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBGreaterThan(Long value) {
            addCriterion("organized_id_party_b >", value, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBGreaterThanOrEqualTo(Long value) {
            addCriterion("organized_id_party_b >=", value, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBLessThan(Long value) {
            addCriterion("organized_id_party_b <", value, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBLessThanOrEqualTo(Long value) {
            addCriterion("organized_id_party_b <=", value, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBIn(List<Long> values) {
            addCriterion("organized_id_party_b in", values, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBNotIn(List<Long> values) {
            addCriterion("organized_id_party_b not in", values, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBBetween(Long value1, Long value2) {
            addCriterion("organized_id_party_b between", value1, value2, "organizedIdPartyB");
            return (Criteria) this;
        }

        public Criteria andOrganizedIdPartyBNotBetween(Long value1, Long value2) {
            addCriterion("organized_id_party_b not between", value1, value2, "organizedIdPartyB");
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

        public Criteria andMemberIdPartyBIsNull() {
            addCriterion("member_id_party_b is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBIsNotNull() {
            addCriterion("member_id_party_b is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBEqualTo(Long value) {
            addCriterion("member_id_party_b =", value, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBNotEqualTo(Long value) {
            addCriterion("member_id_party_b <>", value, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBGreaterThan(Long value) {
            addCriterion("member_id_party_b >", value, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBGreaterThanOrEqualTo(Long value) {
            addCriterion("member_id_party_b >=", value, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBLessThan(Long value) {
            addCriterion("member_id_party_b <", value, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBLessThanOrEqualTo(Long value) {
            addCriterion("member_id_party_b <=", value, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBIn(List<Long> values) {
            addCriterion("member_id_party_b in", values, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBNotIn(List<Long> values) {
            addCriterion("member_id_party_b not in", values, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBBetween(Long value1, Long value2) {
            addCriterion("member_id_party_b between", value1, value2, "memberIdPartyB");
            return (Criteria) this;
        }

        public Criteria andMemberIdPartyBNotBetween(Long value1, Long value2) {
            addCriterion("member_id_party_b not between", value1, value2, "memberIdPartyB");
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdIsNull() {
            addCriterion("address_province_id is null");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdIsNotNull() {
            addCriterion("address_province_id is not null");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdEqualTo(Integer value) {
            addCriterion("address_province_id =", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdNotEqualTo(Integer value) {
            addCriterion("address_province_id <>", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdGreaterThan(Integer value) {
            addCriterion("address_province_id >", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("address_province_id >=", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdLessThan(Integer value) {
            addCriterion("address_province_id <", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdLessThanOrEqualTo(Integer value) {
            addCriterion("address_province_id <=", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdIn(List<Integer> values) {
            addCriterion("address_province_id in", values, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdNotIn(List<Integer> values) {
            addCriterion("address_province_id not in", values, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdBetween(Integer value1, Integer value2) {
            addCriterion("address_province_id between", value1, value2, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("address_province_id not between", value1, value2, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameIsNull() {
            addCriterion("address_province_name is null");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameIsNotNull() {
            addCriterion("address_province_name is not null");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameEqualTo(String value) {
            addCriterion("address_province_name =", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameNotEqualTo(String value) {
            addCriterion("address_province_name <>", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameGreaterThan(String value) {
            addCriterion("address_province_name >", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameGreaterThanOrEqualTo(String value) {
            addCriterion("address_province_name >=", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameLessThan(String value) {
            addCriterion("address_province_name <", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameLessThanOrEqualTo(String value) {
            addCriterion("address_province_name <=", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameLike(String value) {
            addCriterion("address_province_name like", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameNotLike(String value) {
            addCriterion("address_province_name not like", value, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameIn(List<String> values) {
            addCriterion("address_province_name in", values, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameNotIn(List<String> values) {
            addCriterion("address_province_name not in", values, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameBetween(String value1, String value2) {
            addCriterion("address_province_name between", value1, value2, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceNameNotBetween(String value1, String value2) {
            addCriterion("address_province_name not between", value1, value2, "addressProvinceName");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdIsNull() {
            addCriterion("address_city_id is null");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdIsNotNull() {
            addCriterion("address_city_id is not null");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdEqualTo(Integer value) {
            addCriterion("address_city_id =", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdNotEqualTo(Integer value) {
            addCriterion("address_city_id <>", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdGreaterThan(Integer value) {
            addCriterion("address_city_id >", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("address_city_id >=", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdLessThan(Integer value) {
            addCriterion("address_city_id <", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("address_city_id <=", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdIn(List<Integer> values) {
            addCriterion("address_city_id in", values, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdNotIn(List<Integer> values) {
            addCriterion("address_city_id not in", values, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdBetween(Integer value1, Integer value2) {
            addCriterion("address_city_id between", value1, value2, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("address_city_id not between", value1, value2, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameIsNull() {
            addCriterion("address_city_name is null");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameIsNotNull() {
            addCriterion("address_city_name is not null");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameEqualTo(String value) {
            addCriterion("address_city_name =", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameNotEqualTo(String value) {
            addCriterion("address_city_name <>", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameGreaterThan(String value) {
            addCriterion("address_city_name >", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("address_city_name >=", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameLessThan(String value) {
            addCriterion("address_city_name <", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameLessThanOrEqualTo(String value) {
            addCriterion("address_city_name <=", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameLike(String value) {
            addCriterion("address_city_name like", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameNotLike(String value) {
            addCriterion("address_city_name not like", value, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameIn(List<String> values) {
            addCriterion("address_city_name in", values, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameNotIn(List<String> values) {
            addCriterion("address_city_name not in", values, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameBetween(String value1, String value2) {
            addCriterion("address_city_name between", value1, value2, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressCityNameNotBetween(String value1, String value2) {
            addCriterion("address_city_name not between", value1, value2, "addressCityName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdIsNull() {
            addCriterion("address_area_id is null");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdIsNotNull() {
            addCriterion("address_area_id is not null");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdEqualTo(Integer value) {
            addCriterion("address_area_id =", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdNotEqualTo(Integer value) {
            addCriterion("address_area_id <>", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdGreaterThan(Integer value) {
            addCriterion("address_area_id >", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("address_area_id >=", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdLessThan(Integer value) {
            addCriterion("address_area_id <", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdLessThanOrEqualTo(Integer value) {
            addCriterion("address_area_id <=", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdIn(List<Integer> values) {
            addCriterion("address_area_id in", values, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdNotIn(List<Integer> values) {
            addCriterion("address_area_id not in", values, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdBetween(Integer value1, Integer value2) {
            addCriterion("address_area_id between", value1, value2, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("address_area_id not between", value1, value2, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameIsNull() {
            addCriterion("address_area_name is null");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameIsNotNull() {
            addCriterion("address_area_name is not null");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameEqualTo(String value) {
            addCriterion("address_area_name =", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameNotEqualTo(String value) {
            addCriterion("address_area_name <>", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameGreaterThan(String value) {
            addCriterion("address_area_name >", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("address_area_name >=", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameLessThan(String value) {
            addCriterion("address_area_name <", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameLessThanOrEqualTo(String value) {
            addCriterion("address_area_name <=", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameLike(String value) {
            addCriterion("address_area_name like", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameNotLike(String value) {
            addCriterion("address_area_name not like", value, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameIn(List<String> values) {
            addCriterion("address_area_name in", values, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameNotIn(List<String> values) {
            addCriterion("address_area_name not in", values, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameBetween(String value1, String value2) {
            addCriterion("address_area_name between", value1, value2, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andAddressAreaNameNotBetween(String value1, String value2) {
            addCriterion("address_area_name not between", value1, value2, "addressAreaName");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdIsNull() {
            addCriterion("spec_area_id is null");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdIsNotNull() {
            addCriterion("spec_area_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdEqualTo(Long value) {
            addCriterion("spec_area_id =", value, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdNotEqualTo(Long value) {
            addCriterion("spec_area_id <>", value, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdGreaterThan(Long value) {
            addCriterion("spec_area_id >", value, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdGreaterThanOrEqualTo(Long value) {
            addCriterion("spec_area_id >=", value, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdLessThan(Long value) {
            addCriterion("spec_area_id <", value, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdLessThanOrEqualTo(Long value) {
            addCriterion("spec_area_id <=", value, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdIn(List<Long> values) {
            addCriterion("spec_area_id in", values, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdNotIn(List<Long> values) {
            addCriterion("spec_area_id not in", values, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdBetween(Long value1, Long value2) {
            addCriterion("spec_area_id between", value1, value2, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAreaIdNotBetween(Long value1, Long value2) {
            addCriterion("spec_area_id not between", value1, value2, "specAreaId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdIsNull() {
            addCriterion("spec_amount_id is null");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdIsNotNull() {
            addCriterion("spec_amount_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdEqualTo(Long value) {
            addCriterion("spec_amount_id =", value, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdNotEqualTo(Long value) {
            addCriterion("spec_amount_id <>", value, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdGreaterThan(Long value) {
            addCriterion("spec_amount_id >", value, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdGreaterThanOrEqualTo(Long value) {
            addCriterion("spec_amount_id >=", value, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdLessThan(Long value) {
            addCriterion("spec_amount_id <", value, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdLessThanOrEqualTo(Long value) {
            addCriterion("spec_amount_id <=", value, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdIn(List<Long> values) {
            addCriterion("spec_amount_id in", values, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdNotIn(List<Long> values) {
            addCriterion("spec_amount_id not in", values, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdBetween(Long value1, Long value2) {
            addCriterion("spec_amount_id between", value1, value2, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andSpecAmountIdNotBetween(Long value1, Long value2) {
            addCriterion("spec_amount_id not between", value1, value2, "specAmountId");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andThumbIsNull() {
            addCriterion("thumb is null");
            return (Criteria) this;
        }

        public Criteria andThumbIsNotNull() {
            addCriterion("thumb is not null");
            return (Criteria) this;
        }

        public Criteria andThumbEqualTo(String value) {
            addCriterion("thumb =", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbNotEqualTo(String value) {
            addCriterion("thumb <>", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbGreaterThan(String value) {
            addCriterion("thumb >", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbGreaterThanOrEqualTo(String value) {
            addCriterion("thumb >=", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbLessThan(String value) {
            addCriterion("thumb <", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbLessThanOrEqualTo(String value) {
            addCriterion("thumb <=", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbLike(String value) {
            addCriterion("thumb like", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbNotLike(String value) {
            addCriterion("thumb not like", value, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbIn(List<String> values) {
            addCriterion("thumb in", values, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbNotIn(List<String> values) {
            addCriterion("thumb not in", values, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbBetween(String value1, String value2) {
            addCriterion("thumb between", value1, value2, "thumb");
            return (Criteria) this;
        }

        public Criteria andThumbNotBetween(String value1, String value2) {
            addCriterion("thumb not between", value1, value2, "thumb");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeIsNull() {
            addCriterion("solution_time is null");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeIsNotNull() {
            addCriterion("solution_time is not null");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeEqualTo(LocalDateTime value) {
            addCriterion("solution_time =", value, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeNotEqualTo(LocalDateTime value) {
            addCriterion("solution_time <>", value, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeGreaterThan(LocalDateTime value) {
            addCriterion("solution_time >", value, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("solution_time >=", value, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeLessThan(LocalDateTime value) {
            addCriterion("solution_time <", value, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("solution_time <=", value, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeIn(List<LocalDateTime> values) {
            addCriterion("solution_time in", values, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeNotIn(List<LocalDateTime> values) {
            addCriterion("solution_time not in", values, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("solution_time between", value1, value2, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andSolutionTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("solution_time not between", value1, value2, "solutionTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIsNull() {
            addCriterion("industry_id is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIsNotNull() {
            addCriterion("industry_id is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryIdEqualTo(Long value) {
            addCriterion("industry_id =", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotEqualTo(Long value) {
            addCriterion("industry_id <>", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdGreaterThan(Long value) {
            addCriterion("industry_id >", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("industry_id >=", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdLessThan(Long value) {
            addCriterion("industry_id <", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdLessThanOrEqualTo(Long value) {
            addCriterion("industry_id <=", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIn(List<Long> values) {
            addCriterion("industry_id in", values, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotIn(List<Long> values) {
            addCriterion("industry_id not in", values, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdBetween(Long value1, Long value2) {
            addCriterion("industry_id between", value1, value2, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotBetween(Long value1, Long value2) {
            addCriterion("industry_id not between", value1, value2, "industryId");
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