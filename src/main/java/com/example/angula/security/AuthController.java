package com.example.angula.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.angula.security.jwt.JwtUtil;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("/auth")
    public String authenticate(@RequestBody AuthData authData){
        if (authData.getUsername()==null || authData.getPassword() == null)
            throw new BadCredentialsException("null username or password");
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
            authData.getUsername(), authData.getPassword())
        );
        return jwtUtils.generateToken(authData.getUsername());
    }
}