package com.justmop.jwtdemo.models.security;

import com.justmop.jwtdemo.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(){}

    public MyUserDetails(Client client, String otp) {
        this.username = client.getPhone();
        this.password = otp;
        this.enabled = client.getStatus() == 1;
        this.authorities = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() { return this.username; }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
