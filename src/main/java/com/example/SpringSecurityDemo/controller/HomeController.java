package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.repo.UserRepo;
import com.example.SpringSecurityDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        return "Welcom to HomePage";
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){

        List<Users> usersList= userService.getAllUsersList();

        return  ResponseEntity.ok(usersList);
    }

}
