package com.app.Memora.authentication.entities.dtos;

import com.app.Memora.authentication.entities.User;

public class LoginResponseDto {
    private String token;
    private User user;
    private long expiresIn;
    public String getToken() {
        return token;
    }
    public LoginResponseDto setToken(String token) {
        this.token = token;
        return this;
    }
    public long getExpiresIn() {
        return expiresIn;
    }
    public LoginResponseDto setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
    public User getUser() {
        return user;
    }

    public LoginResponseDto setUser(User user) {
        this.user = user;
        return this;
    }
}
