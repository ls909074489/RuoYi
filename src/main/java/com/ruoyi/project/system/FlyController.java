package com.ruoyi.project.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.service.IUserService;

@Controller
@RequestMapping("/pub/fly")
public class FlyController extends BaseController{
	
	private String prefix = "fly";
	
	@Autowired
	private IUserService userService;
	
    @RequestMapping("/login")
    public String detailList(Model model){
    	ServletUtils.getRequest().setAttribute(ShiroConstants.CURRENT_CAPTCHA,"123456");
    	ShiroUtils.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,"123456");
    	System.out.println(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA));
        return prefix + "/login";
    }
    
    @GetMapping("/reg")
    public String reg(Model model){
        return prefix + "/reg";
    }
    
    @ResponseBody
    @PostMapping("/reg")
    public AjaxResult saveReg(User user){
    	user.setLoginName(user.getUserName());
    	user.setPostIds(new Long[0]);
    	user.setRoleIds(new Long[0]);
    	userService.saveUser(user);
    	return AjaxResult.success("");
    }
}
