package com.ruoyi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ruoyi.framework.config.RuoYiConfig;
 
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
 
    // 默认上传的地址
    private static String defaultBaseDir = RuoYiConfig.getProfile();
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    	System.out.println("defaultBaseDir:"+defaultBaseDir);
        registry.addResourceHandler("/profile/**").addResourceLocations("file:D:/profile/");
    }
 
}