package com.example.SpringSecurityDemo.service;

import com.example.SpringSecurityDemo.dto.LoginResponseDto;
import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUtils authUtils;

    public LoginResponseDto login(Users user) {

     Authentication authenticate =   authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),user.getPassword()
                ));

      // authentication manager --> Authentication Provider --> DaoAuthentication Provider
        // -- fetch user details --> UserDetailsService --implemented by --> MyUserDetailsService

        // we get authenticated user

        Users users = (Users) authenticate.getPrincipal();
        String token = authUtils.generateAccessToken(users);
        return new LoginResponseDto(users.getId(),users.getUsername(),token);
    }
}
