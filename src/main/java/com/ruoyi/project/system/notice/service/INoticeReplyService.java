package com.ruoyi.project.system.notice.service;

import java.util.List;
import com.ruoyi.project.system.notice.domain.NoticeReply;

/**
 * 公告 服务层
 * 
 * @author ruoyi
 */
public interface INoticeReplyService{

	 /**
     * 查询公告回答列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<NoticeReply> selectNoticeList(Integer noticeId);

    
	public int addReply(Integer noticeId, String replyContent);


}
