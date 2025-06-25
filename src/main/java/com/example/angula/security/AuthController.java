package com.example.angula.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.angula.security.jwt.JwtUtils;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/auth")
    public String authenticate(@RequestBody AuthData authData){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
            authData.getUsername(), authData.getPassword())
        );
        return jwtUtils.generateToken(authData.getUsername());
    }
}