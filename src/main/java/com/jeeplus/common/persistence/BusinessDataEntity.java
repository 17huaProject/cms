package com.jeeplus.common.persistence;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 业务数据Entity类
 */
public abstract class BusinessDataEntity<T> extends BusinessBaseEntity<T> {
	private static final long serialVersionUID = 1L;
	
    protected Date createTime;
    protected Date updateTime;
    protected String operatorId;
    protected Integer isCheck;
    protected Integer status;
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.operatorId = user.getId();
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
			this.operatorId = user.getId();
		}
		this.updateTime = new Date();
	}
	/**
	 * 审核前执行的方法，需要手动调用
	 */
	@Override
	public void preAudit(){
		preUpdate();
		this.setStatus(CommonConstants.STATUS_EFFECTIVE);
		this.setIsCheck(CommonConstants.AUDIT_PASS);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	


}
