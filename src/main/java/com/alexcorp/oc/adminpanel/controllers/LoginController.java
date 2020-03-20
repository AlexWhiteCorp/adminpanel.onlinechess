package com.alexcorp.oc.adminpanel.controllers;

import com.alexcorp.oc.adminpanel.controllers.requestbodys.LoginUser;
import com.alexcorp.oc.adminpanel.domains.User;
import com.alexcorp.oc.adminpanel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class LoginController {

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("isDevMode", profile.equals("dev"));
        return "authority";
    }

    @PostMapping(value = "/login")
    public @ResponseBody
    String loginProcessing(@RequestBody LoginUser user, HttpSession session) {
        User u = userRepository.findByNickname(user.nickname);
        if(u == null){
            return "{\"error\" : \"Account doesn't exist!\"}";
        }

        if(!passwordEncoder.matches(user.password, u.getSecureCode())){
            return "{\"error\" : \"Incorrect password!\"}";
        }

        if(!u.getActive().equals(User.Active.ACTIVE)){
            return "{\"error\" : \"Account is NonActive or Banned!\"}";
        }

        u.setLastLogin(new Date());
        userRepository.save(u);

        Authentication authentication = new UsernamePasswordAuthenticationToken(u.getNickname(), u.getPassword(), u.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setMaxInactiveInterval(7 * 24 * 60 * 60);

        session.setAttribute("nickname", u.getNickname());
        session.setAttribute("role", u.getRole().toString().toLowerCase());
        return "{\"error\" : \"\"}";
    }
}
