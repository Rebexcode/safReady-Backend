package com.grad.gradgear.dto;

import lombok.Data;

@Data
public class SignupReqRes {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
}
