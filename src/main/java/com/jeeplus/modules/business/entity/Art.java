package com.jeeplus.modules.business.entity;

import org.springframework.web.util.HtmlUtils;

import com.jeeplus.common.persistence.BusinessDataEntity;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

public class Art   extends BusinessDataEntity<Art>{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String artName;

    private Integer eventNum;

    private String type;

    private Float easyLevel;
    private Float easyLevelStart;
    private Float easyLevelEnd;

    private Byte isFree;

    private String bigImg;

    private String smallImg;

    private String artDesc;

    private String note;

    private String remark;

    private String content;
    
    private String easyLevelScope;
	private Integer materialModelId; //物料模板ID
    
	
	
    public Integer getMaterialModelId() {
		return materialModelId;
	}

	public void setMaterialModelId(Integer materialModelId) {
		this.materialModelId = materialModelId;
	}

	public String getEasyLevelScope() {
		return easyLevelScope;
	}

	public void setEasyLevelScope(String easyLevelScope) {
		this.easyLevelScope = easyLevelScope;
	}

	public Float getEasyLevelStart() {
		return easyLevelStart;
	}

	public void setEasyLevelStart(Float easyLevelStart) {
		this.easyLevelStart = easyLevelStart;
	}

	public Float getEasyLevelEnd() {
		return easyLevelEnd;
	}

	public void setEasyLevelEnd(Float easyLevelEnd) {
		this.easyLevelEnd = easyLevelEnd;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }

    public Integer getEventNum() {
        return eventNum;
    }

    public void setEventNum(Integer eventNum) {
        this.eventNum = eventNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getEasyLevel() {
        return easyLevel;
    }

    public void setEasyLevel(Float easyLevel) {
        this.easyLevel = easyLevel;
    }

    public Byte getIsFree() {
        return isFree;
    }

    public void setIsFree(Byte isFree) {
        this.isFree = isFree;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getArtDesc() {
        return artDesc;
    }

    public void setArtDesc(String artDesc) {
        this.artDesc = artDesc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return HtmlUtils.htmlUnescape(content);
    }

    public void setContent(String content) {
        this.content = content;
    }
}