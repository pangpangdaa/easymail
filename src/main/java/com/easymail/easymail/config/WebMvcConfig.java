package com.easymail.easymail.config;

import com.easymail.easymail.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/mail**").addPathPatterns("/mail/**");
      /*  registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/ocr**").addPathPatterns("/ocr/**");
*/

    }

}
