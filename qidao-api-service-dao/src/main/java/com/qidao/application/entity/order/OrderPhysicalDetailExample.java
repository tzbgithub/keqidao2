package com.qidao.application.entity.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderPhysicalDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public OrderPhysicalDetailExample() {
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

    public OrderPhysicalDetailExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public OrderPhysicalDetailExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public OrderPhysicalDetailExample page(Integer page, Integer pageSize) {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeIsNull() {
            addCriterion("address_province_code is null");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeIsNotNull() {
            addCriterion("address_province_code is not null");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeEqualTo(String value) {
            addCriterion("address_province_code =", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeNotEqualTo(String value) {
            addCriterion("address_province_code <>", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeGreaterThan(String value) {
            addCriterion("address_province_code >", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("address_province_code >=", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeLessThan(String value) {
            addCriterion("address_province_code <", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeLessThanOrEqualTo(String value) {
            addCriterion("address_province_code <=", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeLike(String value) {
            addCriterion("address_province_code like", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeNotLike(String value) {
            addCriterion("address_province_code not like", value, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeIn(List<String> values) {
            addCriterion("address_province_code in", values, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeNotIn(List<String> values) {
            addCriterion("address_province_code not in", values, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeBetween(String value1, String value2) {
            addCriterion("address_province_code between", value1, value2, "addressProvinceCode");
            return (Criteria) this;
        }

        public Criteria andAddressProvinceCodeNotBetween(String value1, String value2) {
            addCriterion("address_province_code not between", value1, value2, "addressProvinceCode");
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

        public Criteria andAddressCityCodeIsNull() {
            addCriterion("address_city_code is null");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeIsNotNull() {
            addCriterion("address_city_code is not null");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeEqualTo(String value) {
            addCriterion("address_city_code =", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeNotEqualTo(String value) {
            addCriterion("address_city_code <>", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeGreaterThan(String value) {
            addCriterion("address_city_code >", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("address_city_code >=", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeLessThan(String value) {
            addCriterion("address_city_code <", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeLessThanOrEqualTo(String value) {
            addCriterion("address_city_code <=", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeLike(String value) {
            addCriterion("address_city_code like", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeNotLike(String value) {
            addCriterion("address_city_code not like", value, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeIn(List<String> values) {
            addCriterion("address_city_code in", values, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeNotIn(List<String> values) {
            addCriterion("address_city_code not in", values, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeBetween(String value1, String value2) {
            addCriterion("address_city_code between", value1, value2, "addressCityCode");
            return (Criteria) this;
        }

        public Criteria andAddressCityCodeNotBetween(String value1, String value2) {
            addCriterion("address_city_code not between", value1, value2, "addressCityCode");
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

        public Criteria andAddressAreaCodeIsNull() {
            addCriterion("address_area_code is null");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeIsNotNull() {
            addCriterion("address_area_code is not null");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeEqualTo(String value) {
            addCriterion("address_area_code =", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeNotEqualTo(String value) {
            addCriterion("address_area_code <>", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeGreaterThan(String value) {
            addCriterion("address_area_code >", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("address_area_code >=", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeLessThan(String value) {
            addCriterion("address_area_code <", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("address_area_code <=", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeLike(String value) {
            addCriterion("address_area_code like", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeNotLike(String value) {
            addCriterion("address_area_code not like", value, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeIn(List<String> values) {
            addCriterion("address_area_code in", values, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeNotIn(List<String> values) {
            addCriterion("address_area_code not in", values, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeBetween(String value1, String value2) {
            addCriterion("address_area_code between", value1, value2, "addressAreaCode");
            return (Criteria) this;
        }

        public Criteria andAddressAreaCodeNotBetween(String value1, String value2) {
            addCriterion("address_area_code not between", value1, value2, "addressAreaCode");
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

        public Criteria andRecipientNameIsNull() {
            addCriterion("recipient_name is null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIsNotNull() {
            addCriterion("recipient_name is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameEqualTo(String value) {
            addCriterion("recipient_name =", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotEqualTo(String value) {
            addCriterion("recipient_name <>", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThan(String value) {
            addCriterion("recipient_name >", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_name >=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThan(String value) {
            addCriterion("recipient_name <", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThanOrEqualTo(String value) {
            addCriterion("recipient_name <=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLike(String value) {
            addCriterion("recipient_name like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotLike(String value) {
            addCriterion("recipient_name not like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIn(List<String> values) {
            addCriterion("recipient_name in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotIn(List<String> values) {
            addCriterion("recipient_name not in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameBetween(String value1, String value2) {
            addCriterion("recipient_name between", value1, value2, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotBetween(String value1, String value2) {
            addCriterion("recipient_name not between", value1, value2, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIsNull() {
            addCriterion("recipient_phone is null");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIsNotNull() {
            addCriterion("recipient_phone is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneEqualTo(String value) {
            addCriterion("recipient_phone =", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotEqualTo(String value) {
            addCriterion("recipient_phone <>", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneGreaterThan(String value) {
            addCriterion("recipient_phone >", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_phone >=", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLessThan(String value) {
            addCriterion("recipient_phone <", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLessThanOrEqualTo(String value) {
            addCriterion("recipient_phone <=", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLike(String value) {
            addCriterion("recipient_phone like", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotLike(String value) {
            addCriterion("recipient_phone not like", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIn(List<String> values) {
            addCriterion("recipient_phone in", values, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotIn(List<String> values) {
            addCriterion("recipient_phone not in", values, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneBetween(String value1, String value2) {
            addCriterion("recipient_phone between", value1, value2, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotBetween(String value1, String value2) {
            addCriterion("recipient_phone not between", value1, value2, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeIsNull() {
            addCriterion("logistics_type is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeIsNotNull() {
            addCriterion("logistics_type is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeEqualTo(Integer value) {
            addCriterion("logistics_type =", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeNotEqualTo(Integer value) {
            addCriterion("logistics_type <>", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeGreaterThan(Integer value) {
            addCriterion("logistics_type >", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_type >=", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeLessThan(Integer value) {
            addCriterion("logistics_type <", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_type <=", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeIn(List<Integer> values) {
            addCriterion("logistics_type in", values, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeNotIn(List<Integer> values) {
            addCriterion("logistics_type not in", values, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeBetween(Integer value1, Integer value2) {
            addCriterion("logistics_type between", value1, value2, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_type not between", value1, value2, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoIsNull() {
            addCriterion("logistics_no is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoIsNotNull() {
            addCriterion("logistics_no is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoEqualTo(String value) {
            addCriterion("logistics_no =", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotEqualTo(String value) {
            addCriterion("logistics_no <>", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoGreaterThan(String value) {
            addCriterion("logistics_no >", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_no >=", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoLessThan(String value) {
            addCriterion("logistics_no <", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoLessThanOrEqualTo(String value) {
            addCriterion("logistics_no <=", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoLike(String value) {
            addCriterion("logistics_no like", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotLike(String value) {
            addCriterion("logistics_no not like", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoIn(List<String> values) {
            addCriterion("logistics_no in", values, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotIn(List<String> values) {
            addCriterion("logistics_no not in", values, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoBetween(String value1, String value2) {
            addCriterion("logistics_no between", value1, value2, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotBetween(String value1, String value2) {
            addCriterion("logistics_no not between", value1, value2, "logisticsNo");
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