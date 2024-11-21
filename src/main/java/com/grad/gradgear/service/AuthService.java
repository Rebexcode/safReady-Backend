package com.grad.gradgear.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grad.gradgear.dto.ReqRes;
import com.grad.gradgear.dto.SigninReqRes;
import com.grad.gradgear.dto.SignupReqRes;
import com.grad.gradgear.entity.Form;
import com.grad.gradgear.entity.OurUsers;
import com.grad.gradgear.repository.OurUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(SignupReqRes signupReqRes){
        ReqRes resp = new ReqRes();
        try {
            OurUsers ourUsers = new OurUsers();
            ourUsers.setFirstname(signupReqRes.getFirstname());
            ourUsers.setLastname(signupReqRes.getLastname());
            ourUsers.setEmail(signupReqRes.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(signupReqRes.getPassword()));
            ourUsers.setConfirmPassword(passwordEncoder.encode(signupReqRes.getConfirmPassword()));
            ourUsers.setRole(signupReqRes.getRole());
            OurUsers ourUserResult = ourUserRepo.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId() > 0) {
                var jwt = jwtUtils.generateToken(ourUserResult);
                var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), ourUserResult);

                resp.setOurUsers(ourUserResult);
                resp.setStatusCode(200);
                resp.setToken(jwt);
                resp.setRefreshToken(refreshToken);
                resp.setExpirationTime("24Hr");
                resp.setMessage("Successfully Signed In");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(SigninReqRes signinReqRes) {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinReqRes.getEmail(), signinReqRes.getPassword())
            );

            var user = ourUserRepo.findByEmail(signinReqRes.getEmail())
                    .orElseThrow(() -> new Exception("User not found."));
            logger.info("USER IS: " + user);

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
            response.setEmail(user.getEmail());
            response.setName(user.getFirstname() + " " + user.getLastname());
            response.setRole(user.getRole());

            return response;
        } catch (Exception e) {
            logger.error("Sign-in failed: " + e.getMessage(), e);
            response.setStatusCode(500);
            response.setMessage("Sign-in failed. Please check your credentials.");
            return response;
        }
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest){
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        OurUsers users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        } else {
            response.setStatusCode(500); // Only if there's an issue
        }
        return response;
    }

    public List<OurUsers> getAllUsers() {
        return ourUserRepo.findAll();
    }

    public OurUsers getUserById(int id) {
        Optional<OurUsers> user = ourUserRepo.findById(id);
        return user.orElse(null); // Returns null if user is not found
    }
}
