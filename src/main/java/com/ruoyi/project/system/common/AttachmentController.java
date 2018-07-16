package com.ruoyi.project.system.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.utils.FileUploadUtils;
import com.ruoyi.framework.web.domain.AjaxResult;

@Controller
@RequestMapping("/common/attachment")
public class AttachmentController {

	@ResponseBody
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file,
            HttpServletRequest request){
		AjaxResult result=new AjaxResult();
		try {
			result.put("flag", true);
			result.put("msg", "profile/"+FileUploadUtils.upload(file));
		} catch (IOException e) {
			result.put("suc", false);
			e.printStackTrace();
		}
        return result;
    }
}
