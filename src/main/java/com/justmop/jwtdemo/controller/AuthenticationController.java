package com.justmop.jwtdemo.controller;

import com.justmop.jwtdemo.models.api.AuthenticationRequest;
import com.justmop.jwtdemo.models.api.AuthenticationResponse;
import com.justmop.jwtdemo.security.jwt.JwtUtil;
import com.justmop.jwtdemo.services.AuthenticationService;
import com.justmop.jwtdemo.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return userService.findByUsername(authenticationRequest.getPhone()).map((userDetails) -> {
            if (authenticationRequest.getOtp().equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
