package com.example.oauth.service;


import org.springframework.http.ResponseEntity;

public interface LoginService {
    public ResponseEntity<String> auth(String code);
}
