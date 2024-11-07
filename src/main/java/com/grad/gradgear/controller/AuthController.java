package com.grad.gradgear.controller;

import com.grad.gradgear.dto.ReqRes;
import com.grad.gradgear.entity.Form;
import com.grad.gradgear.entity.OurUsers;
import com.grad.gradgear.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody @Validated ReqRes signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @GetMapping("/signup")
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
    public ResponseEntity<ReqRes> signIn(@RequestBody @Validated ReqRes signInRequest) {
        ReqRes response = authService.signIn(signInRequest); // Get the response as an object
        if (response.getStatusCode() == 200) {
            return ResponseEntity.ok(response);  // Return 200 status with the response object
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Return 500 error with the response object
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
