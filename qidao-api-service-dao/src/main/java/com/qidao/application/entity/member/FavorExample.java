package com.qidao.application.entity.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FavorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public FavorExample() {
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

    public FavorExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public FavorExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public FavorExample page(Integer page, Integer pageSize) {
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

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Long value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Long value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Long value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Long value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Long> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Long> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Long value1, Long value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdIsNull() {
            addCriterion("dynamic_push_member_id is null");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdIsNotNull() {
            addCriterion("dynamic_push_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdEqualTo(Long value) {
            addCriterion("dynamic_push_member_id =", value, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdNotEqualTo(Long value) {
            addCriterion("dynamic_push_member_id <>", value, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdGreaterThan(Long value) {
            addCriterion("dynamic_push_member_id >", value, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dynamic_push_member_id >=", value, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdLessThan(Long value) {
            addCriterion("dynamic_push_member_id <", value, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("dynamic_push_member_id <=", value, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdIn(List<Long> values) {
            addCriterion("dynamic_push_member_id in", values, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdNotIn(List<Long> values) {
            addCriterion("dynamic_push_member_id not in", values, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdBetween(Long value1, Long value2) {
            addCriterion("dynamic_push_member_id between", value1, value2, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicPushMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("dynamic_push_member_id not between", value1, value2, "dynamicPushMemberId");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleIsNull() {
            addCriterion("dynamic_title is null");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleIsNotNull() {
            addCriterion("dynamic_title is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleEqualTo(String value) {
            addCriterion("dynamic_title =", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleNotEqualTo(String value) {
            addCriterion("dynamic_title <>", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleGreaterThan(String value) {
            addCriterion("dynamic_title >", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleGreaterThanOrEqualTo(String value) {
            addCriterion("dynamic_title >=", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleLessThan(String value) {
            addCriterion("dynamic_title <", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleLessThanOrEqualTo(String value) {
            addCriterion("dynamic_title <=", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleLike(String value) {
            addCriterion("dynamic_title like", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleNotLike(String value) {
            addCriterion("dynamic_title not like", value, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleIn(List<String> values) {
            addCriterion("dynamic_title in", values, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleNotIn(List<String> values) {
            addCriterion("dynamic_title not in", values, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleBetween(String value1, String value2) {
            addCriterion("dynamic_title between", value1, value2, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicTitleNotBetween(String value1, String value2) {
            addCriterion("dynamic_title not between", value1, value2, "dynamicTitle");
            return (Criteria) this;
        }

        public Criteria andDynamicImgIsNull() {
            addCriterion("dynamic_img is null");
            return (Criteria) this;
        }

        public Criteria andDynamicImgIsNotNull() {
            addCriterion("dynamic_img is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicImgEqualTo(String value) {
            addCriterion("dynamic_img =", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgNotEqualTo(String value) {
            addCriterion("dynamic_img <>", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgGreaterThan(String value) {
            addCriterion("dynamic_img >", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgGreaterThanOrEqualTo(String value) {
            addCriterion("dynamic_img >=", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgLessThan(String value) {
            addCriterion("dynamic_img <", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgLessThanOrEqualTo(String value) {
            addCriterion("dynamic_img <=", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgLike(String value) {
            addCriterion("dynamic_img like", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgNotLike(String value) {
            addCriterion("dynamic_img not like", value, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgIn(List<String> values) {
            addCriterion("dynamic_img in", values, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgNotIn(List<String> values) {
            addCriterion("dynamic_img not in", values, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgBetween(String value1, String value2) {
            addCriterion("dynamic_img between", value1, value2, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicImgNotBetween(String value1, String value2) {
            addCriterion("dynamic_img not between", value1, value2, "dynamicImg");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryIsNull() {
            addCriterion("dynamic_summary is null");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryIsNotNull() {
            addCriterion("dynamic_summary is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryEqualTo(String value) {
            addCriterion("dynamic_summary =", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryNotEqualTo(String value) {
            addCriterion("dynamic_summary <>", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryGreaterThan(String value) {
            addCriterion("dynamic_summary >", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("dynamic_summary >=", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryLessThan(String value) {
            addCriterion("dynamic_summary <", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryLessThanOrEqualTo(String value) {
            addCriterion("dynamic_summary <=", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryLike(String value) {
            addCriterion("dynamic_summary like", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryNotLike(String value) {
            addCriterion("dynamic_summary not like", value, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryIn(List<String> values) {
            addCriterion("dynamic_summary in", values, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryNotIn(List<String> values) {
            addCriterion("dynamic_summary not in", values, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryBetween(String value1, String value2) {
            addCriterion("dynamic_summary between", value1, value2, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicSummaryNotBetween(String value1, String value2) {
            addCriterion("dynamic_summary not between", value1, value2, "dynamicSummary");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrIsNull() {
            addCriterion("dynamic_label_str is null");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrIsNotNull() {
            addCriterion("dynamic_label_str is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrEqualTo(String value) {
            addCriterion("dynamic_label_str =", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrNotEqualTo(String value) {
            addCriterion("dynamic_label_str <>", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrGreaterThan(String value) {
            addCriterion("dynamic_label_str >", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrGreaterThanOrEqualTo(String value) {
            addCriterion("dynamic_label_str >=", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrLessThan(String value) {
            addCriterion("dynamic_label_str <", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrLessThanOrEqualTo(String value) {
            addCriterion("dynamic_label_str <=", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrLike(String value) {
            addCriterion("dynamic_label_str like", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrNotLike(String value) {
            addCriterion("dynamic_label_str not like", value, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrIn(List<String> values) {
            addCriterion("dynamic_label_str in", values, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrNotIn(List<String> values) {
            addCriterion("dynamic_label_str not in", values, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrBetween(String value1, String value2) {
            addCriterion("dynamic_label_str between", value1, value2, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLabelStrNotBetween(String value1, String value2) {
            addCriterion("dynamic_label_str not between", value1, value2, "dynamicLabelStr");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumIsNull() {
            addCriterion("dynamic_like_num is null");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumIsNotNull() {
            addCriterion("dynamic_like_num is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumEqualTo(Integer value) {
            addCriterion("dynamic_like_num =", value, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumNotEqualTo(Integer value) {
            addCriterion("dynamic_like_num <>", value, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumGreaterThan(Integer value) {
            addCriterion("dynamic_like_num >", value, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("dynamic_like_num >=", value, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumLessThan(Integer value) {
            addCriterion("dynamic_like_num <", value, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumLessThanOrEqualTo(Integer value) {
            addCriterion("dynamic_like_num <=", value, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumIn(List<Integer> values) {
            addCriterion("dynamic_like_num in", values, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumNotIn(List<Integer> values) {
            addCriterion("dynamic_like_num not in", values, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumBetween(Integer value1, Integer value2) {
            addCriterion("dynamic_like_num between", value1, value2, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicLikeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("dynamic_like_num not between", value1, value2, "dynamicLikeNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumIsNull() {
            addCriterion("dynamic_comment_num is null");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumIsNotNull() {
            addCriterion("dynamic_comment_num is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumEqualTo(Integer value) {
            addCriterion("dynamic_comment_num =", value, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumNotEqualTo(Integer value) {
            addCriterion("dynamic_comment_num <>", value, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumGreaterThan(Integer value) {
            addCriterion("dynamic_comment_num >", value, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("dynamic_comment_num >=", value, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumLessThan(Integer value) {
            addCriterion("dynamic_comment_num <", value, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumLessThanOrEqualTo(Integer value) {
            addCriterion("dynamic_comment_num <=", value, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumIn(List<Integer> values) {
            addCriterion("dynamic_comment_num in", values, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumNotIn(List<Integer> values) {
            addCriterion("dynamic_comment_num not in", values, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumBetween(Integer value1, Integer value2) {
            addCriterion("dynamic_comment_num between", value1, value2, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicCommentNumNotBetween(Integer value1, Integer value2) {
            addCriterion("dynamic_comment_num not between", value1, value2, "dynamicCommentNum");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeIsNull() {
            addCriterion("dynamic_push_time is null");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeIsNotNull() {
            addCriterion("dynamic_push_time is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeEqualTo(LocalDateTime value) {
            addCriterion("dynamic_push_time =", value, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeNotEqualTo(LocalDateTime value) {
            addCriterion("dynamic_push_time <>", value, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeGreaterThan(LocalDateTime value) {
            addCriterion("dynamic_push_time >", value, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("dynamic_push_time >=", value, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeLessThan(LocalDateTime value) {
            addCriterion("dynamic_push_time <", value, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("dynamic_push_time <=", value, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeIn(List<LocalDateTime> values) {
            addCriterion("dynamic_push_time in", values, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeNotIn(List<LocalDateTime> values) {
            addCriterion("dynamic_push_time not in", values, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("dynamic_push_time between", value1, value2, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicPushTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("dynamic_push_time not between", value1, value2, "dynamicPushTime");
            return (Criteria) this;
        }

        public Criteria andDynamicIdIsNull() {
            addCriterion("dynamic_id is null");
            return (Criteria) this;
        }

        public Criteria andDynamicIdIsNotNull() {
            addCriterion("dynamic_id is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicIdEqualTo(Long value) {
            addCriterion("dynamic_id =", value, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdNotEqualTo(Long value) {
            addCriterion("dynamic_id <>", value, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdGreaterThan(Long value) {
            addCriterion("dynamic_id >", value, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dynamic_id >=", value, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdLessThan(Long value) {
            addCriterion("dynamic_id <", value, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdLessThanOrEqualTo(Long value) {
            addCriterion("dynamic_id <=", value, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdIn(List<Long> values) {
            addCriterion("dynamic_id in", values, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdNotIn(List<Long> values) {
            addCriterion("dynamic_id not in", values, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdBetween(Long value1, Long value2) {
            addCriterion("dynamic_id between", value1, value2, "dynamicId");
            return (Criteria) this;
        }

        public Criteria andDynamicIdNotBetween(Long value1, Long value2) {
            addCriterion("dynamic_id not between", value1, value2, "dynamicId");
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