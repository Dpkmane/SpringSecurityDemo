package com.example.SpringSecurityDemo.service;

import com.example.SpringSecurityDemo.model.Users;
import com.example.SpringSecurityDemo.repo.UserRepo;
import com.example.SpringSecurityDemo.utils.AuthUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
         try {


                if (header == null || !header.startsWith("Bearer")) {
                    filterChain.doFilter(request, response);
                    return;

                }
                String token = header.split("Bearer ")[1];

                String username = authUtils.getUserNameFromToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    Users user = userRepo.findByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
         } catch (Exception e) {
                handlerExceptionResolver.resolveException(request,response,null,e);
         }
    }
}
