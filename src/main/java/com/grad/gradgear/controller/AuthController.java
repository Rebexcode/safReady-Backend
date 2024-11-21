package com.grad.gradgear.controller;

import com.grad.gradgear.dto.ReqRes;
import com.grad.gradgear.dto.SigninReqRes;
import com.grad.gradgear.dto.SignupReqRes;
import com.grad.gradgear.entity.Form;
import com.grad.gradgear.entity.OurUsers;
import com.grad.gradgear.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody @Validated SignupReqRes signupReqRes){
        return ResponseEntity.ok(authService.signUp(signupReqRes));
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/users")
    public ResponseEntity<List<OurUsers>> getAllUsers() {
        List<OurUsers> ourUsers = authService.getAllUsers();
        return new ResponseEntity<>(ourUsers, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<OurUsers> getUserById(@PathVariable int id) {
        OurUsers user = authService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signin")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ReqRes> signIn(@RequestBody @Validated SigninReqRes signinReqRes) {
        ReqRes response = authService.signIn(signinReqRes);
        return response.getStatusCode() == 200 ?
                ResponseEntity.ok(response) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
