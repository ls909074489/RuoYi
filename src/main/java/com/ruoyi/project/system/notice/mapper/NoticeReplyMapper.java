package com.ruoyi.project.system.notice.mapper;

import java.util.List;
import com.ruoyi.project.system.notice.domain.NoticeReply;

/**
 * 公告 数据层
 * 
 * @author ruoyi
 */
public interface NoticeReplyMapper{

    /**
     * 查询公告回答列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<NoticeReply> selectNoticeList(Integer noticeId);


}