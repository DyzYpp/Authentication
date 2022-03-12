package com.dyz.authentication.entity.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @ClassName AuthUser
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description
 */
public class AuthUser implements UserDetails {

    private static final long serialVersionUID = 2228056662409903301L;

    private String userName;

    private String passWord;

    private Collection<? extends GrantedAuthority> authorities;

    public AuthUser() {
    }

    public AuthUser(String userName, String passWord, Collection<? extends GrantedAuthority> authorities) {
        this.userName = userName;
        this.passWord = passWord;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
