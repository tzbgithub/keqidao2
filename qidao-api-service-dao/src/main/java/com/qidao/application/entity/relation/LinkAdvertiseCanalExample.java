package com.qidao.application.entity.relation;

import java.util.ArrayList;
import java.util.List;

public class LinkAdvertiseCanalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public LinkAdvertiseCanalExample() {
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

    public LinkAdvertiseCanalExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public LinkAdvertiseCanalExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public LinkAdvertiseCanalExample page(Integer page, Integer pageSize) {
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

        public Criteria andAdvertiseIdIsNull() {
            addCriterion("advertise_id is null");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdIsNotNull() {
            addCriterion("advertise_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdEqualTo(Long value) {
            addCriterion("advertise_id =", value, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdNotEqualTo(Long value) {
            addCriterion("advertise_id <>", value, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdGreaterThan(Long value) {
            addCriterion("advertise_id >", value, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("advertise_id >=", value, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdLessThan(Long value) {
            addCriterion("advertise_id <", value, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdLessThanOrEqualTo(Long value) {
            addCriterion("advertise_id <=", value, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdIn(List<Long> values) {
            addCriterion("advertise_id in", values, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdNotIn(List<Long> values) {
            addCriterion("advertise_id not in", values, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdBetween(Long value1, Long value2) {
            addCriterion("advertise_id between", value1, value2, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andAdvertiseIdNotBetween(Long value1, Long value2) {
            addCriterion("advertise_id not between", value1, value2, "advertiseId");
            return (Criteria) this;
        }

        public Criteria andCanalIdIsNull() {
            addCriterion("canal_id is null");
            return (Criteria) this;
        }

        public Criteria andCanalIdIsNotNull() {
            addCriterion("canal_id is not null");
            return (Criteria) this;
        }

        public Criteria andCanalIdEqualTo(Long value) {
            addCriterion("canal_id =", value, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdNotEqualTo(Long value) {
            addCriterion("canal_id <>", value, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdGreaterThan(Long value) {
            addCriterion("canal_id >", value, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdGreaterThanOrEqualTo(Long value) {
            addCriterion("canal_id >=", value, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdLessThan(Long value) {
            addCriterion("canal_id <", value, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdLessThanOrEqualTo(Long value) {
            addCriterion("canal_id <=", value, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdIn(List<Long> values) {
            addCriterion("canal_id in", values, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdNotIn(List<Long> values) {
            addCriterion("canal_id not in", values, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdBetween(Long value1, Long value2) {
            addCriterion("canal_id between", value1, value2, "canalId");
            return (Criteria) this;
        }

        public Criteria andCanalIdNotBetween(Long value1, Long value2) {
            addCriterion("canal_id not between", value1, value2, "canalId");
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