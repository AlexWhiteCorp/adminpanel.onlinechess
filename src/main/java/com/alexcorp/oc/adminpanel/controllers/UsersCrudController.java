package com.alexcorp.oc.adminpanel.controllers;

import com.alexcorp.oc.adminpanel.domains.User;
import com.alexcorp.oc.adminpanel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersCrudController {

    @Autowired
    private UserService userService;

    @GetMapping("/rest/users")
    public @ResponseBody
    Object findAllUsersByRole(@RequestParam(required = false, defaultValue = "") String search,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 50) Pageable pageable)
    {
        return userService.findAllByFilters(search, pageable);
    }

    @GetMapping("/rest/user/create")
    public @ResponseBody
    String findAllUsers(@RequestParam String nickname,
                        @RequestParam String password,
                        @RequestParam User.Active active,
                        @RequestParam User.Role role)
    {
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
                        @RequestParam User.Role role)
    {
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
