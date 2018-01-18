package com.jeeplus.modules.business.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.jeeplus.common.persistence.BusinessBaseEntity;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

@MyBatisDao
public class Feedback   extends BusinessBaseEntity<Feedback> {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String issuerId;

    private String title;

    private String content;

    private Date issueTime;

    private String replierId;

    private String replyContent;

    private Date replyTime;

    private Byte status;

    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return HtmlUtils.htmlUnescape(content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public String getReplierId() {
        return replierId;
    }

    public void setReplierId(String replierId) {
        this.replierId = replierId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	@Override
	public void preInsert() {
		
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
		
	}
}