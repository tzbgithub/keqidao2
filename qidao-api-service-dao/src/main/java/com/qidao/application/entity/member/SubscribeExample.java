package com.qidao.application.entity.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SubscribeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public SubscribeExample() {
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

    public SubscribeExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public SubscribeExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public SubscribeExample page(Integer page, Integer pageSize) {
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

        public Criteria andSubscribeIdIsNull() {
            addCriterion("subscribe_id is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdIsNotNull() {
            addCriterion("subscribe_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdEqualTo(Long value) {
            addCriterion("subscribe_id =", value, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdNotEqualTo(Long value) {
            addCriterion("subscribe_id <>", value, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdGreaterThan(Long value) {
            addCriterion("subscribe_id >", value, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("subscribe_id >=", value, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdLessThan(Long value) {
            addCriterion("subscribe_id <", value, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdLessThanOrEqualTo(Long value) {
            addCriterion("subscribe_id <=", value, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdIn(List<Long> values) {
            addCriterion("subscribe_id in", values, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdNotIn(List<Long> values) {
            addCriterion("subscribe_id not in", values, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdBetween(Long value1, Long value2) {
            addCriterion("subscribe_id between", value1, value2, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeIdNotBetween(Long value1, Long value2) {
            addCriterion("subscribe_id not between", value1, value2, "subscribeId");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeIsNull() {
            addCriterion("subscribe_time is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeIsNotNull() {
            addCriterion("subscribe_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeEqualTo(LocalDateTime value) {
            addCriterion("subscribe_time =", value, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeNotEqualTo(LocalDateTime value) {
            addCriterion("subscribe_time <>", value, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeGreaterThan(LocalDateTime value) {
            addCriterion("subscribe_time >", value, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("subscribe_time >=", value, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeLessThan(LocalDateTime value) {
            addCriterion("subscribe_time <", value, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("subscribe_time <=", value, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeIn(List<LocalDateTime> values) {
            addCriterion("subscribe_time in", values, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeNotIn(List<LocalDateTime> values) {
            addCriterion("subscribe_time not in", values, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("subscribe_time between", value1, value2, "subscribeTime");
            return (Criteria) this;
        }

        public Criteria andSubscribeTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("subscribe_time not between", value1, value2, "subscribeTime");
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

        public Criteria andSubscribeHeadImgIsNull() {
            addCriterion("subscribe_head_img is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgIsNotNull() {
            addCriterion("subscribe_head_img is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgEqualTo(String value) {
            addCriterion("subscribe_head_img =", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgNotEqualTo(String value) {
            addCriterion("subscribe_head_img <>", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgGreaterThan(String value) {
            addCriterion("subscribe_head_img >", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgGreaterThanOrEqualTo(String value) {
            addCriterion("subscribe_head_img >=", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgLessThan(String value) {
            addCriterion("subscribe_head_img <", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgLessThanOrEqualTo(String value) {
            addCriterion("subscribe_head_img <=", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgLike(String value) {
            addCriterion("subscribe_head_img like", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgNotLike(String value) {
            addCriterion("subscribe_head_img not like", value, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgIn(List<String> values) {
            addCriterion("subscribe_head_img in", values, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgNotIn(List<String> values) {
            addCriterion("subscribe_head_img not in", values, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgBetween(String value1, String value2) {
            addCriterion("subscribe_head_img between", value1, value2, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeHeadImgNotBetween(String value1, String value2) {
            addCriterion("subscribe_head_img not between", value1, value2, "subscribeHeadImg");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameIsNull() {
            addCriterion("subscribe_name is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameIsNotNull() {
            addCriterion("subscribe_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameEqualTo(String value) {
            addCriterion("subscribe_name =", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameNotEqualTo(String value) {
            addCriterion("subscribe_name <>", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameGreaterThan(String value) {
            addCriterion("subscribe_name >", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameGreaterThanOrEqualTo(String value) {
            addCriterion("subscribe_name >=", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameLessThan(String value) {
            addCriterion("subscribe_name <", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameLessThanOrEqualTo(String value) {
            addCriterion("subscribe_name <=", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameLike(String value) {
            addCriterion("subscribe_name like", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameNotLike(String value) {
            addCriterion("subscribe_name not like", value, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameIn(List<String> values) {
            addCriterion("subscribe_name in", values, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameNotIn(List<String> values) {
            addCriterion("subscribe_name not in", values, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameBetween(String value1, String value2) {
            addCriterion("subscribe_name between", value1, value2, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribeNameNotBetween(String value1, String value2) {
            addCriterion("subscribe_name not between", value1, value2, "subscribeName");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionIsNull() {
            addCriterion("subscribe_position is null");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionIsNotNull() {
            addCriterion("subscribe_position is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionEqualTo(String value) {
            addCriterion("subscribe_position =", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionNotEqualTo(String value) {
            addCriterion("subscribe_position <>", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionGreaterThan(String value) {
            addCriterion("subscribe_position >", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionGreaterThanOrEqualTo(String value) {
            addCriterion("subscribe_position >=", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionLessThan(String value) {
            addCriterion("subscribe_position <", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionLessThanOrEqualTo(String value) {
            addCriterion("subscribe_position <=", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionLike(String value) {
            addCriterion("subscribe_position like", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionNotLike(String value) {
            addCriterion("subscribe_position not like", value, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionIn(List<String> values) {
            addCriterion("subscribe_position in", values, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionNotIn(List<String> values) {
            addCriterion("subscribe_position not in", values, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionBetween(String value1, String value2) {
            addCriterion("subscribe_position between", value1, value2, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribePositionNotBetween(String value1, String value2) {
            addCriterion("subscribe_position not between", value1, value2, "subscribePosition");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameIsNull() {
            addCriterion("subscribe_organization_name is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameIsNotNull() {
            addCriterion("subscribe_organization_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameEqualTo(String value) {
            addCriterion("subscribe_organization_name =", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameNotEqualTo(String value) {
            addCriterion("subscribe_organization_name <>", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameGreaterThan(String value) {
            addCriterion("subscribe_organization_name >", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameGreaterThanOrEqualTo(String value) {
            addCriterion("subscribe_organization_name >=", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameLessThan(String value) {
            addCriterion("subscribe_organization_name <", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameLessThanOrEqualTo(String value) {
            addCriterion("subscribe_organization_name <=", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameLike(String value) {
            addCriterion("subscribe_organization_name like", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameNotLike(String value) {
            addCriterion("subscribe_organization_name not like", value, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameIn(List<String> values) {
            addCriterion("subscribe_organization_name in", values, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameNotIn(List<String> values) {
            addCriterion("subscribe_organization_name not in", values, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameBetween(String value1, String value2) {
            addCriterion("subscribe_organization_name between", value1, value2, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeOrganizationNameNotBetween(String value1, String value2) {
            addCriterion("subscribe_organization_name not between", value1, value2, "subscribeOrganizationName");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeIsNull() {
            addCriterion("subscribe_type is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeIsNotNull() {
            addCriterion("subscribe_type is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeEqualTo(Integer value) {
            addCriterion("subscribe_type =", value, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeNotEqualTo(Integer value) {
            addCriterion("subscribe_type <>", value, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeGreaterThan(Integer value) {
            addCriterion("subscribe_type >", value, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("subscribe_type >=", value, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeLessThan(Integer value) {
            addCriterion("subscribe_type <", value, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("subscribe_type <=", value, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeIn(List<Integer> values) {
            addCriterion("subscribe_type in", values, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeNotIn(List<Integer> values) {
            addCriterion("subscribe_type not in", values, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeBetween(Integer value1, Integer value2) {
            addCriterion("subscribe_type between", value1, value2, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("subscribe_type not between", value1, value2, "subscribeType");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationIsNull() {
            addCriterion("subscribe_education is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationIsNotNull() {
            addCriterion("subscribe_education is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationEqualTo(String value) {
            addCriterion("subscribe_education =", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationNotEqualTo(String value) {
            addCriterion("subscribe_education <>", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationGreaterThan(String value) {
            addCriterion("subscribe_education >", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationGreaterThanOrEqualTo(String value) {
            addCriterion("subscribe_education >=", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationLessThan(String value) {
            addCriterion("subscribe_education <", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationLessThanOrEqualTo(String value) {
            addCriterion("subscribe_education <=", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationLike(String value) {
            addCriterion("subscribe_education like", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationNotLike(String value) {
            addCriterion("subscribe_education not like", value, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationIn(List<String> values) {
            addCriterion("subscribe_education in", values, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationNotIn(List<String> values) {
            addCriterion("subscribe_education not in", values, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationBetween(String value1, String value2) {
            addCriterion("subscribe_education between", value1, value2, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeEducationNotBetween(String value1, String value2) {
            addCriterion("subscribe_education not between", value1, value2, "subscribeEducation");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongIsNull() {
            addCriterion("subscribe_belong is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongIsNotNull() {
            addCriterion("subscribe_belong is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongEqualTo(String value) {
            addCriterion("subscribe_belong =", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongNotEqualTo(String value) {
            addCriterion("subscribe_belong <>", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongGreaterThan(String value) {
            addCriterion("subscribe_belong >", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongGreaterThanOrEqualTo(String value) {
            addCriterion("subscribe_belong >=", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongLessThan(String value) {
            addCriterion("subscribe_belong <", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongLessThanOrEqualTo(String value) {
            addCriterion("subscribe_belong <=", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongLike(String value) {
            addCriterion("subscribe_belong like", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongNotLike(String value) {
            addCriterion("subscribe_belong not like", value, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongIn(List<String> values) {
            addCriterion("subscribe_belong in", values, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongNotIn(List<String> values) {
            addCriterion("subscribe_belong not in", values, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongBetween(String value1, String value2) {
            addCriterion("subscribe_belong between", value1, value2, "subscribeBelong");
            return (Criteria) this;
        }

        public Criteria andSubscribeBelongNotBetween(String value1, String value2) {
            addCriterion("subscribe_belong not between", value1, value2, "subscribeBelong");
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