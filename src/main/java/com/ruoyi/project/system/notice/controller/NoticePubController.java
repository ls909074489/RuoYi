package com.ruoyi.project.system.notice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.ActionResultModel;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.notice.domain.Notice;
import com.ruoyi.project.system.notice.domain.NoticeReply;
import com.ruoyi.project.system.notice.service.INoticeReplyService;
import com.ruoyi.project.system.notice.service.INoticeService;

/**
 * 公告 信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/pub/notice")
public class NoticePubController extends BaseController{

	private String prefix = "system/notice";
	
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private INoticeReplyService noticeReplyService;

    @RequestMapping("/{userName}")
    public String detailList(@PathVariable("userName") String userName, Model model)
    {
    	 List<Notice> list = noticeService.listByCreator(userName);
    	 System.out.println(list.size());
    	 model.addAttribute("list", list);
        return prefix + "/pub_notice_list";
    }

    @RequestMapping("/view")
    public String detailView(@RequestParam Integer noticeId, Model model)
    {
    	 Notice notice = noticeService.selectNoticeById(noticeId);
    	 noticeService.addViewCount(noticeId);
    	 System.out.println(notice);
    	 System.out.println(notice.getReplyCount()+"========");
    	 model.addAttribute("notice", notice);
        return prefix + "/pub_notice_detail";
    }
    
    /**
     * 添加回复
     * @param noticeId
     * @param replyContent
     * @return
     */
    @ResponseBody
    @RequestMapping("/addReply")
    public AjaxResult addReply(@RequestParam Integer noticeId, String replyContent){
    	int updateCount= noticeService.addReplyCount(noticeId);
    	System.out.println(updateCount);
        return AjaxResult.success("");
    }
    
    /**
     * 获取回复
     * @param noticeId
     * @param replyContent
     * @return
     */
    @ResponseBody
    @RequestMapping("/dataReply")
    public ActionResultModel<NoticeReply> dataReply(@RequestParam Integer noticeId){
    	ActionResultModel<NoticeReply> arm=new ActionResultModel<NoticeReply>();
    	List<NoticeReply> list= noticeReplyService.selectNoticeList(noticeId);
    	arm.setSuc(true);
    	arm.setRecords(list);
        return arm;
    }
}
