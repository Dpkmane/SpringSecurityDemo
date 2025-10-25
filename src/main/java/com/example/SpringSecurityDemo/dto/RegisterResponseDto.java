package com.example.SpringSecurityDemo.dto;

import org.springframework.stereotype.Component;

@Component
public class RegisterResponseDto {

    private int id;
    private String userName;

    public RegisterResponseDto(int id , String userName){
        this.id=id;
        this.userName=userName;
    }
    public RegisterResponseDto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
