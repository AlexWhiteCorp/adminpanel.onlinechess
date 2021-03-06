package com.alexcorp.oc.adminpanel.controllers.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ValueCard implements Serializable {

    private String title;
    private String amount;
    private double progress;
    private float delta;

}
