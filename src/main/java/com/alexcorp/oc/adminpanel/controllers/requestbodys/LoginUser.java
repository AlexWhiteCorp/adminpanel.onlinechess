package com.alexcorp.oc.adminpanel.controllers.requestbodys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {

    public String nickname;
    public String password;

}
