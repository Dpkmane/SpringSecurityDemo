package com.example.SpringSecurityDemo.utils;

import com.example.SpringSecurityDemo.model.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtils {

    @Value("${jwt.secretkey}")
    //we can use base64 encoded key
    private String jwtSecretKey;

    private SecretKey getSigningKey(){
        //If your key is not base64-encoded you can do that via (generally not recommended)
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(Users user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getSigningKey())
                .compact();
    }
}
