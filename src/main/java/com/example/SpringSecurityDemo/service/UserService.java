package com.example.SpringSecurityDemo.service;

import com.example.SpringSecurityDemo.dto.RegisterResponseDto;
import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

     @Autowired
     private UserRepo repo;

     private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public RegisterResponseDto saveUser(Users user){

        Users existingUser = repo.findByUsername(user.getUsername());
        if(existingUser != null){
            throw  new IllegalArgumentException("user already exists");

        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = repo.save(user);

        return new RegisterResponseDto(user.getId(),user.getUsername());

    }
}
