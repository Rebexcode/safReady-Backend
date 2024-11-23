package com.grad.gradgear.dto;

import com.grad.gradgear.entity.OurUsers;
import lombok.Data;

@Data
public class SigninReqRes {

    private String email;
    private String password;
}
