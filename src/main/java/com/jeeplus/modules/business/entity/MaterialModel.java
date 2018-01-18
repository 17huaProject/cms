package com.jeeplus.modules.business.entity;

import org.springframework.web.util.HtmlUtils;

import com.jeeplus.common.persistence.BusinessDataEntity;

public class MaterialModel  extends BusinessDataEntity<MaterialModel>{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String goodsInfo;

    private Byte deleted;
    private Integer artId;

    
    
    public Integer getArtId() {
		return artId;
	}

	public void setArtId(Integer artId) {
		this.artId = artId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = HtmlUtils.htmlUnescape(goodsInfo);
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
}