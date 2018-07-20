package com.ruoyi.project.system.notice.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 公告表 sys_notice
 * 
 * @author ruoyi
 */
public class NoticeReply extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Integer replyId;
    /** 公告ID */
    private Integer noticeId;
    /** 回答内容 */
    private String replyContent;
    
    private String status;
    
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
