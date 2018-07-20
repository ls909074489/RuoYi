package com.ruoyi.project.system.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.project.system.notice.domain.NoticeReply;
import com.ruoyi.project.system.notice.mapper.NoticeReplyMapper;

/**
 * 公告 服务层实现
 * 
 * @author ruoyi
 * @date 2018-06-25
 */
@Service
public class NoticeReplyServiceImpl implements INoticeReplyService
{
    @Autowired
    private NoticeReplyMapper noticeReplyMapper;

	@Override
	public List<NoticeReply> selectNoticeList(Integer noticeId) {
		return noticeReplyMapper.selectNoticeList(noticeId);
	}


}
