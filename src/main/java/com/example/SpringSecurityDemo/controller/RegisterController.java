package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.dto.RegisterResponseDto;
import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController  {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody Users user){
        return ResponseEntity.ok(service.saveUser(user));
    }
}
