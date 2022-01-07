package com.example.fleetmanagementsystem.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(final User user) {
        this.user = user;
    }

    public CustomUserDetails() {
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

        List<Authority> userAuthorities = null;

        if (user != null) {
            userAuthorities = (List<Authority>) user.getAuthorities();
        }

        if (userAuthorities != null) {
            for (Authority authority : userAuthorities) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }
        }

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

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
        return this.user.getEnabled();
    }

    @Override
    public String toString() {
        return "CustomUserDetails [user=" + user + "]";
    }
}