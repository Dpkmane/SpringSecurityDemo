package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.dto.LoginResponseDto;
import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private  AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody Users user){

        return ResponseEntity.ok(authService.login(user));

    }
}
