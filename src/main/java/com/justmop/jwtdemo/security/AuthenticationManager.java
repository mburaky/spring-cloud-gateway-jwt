package com.justmop.jwtdemo.security;

import com.justmop.jwtdemo.security.jwt.JwtUtil;
import com.justmop.jwtdemo.services.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        //String username = jwtUtil.getUsernameFromToken(authToken);
        //Mono<User> userDetails = myUserDetailsService.findByUsername(username);

        try {
            if (!jwtUtil.validateToken(authToken)) {
                return Mono.empty();
            }
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            // List<String> rolesMap = claims.get("role", List.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            /*for (String rolemap : rolesMap) {
                authorities.add(new SimpleGrantedAuthority(rolemap));
            }*/
            return Mono.just(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}