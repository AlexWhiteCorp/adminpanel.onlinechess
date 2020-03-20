package com.alexcorp.oc.adminpanel.controllers;

import com.alexcorp.oc.adminpanel.domains.User;
import com.alexcorp.oc.adminpanel.repositories.SearchCriteria;
import com.alexcorp.oc.adminpanel.repositories.UserDao;
import com.alexcorp.oc.adminpanel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersCrudController {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private final static long SECONDS_IN_DAY = 24 * 60 * 60 * 1000;
    private final static long SECONDS_IN_WEEK = 7 * SECONDS_IN_DAY;

    private final static int TODAY = 0;
    private final static int YESTERDAY = 1;
    private final static int LAST_WEEK = 7;
    private final static int LAST_4_WEEK = 28;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @GetMapping("/rest/users")
    public @ResponseBody
    Object findAllUsersByRole(@RequestParam(required = false) String search) {
        List<SearchCriteria> params = new ArrayList<>();

        if (search != null) {
            String[] fields = search.split(",");
            for(String field : fields) {
                params.add(new SearchCriteria(field.split(":")[0], ":", field.split(":")[1]));
            }
        }

        return userDao.searchUser(params);
    }

    @GetMapping("/rest/user/create")
    public @ResponseBody
    String findAllUsers(@RequestParam String nickname,
                        @RequestParam String password,
                        @RequestParam User.Active active,
                        @RequestParam User.Role role) {
        try {
            if(nickname.length() == 0) return "{\"error\" : \"Nickname cannot be empty\"}";
            if(password.length() < 8) return "{\"error\" : \"Password less then 8 characters\"}";

            userService.registerNewUser(nickname, password, role, active);
            return "";
        }
        catch (Exception e){
            e.printStackTrace();
            return "{\"error\" : \"Saving error\"}";
        }
    }

    @GetMapping("/rest/user/save")
    public @ResponseBody
    String findAllUsers(@RequestParam long id,
                        @RequestParam String nickname,
                        @RequestParam String password,
                        @RequestParam User.Active active,
                        @RequestParam User.Role role) {
        try {
            if(nickname.length() == 0) return "{\"error\" : \"Nickname cannot be empty\"}";
            if(password.length() < 8) return "{\"error\" : \"Password less then 8 characters\"}";

            userService.updateInfo(new User(id, nickname, password, active, role, null));
            return "";
        }
        catch (Exception e){
            e.printStackTrace();
            return "{\"error\" : \"Saving error\"}";
        }
    }
}
