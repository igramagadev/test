package com.example.demo.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record AppUserPrincipal(Integer id, String email, String password, boolean blocked, Collection<? extends GrantedAuthority> authorities) implements UserDetails {
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return !blocked; }
    @Override public boolean isAccountNonLocked() { return !blocked; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return !blocked; }
}
