package com.example.SpringSecurityDemo.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginResponseDto {

    private int id;
    private String username;
    private  String jwtToken;

    public LoginResponseDto(int id , String username , String jwtToken){
        this.id=id;
        this.username=username;
        this.jwtToken=jwtToken;
    }

    public  LoginResponseDto(){}

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
