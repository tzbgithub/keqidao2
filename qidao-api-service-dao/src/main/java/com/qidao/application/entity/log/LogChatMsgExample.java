package com.qidao.application.entity.log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogChatMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public LogChatMsgExample() {
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

    public LogChatMsgExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public LogChatMsgExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public LogChatMsgExample page(Integer page, Integer pageSize) {
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

        public Criteria andEasemobMsgIdIsNull() {
            addCriterion("easemob_msg_id is null");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdIsNotNull() {
            addCriterion("easemob_msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdEqualTo(Long value) {
            addCriterion("easemob_msg_id =", value, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdNotEqualTo(Long value) {
            addCriterion("easemob_msg_id <>", value, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdGreaterThan(Long value) {
            addCriterion("easemob_msg_id >", value, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdGreaterThanOrEqualTo(Long value) {
            addCriterion("easemob_msg_id >=", value, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdLessThan(Long value) {
            addCriterion("easemob_msg_id <", value, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdLessThanOrEqualTo(Long value) {
            addCriterion("easemob_msg_id <=", value, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdIn(List<Long> values) {
            addCriterion("easemob_msg_id in", values, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdNotIn(List<Long> values) {
            addCriterion("easemob_msg_id not in", values, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdBetween(Long value1, Long value2) {
            addCriterion("easemob_msg_id between", value1, value2, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andEasemobMsgIdNotBetween(Long value1, Long value2) {
            addCriterion("easemob_msg_id not between", value1, value2, "easemobMsgId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdIsNull() {
            addCriterion("from_member_id is null");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdIsNotNull() {
            addCriterion("from_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdEqualTo(Long value) {
            addCriterion("from_member_id =", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdNotEqualTo(Long value) {
            addCriterion("from_member_id <>", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdGreaterThan(Long value) {
            addCriterion("from_member_id >", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_member_id >=", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdLessThan(Long value) {
            addCriterion("from_member_id <", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("from_member_id <=", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdIn(List<Long> values) {
            addCriterion("from_member_id in", values, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdNotIn(List<Long> values) {
            addCriterion("from_member_id not in", values, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdBetween(Long value1, Long value2) {
            addCriterion("from_member_id between", value1, value2, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("from_member_id not between", value1, value2, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdIsNull() {
            addCriterion("to_member_id is null");
            return (Criteria) this;
        }

        public Criteria andToMemberIdIsNotNull() {
            addCriterion("to_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andToMemberIdEqualTo(Long value) {
            addCriterion("to_member_id =", value, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdNotEqualTo(Long value) {
            addCriterion("to_member_id <>", value, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdGreaterThan(Long value) {
            addCriterion("to_member_id >", value, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_member_id >=", value, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdLessThan(Long value) {
            addCriterion("to_member_id <", value, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("to_member_id <=", value, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdIn(List<Long> values) {
            addCriterion("to_member_id in", values, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdNotIn(List<Long> values) {
            addCriterion("to_member_id not in", values, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdBetween(Long value1, Long value2) {
            addCriterion("to_member_id between", value1, value2, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andToMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("to_member_id not between", value1, value2, "toMemberId");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberIsNull() {
            addCriterion("from_easemob_member is null");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberIsNotNull() {
            addCriterion("from_easemob_member is not null");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberEqualTo(String value) {
            addCriterion("from_easemob_member =", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberNotEqualTo(String value) {
            addCriterion("from_easemob_member <>", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberGreaterThan(String value) {
            addCriterion("from_easemob_member >", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberGreaterThanOrEqualTo(String value) {
            addCriterion("from_easemob_member >=", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberLessThan(String value) {
            addCriterion("from_easemob_member <", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberLessThanOrEqualTo(String value) {
            addCriterion("from_easemob_member <=", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberLike(String value) {
            addCriterion("from_easemob_member like", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberNotLike(String value) {
            addCriterion("from_easemob_member not like", value, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberIn(List<String> values) {
            addCriterion("from_easemob_member in", values, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberNotIn(List<String> values) {
            addCriterion("from_easemob_member not in", values, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberBetween(String value1, String value2) {
            addCriterion("from_easemob_member between", value1, value2, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andFromEasemobMemberNotBetween(String value1, String value2) {
            addCriterion("from_easemob_member not between", value1, value2, "fromEasemobMember");
            return (Criteria) this;
        }

        public Criteria andToEasemobIsNull() {
            addCriterion("to_easemob is null");
            return (Criteria) this;
        }

        public Criteria andToEasemobIsNotNull() {
            addCriterion("to_easemob is not null");
            return (Criteria) this;
        }

        public Criteria andToEasemobEqualTo(String value) {
            addCriterion("to_easemob =", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobNotEqualTo(String value) {
            addCriterion("to_easemob <>", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobGreaterThan(String value) {
            addCriterion("to_easemob >", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobGreaterThanOrEqualTo(String value) {
            addCriterion("to_easemob >=", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobLessThan(String value) {
            addCriterion("to_easemob <", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobLessThanOrEqualTo(String value) {
            addCriterion("to_easemob <=", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobLike(String value) {
            addCriterion("to_easemob like", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobNotLike(String value) {
            addCriterion("to_easemob not like", value, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobIn(List<String> values) {
            addCriterion("to_easemob in", values, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobNotIn(List<String> values) {
            addCriterion("to_easemob not in", values, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobBetween(String value1, String value2) {
            addCriterion("to_easemob between", value1, value2, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andToEasemobNotBetween(String value1, String value2) {
            addCriterion("to_easemob not between", value1, value2, "toEasemob");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNull() {
            addCriterion("chat_type is null");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNotNull() {
            addCriterion("chat_type is not null");
            return (Criteria) this;
        }

        public Criteria andChatTypeEqualTo(Integer value) {
            addCriterion("chat_type =", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotEqualTo(Integer value) {
            addCriterion("chat_type <>", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThan(Integer value) {
            addCriterion("chat_type >", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("chat_type >=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThan(Integer value) {
            addCriterion("chat_type <", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThanOrEqualTo(Integer value) {
            addCriterion("chat_type <=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeIn(List<Integer> values) {
            addCriterion("chat_type in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotIn(List<Integer> values) {
            addCriterion("chat_type not in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeBetween(Integer value1, Integer value2) {
            addCriterion("chat_type between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("chat_type not between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeIsNull() {
            addCriterion("msg_type is null");
            return (Criteria) this;
        }

        public Criteria andMsgTypeIsNotNull() {
            addCriterion("msg_type is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTypeEqualTo(String value) {
            addCriterion("msg_type =", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeNotEqualTo(String value) {
            addCriterion("msg_type <>", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeGreaterThan(String value) {
            addCriterion("msg_type >", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeGreaterThanOrEqualTo(String value) {
            addCriterion("msg_type >=", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeLessThan(String value) {
            addCriterion("msg_type <", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeLessThanOrEqualTo(String value) {
            addCriterion("msg_type <=", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeLike(String value) {
            addCriterion("msg_type like", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeNotLike(String value) {
            addCriterion("msg_type not like", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeIn(List<String> values) {
            addCriterion("msg_type in", values, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeNotIn(List<String> values) {
            addCriterion("msg_type not in", values, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeBetween(String value1, String value2) {
            addCriterion("msg_type between", value1, value2, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeNotBetween(String value1, String value2) {
            addCriterion("msg_type not between", value1, value2, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTimeIsNull() {
            addCriterion("msg_time is null");
            return (Criteria) this;
        }

        public Criteria andMsgTimeIsNotNull() {
            addCriterion("msg_time is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTimeEqualTo(LocalDateTime value) {
            addCriterion("msg_time =", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeNotEqualTo(LocalDateTime value) {
            addCriterion("msg_time <>", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeGreaterThan(LocalDateTime value) {
            addCriterion("msg_time >", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("msg_time >=", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeLessThan(LocalDateTime value) {
            addCriterion("msg_time <", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("msg_time <=", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeIn(List<LocalDateTime> values) {
            addCriterion("msg_time in", values, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeNotIn(List<LocalDateTime> values) {
            addCriterion("msg_time not in", values, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("msg_time between", value1, value2, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("msg_time not between", value1, value2, "msgTime");
            return (Criteria) this;
        }

        public Criteria andChatMsgIsNull() {
            addCriterion("chat_msg is null");
            return (Criteria) this;
        }

        public Criteria andChatMsgIsNotNull() {
            addCriterion("chat_msg is not null");
            return (Criteria) this;
        }

        public Criteria andChatMsgEqualTo(String value) {
            addCriterion("chat_msg =", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgNotEqualTo(String value) {
            addCriterion("chat_msg <>", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgGreaterThan(String value) {
            addCriterion("chat_msg >", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgGreaterThanOrEqualTo(String value) {
            addCriterion("chat_msg >=", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgLessThan(String value) {
            addCriterion("chat_msg <", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgLessThanOrEqualTo(String value) {
            addCriterion("chat_msg <=", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgLike(String value) {
            addCriterion("chat_msg like", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgNotLike(String value) {
            addCriterion("chat_msg not like", value, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgIn(List<String> values) {
            addCriterion("chat_msg in", values, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgNotIn(List<String> values) {
            addCriterion("chat_msg not in", values, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgBetween(String value1, String value2) {
            addCriterion("chat_msg between", value1, value2, "chatMsg");
            return (Criteria) this;
        }

        public Criteria andChatMsgNotBetween(String value1, String value2) {
            addCriterion("chat_msg not between", value1, value2, "chatMsg");
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

        public Criteria andCosAddressIsNull() {
            addCriterion("cos_address is null");
            return (Criteria) this;
        }

        public Criteria andCosAddressIsNotNull() {
            addCriterion("cos_address is not null");
            return (Criteria) this;
        }

        public Criteria andCosAddressEqualTo(String value) {
            addCriterion("cos_address =", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressNotEqualTo(String value) {
            addCriterion("cos_address <>", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressGreaterThan(String value) {
            addCriterion("cos_address >", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressGreaterThanOrEqualTo(String value) {
            addCriterion("cos_address >=", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressLessThan(String value) {
            addCriterion("cos_address <", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressLessThanOrEqualTo(String value) {
            addCriterion("cos_address <=", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressLike(String value) {
            addCriterion("cos_address like", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressNotLike(String value) {
            addCriterion("cos_address not like", value, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressIn(List<String> values) {
            addCriterion("cos_address in", values, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressNotIn(List<String> values) {
            addCriterion("cos_address not in", values, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressBetween(String value1, String value2) {
            addCriterion("cos_address between", value1, value2, "cosAddress");
            return (Criteria) this;
        }

        public Criteria andCosAddressNotBetween(String value1, String value2) {
            addCriterion("cos_address not between", value1, value2, "cosAddress");
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