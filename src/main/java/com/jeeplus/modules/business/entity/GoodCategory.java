package com.jeeplus.modules.business.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.jeeplus.common.persistence.BusinessDataEntity;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

public class GoodCategory    extends BusinessDataEntity<GoodCategory> {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String cateAttrib;

    private String createBy;

    private String updateBy;

    private String remarks;

    private Byte deleted;
    
    private String[] cateAttribs;

    
    public String[] getCateAttribs() {
		return cateAttribs;
	}

	public void setCateAttribs(String[] cateAttribs) {
		this.cateAttribs = cateAttribs;
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

    public String getCateAttrib() {
        return cateAttrib;
    }

    public void setCateAttrib(String cateAttrib) {
        this.cateAttrib = cateAttrib;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
    
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.createBy = user.getId();
		}
		this.updateTime = new Date();
		this.createTime = this.updateTime;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user.getId();
		}
		this.updateTime = new Date();
	}
    /**
	 * 逻辑删除前执行的方法，需要手动调用
	 */
	public void preDeleteLogic(){
		preUpdate();
		this.setDeleted(CommonConstants.DELETED_Y);
	}
    
    
}