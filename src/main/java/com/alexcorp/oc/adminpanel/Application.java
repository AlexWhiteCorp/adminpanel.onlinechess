package com.alexcorp.oc.adminpanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:/properties/dev/spring.properties")
    static class Development
    { }

    @Configuration
    @Profile("prod")
    @PropertySource({"classpath:/properties/prod/spring.properties"})
    static class Production
    { }

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}