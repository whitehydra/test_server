package com.denis.test.server.service;

public interface GetTokenService {
    String getToken(String username, String password, UserService service);
}
