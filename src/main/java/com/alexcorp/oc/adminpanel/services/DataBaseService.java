package com.alexcorp.oc.adminpanel.services;

import com.alexcorp.oc.adminpanel.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataBaseService {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init(){
        try {
            userService.registerNewUser("Admin", "Qwerty37", User.Role.ADMIN, User.Active.ACTIVE);
        }
        catch (Exception e){
            System.out.println("DataBase already init!");
        }
    }
}
