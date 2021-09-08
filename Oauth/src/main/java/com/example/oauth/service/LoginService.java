package com.example.oauth.service;


import com.example.oauth.domain.OauthToken;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    public ResponseEntity<OauthToken> getOauthToken(String code);
    public ResponseEntity<String> getProfile(OauthToken oauthToken);
}
