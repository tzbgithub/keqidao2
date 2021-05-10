package com.qidao.application.entity.organization;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrganizationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public OrganizationExample() {
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

    public OrganizationExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public OrganizationExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public OrganizationExample page(Integer page, Integer pageSize) {
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

        public Criteria andResponsibleMemberIdIsNull() {
            addCriterion("responsible_member_id is null");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdIsNotNull() {
            addCriterion("responsible_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdEqualTo(Long value) {
            addCriterion("responsible_member_id =", value, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdNotEqualTo(Long value) {
            addCriterion("responsible_member_id <>", value, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdGreaterThan(Long value) {
            addCriterion("responsible_member_id >", value, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("responsible_member_id >=", value, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdLessThan(Long value) {
            addCriterion("responsible_member_id <", value, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("responsible_member_id <=", value, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdIn(List<Long> values) {
            addCriterion("responsible_member_id in", values, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdNotIn(List<Long> values) {
            addCriterion("responsible_member_id not in", values, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdBetween(Long value1, Long value2) {
            addCriterion("responsible_member_id between", value1, value2, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andResponsibleMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("responsible_member_id not between", value1, value2, "responsibleMemberId");
            return (Criteria) this;
        }

        public Criteria andBackendImageIsNull() {
            addCriterion("backend_image is null");
            return (Criteria) this;
        }

        public Criteria andBackendImageIsNotNull() {
            addCriterion("backend_image is not null");
            return (Criteria) this;
        }

        public Criteria andBackendImageEqualTo(String value) {
            addCriterion("backend_image =", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageNotEqualTo(String value) {
            addCriterion("backend_image <>", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageGreaterThan(String value) {
            addCriterion("backend_image >", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageGreaterThanOrEqualTo(String value) {
            addCriterion("backend_image >=", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageLessThan(String value) {
            addCriterion("backend_image <", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageLessThanOrEqualTo(String value) {
            addCriterion("backend_image <=", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageLike(String value) {
            addCriterion("backend_image like", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageNotLike(String value) {
            addCriterion("backend_image not like", value, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageIn(List<String> values) {
            addCriterion("backend_image in", values, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageNotIn(List<String> values) {
            addCriterion("backend_image not in", values, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageBetween(String value1, String value2) {
            addCriterion("backend_image between", value1, value2, "backendImage");
            return (Criteria) this;
        }

        public Criteria andBackendImageNotBetween(String value1, String value2) {
            addCriterion("backend_image not between", value1, value2, "backendImage");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
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

        public Criteria andIndustryRemarkIsNull() {
            addCriterion("industry_remark is null");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkIsNotNull() {
            addCriterion("industry_remark is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkEqualTo(String value) {
            addCriterion("industry_remark =", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkNotEqualTo(String value) {
            addCriterion("industry_remark <>", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkGreaterThan(String value) {
            addCriterion("industry_remark >", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("industry_remark >=", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkLessThan(String value) {
            addCriterion("industry_remark <", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkLessThanOrEqualTo(String value) {
            addCriterion("industry_remark <=", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkLike(String value) {
            addCriterion("industry_remark like", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkNotLike(String value) {
            addCriterion("industry_remark not like", value, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkIn(List<String> values) {
            addCriterion("industry_remark in", values, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkNotIn(List<String> values) {
            addCriterion("industry_remark not in", values, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkBetween(String value1, String value2) {
            addCriterion("industry_remark between", value1, value2, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andIndustryRemarkNotBetween(String value1, String value2) {
            addCriterion("industry_remark not between", value1, value2, "industryRemark");
            return (Criteria) this;
        }

        public Criteria andFundsIdIsNull() {
            addCriterion("funds_id is null");
            return (Criteria) this;
        }

        public Criteria andFundsIdIsNotNull() {
            addCriterion("funds_id is not null");
            return (Criteria) this;
        }

        public Criteria andFundsIdEqualTo(String value) {
            addCriterion("funds_id =", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdNotEqualTo(String value) {
            addCriterion("funds_id <>", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdGreaterThan(String value) {
            addCriterion("funds_id >", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdGreaterThanOrEqualTo(String value) {
            addCriterion("funds_id >=", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdLessThan(String value) {
            addCriterion("funds_id <", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdLessThanOrEqualTo(String value) {
            addCriterion("funds_id <=", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdLike(String value) {
            addCriterion("funds_id like", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdNotLike(String value) {
            addCriterion("funds_id not like", value, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdIn(List<String> values) {
            addCriterion("funds_id in", values, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdNotIn(List<String> values) {
            addCriterion("funds_id not in", values, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdBetween(String value1, String value2) {
            addCriterion("funds_id between", value1, value2, "fundsId");
            return (Criteria) this;
        }

        public Criteria andFundsIdNotBetween(String value1, String value2) {
            addCriterion("funds_id not between", value1, value2, "fundsId");
            return (Criteria) this;
        }

        public Criteria andScaleIdIsNull() {
            addCriterion("scale_id is null");
            return (Criteria) this;
        }

        public Criteria andScaleIdIsNotNull() {
            addCriterion("scale_id is not null");
            return (Criteria) this;
        }

        public Criteria andScaleIdEqualTo(Long value) {
            addCriterion("scale_id =", value, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdNotEqualTo(Long value) {
            addCriterion("scale_id <>", value, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdGreaterThan(Long value) {
            addCriterion("scale_id >", value, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("scale_id >=", value, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdLessThan(Long value) {
            addCriterion("scale_id <", value, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdLessThanOrEqualTo(Long value) {
            addCriterion("scale_id <=", value, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdIn(List<Long> values) {
            addCriterion("scale_id in", values, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdNotIn(List<Long> values) {
            addCriterion("scale_id not in", values, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdBetween(Long value1, Long value2) {
            addCriterion("scale_id between", value1, value2, "scaleId");
            return (Criteria) this;
        }

        public Criteria andScaleIdNotBetween(Long value1, Long value2) {
            addCriterion("scale_id not between", value1, value2, "scaleId");
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

        public Criteria andLicenseIsNull() {
            addCriterion("license is null");
            return (Criteria) this;
        }

        public Criteria andLicenseIsNotNull() {
            addCriterion("license is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseEqualTo(String value) {
            addCriterion("license =", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotEqualTo(String value) {
            addCriterion("license <>", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseGreaterThan(String value) {
            addCriterion("license >", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseGreaterThanOrEqualTo(String value) {
            addCriterion("license >=", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseLessThan(String value) {
            addCriterion("license <", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseLessThanOrEqualTo(String value) {
            addCriterion("license <=", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseLike(String value) {
            addCriterion("license like", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotLike(String value) {
            addCriterion("license not like", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseIn(List<String> values) {
            addCriterion("license in", values, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotIn(List<String> values) {
            addCriterion("license not in", values, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseBetween(String value1, String value2) {
            addCriterion("license between", value1, value2, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotBetween(String value1, String value2) {
            addCriterion("license not between", value1, value2, "license");
            return (Criteria) this;
        }

        public Criteria andQualificationsIsNull() {
            addCriterion("qualifications is null");
            return (Criteria) this;
        }

        public Criteria andQualificationsIsNotNull() {
            addCriterion("qualifications is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationsEqualTo(String value) {
            addCriterion("qualifications =", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotEqualTo(String value) {
            addCriterion("qualifications <>", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsGreaterThan(String value) {
            addCriterion("qualifications >", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsGreaterThanOrEqualTo(String value) {
            addCriterion("qualifications >=", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLessThan(String value) {
            addCriterion("qualifications <", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLessThanOrEqualTo(String value) {
            addCriterion("qualifications <=", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLike(String value) {
            addCriterion("qualifications like", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotLike(String value) {
            addCriterion("qualifications not like", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsIn(List<String> values) {
            addCriterion("qualifications in", values, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotIn(List<String> values) {
            addCriterion("qualifications not in", values, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsBetween(String value1, String value2) {
            addCriterion("qualifications between", value1, value2, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotBetween(String value1, String value2) {
            addCriterion("qualifications not between", value1, value2, "qualifications");
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

        public Criteria andAddressProvinceIdEqualTo(String value) {
            addCriterion("address_province_id =", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdNotEqualTo(String value) {
            addCriterion("address_province_id <>", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdGreaterThan(String value) {
            addCriterion("address_province_id >", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdGreaterThanOrEqualTo(String value) {
            addCriterion("address_province_id >=", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdLessThan(String value) {
            addCriterion("address_province_id <", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdLessThanOrEqualTo(String value) {
            addCriterion("address_province_id <=", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdLike(String value) {
            addCriterion("address_province_id like", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdNotLike(String value) {
            addCriterion("address_province_id not like", value, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdIn(List<String> values) {
            addCriterion("address_province_id in", values, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdNotIn(List<String> values) {
            addCriterion("address_province_id not in", values, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdBetween(String value1, String value2) {
            addCriterion("address_province_id between", value1, value2, "addressProvinceId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceIdNotBetween(String value1, String value2) {
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

        public Criteria andAddressCityIdEqualTo(String value) {
            addCriterion("address_city_id =", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdNotEqualTo(String value) {
            addCriterion("address_city_id <>", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdGreaterThan(String value) {
            addCriterion("address_city_id >", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdGreaterThanOrEqualTo(String value) {
            addCriterion("address_city_id >=", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdLessThan(String value) {
            addCriterion("address_city_id <", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdLessThanOrEqualTo(String value) {
            addCriterion("address_city_id <=", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdLike(String value) {
            addCriterion("address_city_id like", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdNotLike(String value) {
            addCriterion("address_city_id not like", value, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdIn(List<String> values) {
            addCriterion("address_city_id in", values, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdNotIn(List<String> values) {
            addCriterion("address_city_id not in", values, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdBetween(String value1, String value2) {
            addCriterion("address_city_id between", value1, value2, "addressCityId");
            return (Criteria) this;
        }

        public Criteria andAddressCityIdNotBetween(String value1, String value2) {
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

        public Criteria andAddressAreaIdEqualTo(String value) {
            addCriterion("address_area_id =", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdNotEqualTo(String value) {
            addCriterion("address_area_id <>", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdGreaterThan(String value) {
            addCriterion("address_area_id >", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdGreaterThanOrEqualTo(String value) {
            addCriterion("address_area_id >=", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdLessThan(String value) {
            addCriterion("address_area_id <", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdLessThanOrEqualTo(String value) {
            addCriterion("address_area_id <=", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdLike(String value) {
            addCriterion("address_area_id like", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdNotLike(String value) {
            addCriterion("address_area_id not like", value, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdIn(List<String> values) {
            addCriterion("address_area_id in", values, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdNotIn(List<String> values) {
            addCriterion("address_area_id not in", values, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdBetween(String value1, String value2) {
            addCriterion("address_area_id between", value1, value2, "addressAreaId");
            return (Criteria) this;
        }

        public Criteria andAddressAreaIdNotBetween(String value1, String value2) {
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

        public Criteria andAddressDetailIsNull() {
            addCriterion("address_detail is null");
            return (Criteria) this;
        }

        public Criteria andAddressDetailIsNotNull() {
            addCriterion("address_detail is not null");
            return (Criteria) this;
        }

        public Criteria andAddressDetailEqualTo(String value) {
            addCriterion("address_detail =", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotEqualTo(String value) {
            addCriterion("address_detail <>", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailGreaterThan(String value) {
            addCriterion("address_detail >", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailGreaterThanOrEqualTo(String value) {
            addCriterion("address_detail >=", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailLessThan(String value) {
            addCriterion("address_detail <", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailLessThanOrEqualTo(String value) {
            addCriterion("address_detail <=", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailLike(String value) {
            addCriterion("address_detail like", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotLike(String value) {
            addCriterion("address_detail not like", value, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailIn(List<String> values) {
            addCriterion("address_detail in", values, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotIn(List<String> values) {
            addCriterion("address_detail not in", values, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailBetween(String value1, String value2) {
            addCriterion("address_detail between", value1, value2, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andAddressDetailNotBetween(String value1, String value2) {
            addCriterion("address_detail not between", value1, value2, "addressDetail");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdIsNull() {
            addCriterion("tech_scale_id is null");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdIsNotNull() {
            addCriterion("tech_scale_id is not null");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdEqualTo(Long value) {
            addCriterion("tech_scale_id =", value, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdNotEqualTo(Long value) {
            addCriterion("tech_scale_id <>", value, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdGreaterThan(Long value) {
            addCriterion("tech_scale_id >", value, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tech_scale_id >=", value, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdLessThan(Long value) {
            addCriterion("tech_scale_id <", value, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdLessThanOrEqualTo(Long value) {
            addCriterion("tech_scale_id <=", value, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdIn(List<Long> values) {
            addCriterion("tech_scale_id in", values, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdNotIn(List<Long> values) {
            addCriterion("tech_scale_id not in", values, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdBetween(Long value1, Long value2) {
            addCriterion("tech_scale_id between", value1, value2, "techScaleId");
            return (Criteria) this;
        }

        public Criteria andTechScaleIdNotBetween(Long value1, Long value2) {
            addCriterion("tech_scale_id not between", value1, value2, "techScaleId");
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

        public Criteria andVipStartTimeIsNull() {
            addCriterion("vip_start_time is null");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeIsNotNull() {
            addCriterion("vip_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeEqualTo(LocalDateTime value) {
            addCriterion("vip_start_time =", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeNotEqualTo(LocalDateTime value) {
            addCriterion("vip_start_time <>", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeGreaterThan(LocalDateTime value) {
            addCriterion("vip_start_time >", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("vip_start_time >=", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeLessThan(LocalDateTime value) {
            addCriterion("vip_start_time <", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("vip_start_time <=", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeIn(List<LocalDateTime> values) {
            addCriterion("vip_start_time in", values, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeNotIn(List<LocalDateTime> values) {
            addCriterion("vip_start_time not in", values, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("vip_start_time between", value1, value2, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("vip_start_time not between", value1, value2, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeIsNull() {
            addCriterion("vip_end_time is null");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeIsNotNull() {
            addCriterion("vip_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeEqualTo(LocalDateTime value) {
            addCriterion("vip_end_time =", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeNotEqualTo(LocalDateTime value) {
            addCriterion("vip_end_time <>", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeGreaterThan(LocalDateTime value) {
            addCriterion("vip_end_time >", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("vip_end_time >=", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeLessThan(LocalDateTime value) {
            addCriterion("vip_end_time <", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("vip_end_time <=", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeIn(List<LocalDateTime> values) {
            addCriterion("vip_end_time in", values, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeNotIn(List<LocalDateTime> values) {
            addCriterion("vip_end_time not in", values, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("vip_end_time between", value1, value2, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("vip_end_time not between", value1, value2, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNull() {
            addCriterion("verify_status is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNotNull() {
            addCriterion("verify_status is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusEqualTo(Integer value) {
            addCriterion("verify_status =", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotEqualTo(Integer value) {
            addCriterion("verify_status <>", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThan(Integer value) {
            addCriterion("verify_status >", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_status >=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThan(Integer value) {
            addCriterion("verify_status <", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("verify_status <=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIn(List<Integer> values) {
            addCriterion("verify_status in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotIn(List<Integer> values) {
            addCriterion("verify_status not in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusBetween(Integer value1, Integer value2) {
            addCriterion("verify_status between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_status not between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonIsNull() {
            addCriterion("verify_reason is null");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonIsNotNull() {
            addCriterion("verify_reason is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonEqualTo(String value) {
            addCriterion("verify_reason =", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonNotEqualTo(String value) {
            addCriterion("verify_reason <>", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonGreaterThan(String value) {
            addCriterion("verify_reason >", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonGreaterThanOrEqualTo(String value) {
            addCriterion("verify_reason >=", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonLessThan(String value) {
            addCriterion("verify_reason <", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonLessThanOrEqualTo(String value) {
            addCriterion("verify_reason <=", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonLike(String value) {
            addCriterion("verify_reason like", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonNotLike(String value) {
            addCriterion("verify_reason not like", value, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonIn(List<String> values) {
            addCriterion("verify_reason in", values, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonNotIn(List<String> values) {
            addCriterion("verify_reason not in", values, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonBetween(String value1, String value2) {
            addCriterion("verify_reason between", value1, value2, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andVerifyReasonNotBetween(String value1, String value2) {
            addCriterion("verify_reason not between", value1, value2, "verifyReason");
            return (Criteria) this;
        }

        public Criteria andBelongIsNull() {
            addCriterion("belong is null");
            return (Criteria) this;
        }

        public Criteria andBelongIsNotNull() {
            addCriterion("belong is not null");
            return (Criteria) this;
        }

        public Criteria andBelongEqualTo(String value) {
            addCriterion("belong =", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongNotEqualTo(String value) {
            addCriterion("belong <>", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongGreaterThan(String value) {
            addCriterion("belong >", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongGreaterThanOrEqualTo(String value) {
            addCriterion("belong >=", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongLessThan(String value) {
            addCriterion("belong <", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongLessThanOrEqualTo(String value) {
            addCriterion("belong <=", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongLike(String value) {
            addCriterion("belong like", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongNotLike(String value) {
            addCriterion("belong not like", value, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongIn(List<String> values) {
            addCriterion("belong in", values, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongNotIn(List<String> values) {
            addCriterion("belong not in", values, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongBetween(String value1, String value2) {
            addCriterion("belong between", value1, value2, "belong");
            return (Criteria) this;
        }

        public Criteria andBelongNotBetween(String value1, String value2) {
            addCriterion("belong not between", value1, value2, "belong");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNull() {
            addCriterion("salesman_id is null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNotNull() {
            addCriterion("salesman_id is not null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdEqualTo(Long value) {
            addCriterion("salesman_id =", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotEqualTo(Long value) {
            addCriterion("salesman_id <>", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThan(Long value) {
            addCriterion("salesman_id >", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThanOrEqualTo(Long value) {
            addCriterion("salesman_id >=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThan(Long value) {
            addCriterion("salesman_id <", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThanOrEqualTo(Long value) {
            addCriterion("salesman_id <=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIn(List<Long> values) {
            addCriterion("salesman_id in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotIn(List<Long> values) {
            addCriterion("salesman_id not in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdBetween(Long value1, Long value2) {
            addCriterion("salesman_id between", value1, value2, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotBetween(Long value1, Long value2) {
            addCriterion("salesman_id not between", value1, value2, "salesmanId");
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