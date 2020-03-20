package com.alexcorp.oc.adminpanel.configs;

import com.alexcorp.oc.adminpanel.security.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/**", "/login").permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProcessing")
                    .permitAll()
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember")
                    .key("uniqueAndSecret")
                    .tokenValiditySeconds(7 * 24 * 60 * 60); // 7 days
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

}
