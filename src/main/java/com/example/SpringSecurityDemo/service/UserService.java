package com.example.SpringSecurityDemo.service;

import com.example.SpringSecurityDemo.dto.LoginResponseDto;
import com.example.SpringSecurityDemo.dto.RegisterResponseDto;
import com.example.SpringSecurityDemo.model.AuthProvidertype;
import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.repo.UserRepo;
import com.example.SpringSecurityDemo.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthUtils authUtils;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public RegisterResponseDto saveUser(Users user) {
        Users users = signUpInternal(user, AuthProvidertype.EMAIL, null);
        return new RegisterResponseDto(users.getId(), users.getUsername());

    }

    public Users signUpInternal(Users user, AuthProvidertype authProvidertype, String providerId) {
        Users existingUser = repo.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("user already exists");

        }
        user = Users.builder()
                .username(user.getUsername())
                .providerId(providerId)
                .providerType(authProvidertype)
                .build();
        if (authProvidertype == AuthProvidertype.EMAIL) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repo.save(user);
    }

    public List<Users> getAllUsersList() {
        return repo.findAll();
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2Login(OAuth2User oAuth2User, String registrationId) {

        //fetch provider type from provider id
        AuthProvidertype providerType = getProviderIDByRegistraionId(registrationId);

        String providerid = determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        Users users = repo.findByProviderIdAndProviderType(providerid, providerType);

        String userEmail = oAuth2User.getAttribute("email");

        Users usersByEmail = repo.findByUsername(userEmail);

        if (users == null & usersByEmail == null) {
            String username = determineUserNameFromOAuth2user(oAuth2User, registrationId, providerid);
            users = signUpInternal(new Users(username), providerType, providerid);
        } else if (users != null) {
            if (userEmail != null && !userEmail.isBlank() && !userEmail.equals(users.getUsername())) {
                users.setUsername(userEmail);
                repo.save(users);
            }

        } else {
            throw new BadCredentialsException("this email already register with " + usersByEmail.getProviderType());

        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(users.getId(), authUtils.generateAccessToken(users));
        return ResponseEntity.ok(loginResponseDto);
    }

    private AuthProvidertype getProviderIDByRegistraionId(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> AuthProvidertype.GOOGLE;
            case "github" -> AuthProvidertype.GITHUB;
            default -> throw new IllegalArgumentException("Unsuported auth provider " + registrationId);
        };

    }

    private String determineProviderIdFromOAuth2User(OAuth2User oAuth2User, String regId) {
        String providerid = switch (regId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                throw new IllegalArgumentException("Unsuppoerted OAuth 2 provider");
            }
        };

        if (providerid == null || providerid.isBlank()) {
            throw new IllegalArgumentException("Unable to determine provider id ");
        }
        return providerid;
    }

    private String determineUserNameFromOAuth2user(OAuth2User oAuth2User, String registrationId, String providerId) {
        String userEmail = oAuth2User.getAttribute("email");
        if (userEmail != null && !userEmail.isBlank()) {
            return userEmail;
        }
        return switch (registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }
}
