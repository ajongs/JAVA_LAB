package com.example.oauth.controller;

import com.example.oauth.domain.OauthToken;
import com.example.oauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

    @Autowired
    LoginService loginService;

    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity auth(@RequestParam String code){
        OauthToken oauthToken = loginService.getOauthToken(code).getBody();
        return loginService.getProfile(oauthToken);
    }

    @GetMapping("/auth")
    public ResponseEntity getToken(@RequestParam String code){
        return loginService.getOauthToken(code);
    }

}
