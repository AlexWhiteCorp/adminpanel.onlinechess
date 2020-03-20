package com.alexcorp.oc.adminpanel.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/public/images/favicon.ico");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/public/images/");
        registry.addResourceHandler("/styles/**").addResourceLocations("classpath:/static/public/styles/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("classpath:/static/public/scripts/");
        registry.addResourceHandler("/vue/**").addResourceLocations("classpath:/static/vue/");
    }

}