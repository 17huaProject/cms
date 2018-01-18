package com.jeeplus.modules.business.dto;

public class ArtDTO{
	private Integer id;

    private String artName;

    private Integer eventNum;

    private String type;

    private Float easyLevel;

    private Byte isFree;

    private String bigImg;

    private String smallImg;

    private String note;

    private String easyLevelScope;
    
    public String getEasyLevelScope() {
		return easyLevelScope;
	}

	public void setEasyLevelScope(String easyLevelScope) {
		this.easyLevelScope = easyLevelScope;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}