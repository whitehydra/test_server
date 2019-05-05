package com.denis.test.server.service;


import com.denis.test.server.other.Functions;
import org.springframework.stereotype.Service;

@Service
public class GetTokenServiceImpl implements GetTokenService {

    @Override
    public String getToken(String username, String password, UserService userService){
        if (username == null || password == null)
            return null;
        String token;
        try {
            token = Functions.generateHash(Functions.generateString() + username);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
