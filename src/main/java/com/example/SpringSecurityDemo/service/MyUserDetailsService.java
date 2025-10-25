package com.example.SpringSecurityDemo.service;

import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        System.out.println("user in load by username"+user);
        if(user == null){
            throw  new UsernameNotFoundException("User Not Foud");
        }

        return user;
    }
}
