package com.jeeplus.modules.business.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArtExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArtExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andArtNameIsNull() {
            addCriterion("art_name is null");
            return (Criteria) this;
        }

        public Criteria andArtNameIsNotNull() {
            addCriterion("art_name is not null");
            return (Criteria) this;
        }

        public Criteria andArtNameEqualTo(String value) {
            addCriterion("art_name =", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameNotEqualTo(String value) {
            addCriterion("art_name <>", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameGreaterThan(String value) {
            addCriterion("art_name >", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameGreaterThanOrEqualTo(String value) {
            addCriterion("art_name >=", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameLessThan(String value) {
            addCriterion("art_name <", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameLessThanOrEqualTo(String value) {
            addCriterion("art_name <=", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameLike(String value) {
            addCriterion("art_name like", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameNotLike(String value) {
            addCriterion("art_name not like", value, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameIn(List<String> values) {
            addCriterion("art_name in", values, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameNotIn(List<String> values) {
            addCriterion("art_name not in", values, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameBetween(String value1, String value2) {
            addCriterion("art_name between", value1, value2, "artName");
            return (Criteria) this;
        }

        public Criteria andArtNameNotBetween(String value1, String value2) {
            addCriterion("art_name not between", value1, value2, "artName");
            return (Criteria) this;
        }

        public Criteria andEventNumIsNull() {
            addCriterion("event_num is null");
            return (Criteria) this;
        }

        public Criteria andEventNumIsNotNull() {
            addCriterion("event_num is not null");
            return (Criteria) this;
        }

        public Criteria andEventNumEqualTo(Integer value) {
            addCriterion("event_num =", value, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumNotEqualTo(Integer value) {
            addCriterion("event_num <>", value, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumGreaterThan(Integer value) {
            addCriterion("event_num >", value, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("event_num >=", value, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumLessThan(Integer value) {
            addCriterion("event_num <", value, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumLessThanOrEqualTo(Integer value) {
            addCriterion("event_num <=", value, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumIn(List<Integer> values) {
            addCriterion("event_num in", values, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumNotIn(List<Integer> values) {
            addCriterion("event_num not in", values, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumBetween(Integer value1, Integer value2) {
            addCriterion("event_num between", value1, value2, "eventNum");
            return (Criteria) this;
        }

        public Criteria andEventNumNotBetween(Integer value1, Integer value2) {
            addCriterion("event_num not between", value1, value2, "eventNum");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andEasyLevelIsNull() {
            addCriterion("easy_level is null");
            return (Criteria) this;
        }

        public Criteria andEasyLevelIsNotNull() {
            addCriterion("easy_level is not null");
            return (Criteria) this;
        }

        public Criteria andEasyLevelEqualTo(Float value) {
            addCriterion("easy_level =", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelNotEqualTo(Float value) {
            addCriterion("easy_level <>", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelGreaterThan(Float value) {
            addCriterion("easy_level >", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelGreaterThanOrEqualTo(Float value) {
            addCriterion("easy_level >=", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelLessThan(Float value) {
            addCriterion("easy_level <", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelLessThanOrEqualTo(Float value) {
            addCriterion("easy_level <=", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelLike(Float value) {
            addCriterion("easy_level like", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelNotLike(Float value) {
            addCriterion("easy_level not like", value, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelIn(List<Float> values) {
            addCriterion("easy_level in", values, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelNotIn(List<Float> values) {
            addCriterion("easy_level not in", values, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelBetween(Float value1, Float value2) {
            addCriterion("easy_level between", value1, value2, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andEasyLevelNotBetween(Float value1, Float value2) {
            addCriterion("easy_level not between", value1, value2, "easyLevel");
            return (Criteria) this;
        }

        public Criteria andIsFreeIsNull() {
            addCriterion("is_free is null");
            return (Criteria) this;
        }

        public Criteria andIsFreeIsNotNull() {
            addCriterion("is_free is not null");
            return (Criteria) this;
        }

        public Criteria andIsFreeEqualTo(Byte value) {
            addCriterion("is_free =", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeNotEqualTo(Byte value) {
            addCriterion("is_free <>", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeGreaterThan(Byte value) {
            addCriterion("is_free >", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_free >=", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeLessThan(Byte value) {
            addCriterion("is_free <", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeLessThanOrEqualTo(Byte value) {
            addCriterion("is_free <=", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeIn(List<Byte> values) {
            addCriterion("is_free in", values, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeNotIn(List<Byte> values) {
            addCriterion("is_free not in", values, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeBetween(Byte value1, Byte value2) {
            addCriterion("is_free between", value1, value2, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeNotBetween(Byte value1, Byte value2) {
            addCriterion("is_free not between", value1, value2, "isFree");
            return (Criteria) this;
        }

        public Criteria andBigImgIsNull() {
            addCriterion("big_img is null");
            return (Criteria) this;
        }

        public Criteria andBigImgIsNotNull() {
            addCriterion("big_img is not null");
            return (Criteria) this;
        }

        public Criteria andBigImgEqualTo(String value) {
            addCriterion("big_img =", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotEqualTo(String value) {
            addCriterion("big_img <>", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgGreaterThan(String value) {
            addCriterion("big_img >", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgGreaterThanOrEqualTo(String value) {
            addCriterion("big_img >=", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgLessThan(String value) {
            addCriterion("big_img <", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgLessThanOrEqualTo(String value) {
            addCriterion("big_img <=", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgLike(String value) {
            addCriterion("big_img like", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotLike(String value) {
            addCriterion("big_img not like", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgIn(List<String> values) {
            addCriterion("big_img in", values, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotIn(List<String> values) {
            addCriterion("big_img not in", values, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgBetween(String value1, String value2) {
            addCriterion("big_img between", value1, value2, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotBetween(String value1, String value2) {
            addCriterion("big_img not between", value1, value2, "bigImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgIsNull() {
            addCriterion("small_img is null");
            return (Criteria) this;
        }

        public Criteria andSmallImgIsNotNull() {
            addCriterion("small_img is not null");
            return (Criteria) this;
        }

        public Criteria andSmallImgEqualTo(String value) {
            addCriterion("small_img =", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgNotEqualTo(String value) {
            addCriterion("small_img <>", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgGreaterThan(String value) {
            addCriterion("small_img >", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgGreaterThanOrEqualTo(String value) {
            addCriterion("small_img >=", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgLessThan(String value) {
            addCriterion("small_img <", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgLessThanOrEqualTo(String value) {
            addCriterion("small_img <=", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgLike(String value) {
            addCriterion("small_img like", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgNotLike(String value) {
            addCriterion("small_img not like", value, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgIn(List<String> values) {
            addCriterion("small_img in", values, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgNotIn(List<String> values) {
            addCriterion("small_img not in", values, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgBetween(String value1, String value2) {
            addCriterion("small_img between", value1, value2, "smallImg");
            return (Criteria) this;
        }

        public Criteria andSmallImgNotBetween(String value1, String value2) {
            addCriterion("small_img not between", value1, value2, "smallImg");
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

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
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

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andArtDescIsNull() {
            addCriterion("art_desc is null");
            return (Criteria) this;
        }

        public Criteria andArtDescIsNotNull() {
            addCriterion("art_desc is not null");
            return (Criteria) this;
        }

        public Criteria andArtDescEqualTo(String value) {
            addCriterion("art_desc =", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescNotEqualTo(String value) {
            addCriterion("art_desc <>", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescGreaterThan(String value) {
            addCriterion("art_desc >", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescGreaterThanOrEqualTo(String value) {
            addCriterion("art_desc >=", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescLessThan(String value) {
            addCriterion("art_desc <", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescLessThanOrEqualTo(String value) {
            addCriterion("art_desc <=", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescLike(String value) {
            addCriterion("art_desc like", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescNotLike(String value) {
            addCriterion("art_desc not like", value, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescIn(List<String> values) {
            addCriterion("art_desc in", values, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescNotIn(List<String> values) {
            addCriterion("art_desc not in", values, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescBetween(String value1, String value2) {
            addCriterion("art_desc between", value1, value2, "artDesc");
            return (Criteria) this;
        }

        public Criteria andArtDescNotBetween(String value1, String value2) {
            addCriterion("art_desc not between", value1, value2, "artDesc");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
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

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIsNull() {
            addCriterion("operator_id is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIsNotNull() {
            addCriterion("operator_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorIdEqualTo(String value) {
            addCriterion("operator_id =", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotEqualTo(String value) {
            addCriterion("operator_id <>", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdGreaterThan(String value) {
            addCriterion("operator_id >", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdGreaterThanOrEqualTo(String value) {
            addCriterion("operator_id >=", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLessThan(String value) {
            addCriterion("operator_id <", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLessThanOrEqualTo(String value) {
            addCriterion("operator_id <=", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLike(String value) {
            addCriterion("operator_id like", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotLike(String value) {
            addCriterion("operator_id not like", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIn(List<String> values) {
            addCriterion("operator_id in", values, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotIn(List<String> values) {
            addCriterion("operator_id not in", values, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdBetween(String value1, String value2) {
            addCriterion("operator_id between", value1, value2, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotBetween(String value1, String value2) {
            addCriterion("operator_id not between", value1, value2, "operatorId");
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