package com.ruoyi.project.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.web.controller.BaseController;

@Controller
@RequestMapping("/pub/fly")
public class FlyController extends BaseController{
	
	private String prefix = "fly";
	
    @RequestMapping("/login")
    public String detailList(Model model){
    	ServletUtils.getRequest().setAttribute(ShiroConstants.CURRENT_CAPTCHA,"123456");
    	ShiroUtils.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,"123456");
    	System.out.println(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA));
        return prefix + "/login";
    }
    
    @RequestMapping("/reg")
    public String reg(Model model){
        return prefix + "/reg";
    }
}
