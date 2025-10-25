package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.model.Users;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {



    @GetMapping("/")
    public String home(){
        return "Welcom to HomePage";
    }

}
