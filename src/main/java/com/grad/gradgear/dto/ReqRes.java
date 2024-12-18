package com.grad.gradgear.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.grad.gradgear.entity.Checklist;
import com.grad.gradgear.entity.OurUsers;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String role;
    private String password;
    private String firstname;
    private String lastname;
    private String confirmPassword;
    private OurUsers ourUsers;
    private String title;
    private String description;
    private boolean completed;
    private Checklist checklist;
}
