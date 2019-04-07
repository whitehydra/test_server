package com.denis.test.server.service;


import org.springframework.stereotype.Service;

@Service
public class GetTokenServiceImpl implements GetTokenService {
    @Override
    public String getToken(String username, String password, UserService userService){
        if (username == null || password == null)
            return null;
        String token = username + password;
        return  token;
    }
}
