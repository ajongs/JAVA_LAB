package com.example.oauth.serviceImpl;

import com.example.oauth.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginServiceImpl implements LoginService {
    @Value("${kakao.rest-api-key}")
    String REST_API_KEY;

    @Value("${kakao.redirect-uri}")
    String REDIRECT_URI;

    @Value("${kakao.client-secret}")
    String CLIENT_SECRET;

    private MultiValueMap<String, String> generateParams(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", REST_API_KEY);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);
        params.add("client_secret", CLIENT_SECRET);
        return params;
    }

    @Override
    public ResponseEntity<String> auth(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(generateParams(code), headers);

        return requestAuth(kakaoTokenRequest);
    }

    private ResponseEntity<String> requestAuth(HttpEntity request){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }
}
