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

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private RedisTemplate<String, String> sessionRedis;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        // String otp = sessionRedis.opsForValue().get(phoneNumber);
        String otp = "jwt";

        Optional<Client> client = clientRepository.findOneByPhone(phoneNumber);
        if (client.isPresent()) {
            return new User(client.get().getPhone(), otp, new ArrayList<>());
        }

        throw new UsernameNotFoundException("User not found");
    }

}
