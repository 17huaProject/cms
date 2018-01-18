package com.jeeplus.modules.business.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.jeeplus.common.persistence.BusinessBaseEntity;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Joinus   extends BusinessBaseEntity<Joinus> {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String phone;

    private String email;

    private String position;

    private String description;

    private Date createTime;

    private Byte status;

    private String suggestion;

    private String replierId;

    private Date replyTime;
    private Date startTime ; 	//开始时间
    private Date endTime ;		//结束时间

    
    
    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getReplierId() {
        return replierId;
    }

    public void setReplierId(String replierId) {
        this.replierId = replierId;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

	@Override
	public void preInsert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preUpdate() {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.replierId = user.getId();
		}
		this.replyTime = new Date();
		this.status =  CommonConstants.STATUS_DEALED ;
		
	}

	@Override
	public void preAudit() {
		// TODO Auto-generated method stub
		
	}
}