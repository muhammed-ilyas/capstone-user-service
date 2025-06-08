package com.aim.capstoneuserservice.services;

import com.aim.capstoneuserservice.models.Token;
import com.aim.capstoneuserservice.models.User;

public interface UserService {
    User signup(String name, String email, String password);
    Token login(String email, String password);
    Token logout(String tokenValue);
    Token validateToken(String tokenValue);
}
