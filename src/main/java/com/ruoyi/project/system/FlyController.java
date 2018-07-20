package com.ruoyi.project.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruoyi.framework.web.controller.BaseController;

@Controller
@RequestMapping("/pub/fly")
public class FlyController extends BaseController{
	
	private String prefix = "fly";
	
    @RequestMapping("/login")
    public String detailList(Model model){
        return prefix + "/login";
    }
    
    @RequestMapping("/reg")
    public String reg(Model model){
        return prefix + "/reg";
    }
}
