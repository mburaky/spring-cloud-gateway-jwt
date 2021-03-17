package com.justmop.jwtdemo.services;

import com.justmop.jwtdemo.entity.Client;
import com.justmop.jwtdemo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService {

    @Autowired
    private RedisTemplate<String, String> sessionRedis;

    @Autowired
    private ClientRepository clientRepository;

    public Mono<User> findByUsername(String phoneNumber) {
        String otp = "jwt";
        Optional<Client> client = clientRepository.findOneByPhone(phoneNumber);
        return client.map(value -> Mono.just(new User(value.getPhone(), otp, new ArrayList<>())))
                .orElseGet(Mono::empty);
    }
}
