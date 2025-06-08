package com.aim.capstoneuserservice.services;

import com.aim.capstoneuserservice.models.Token;
import com.aim.capstoneuserservice.models.User;
import com.aim.capstoneuserservice.repositories.TokenRepository;
import com.aim.capstoneuserservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bcryptPasswordEncoder,
                           TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bcryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signup(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            //throw exception or redirect to login or both
            return null;
        }

        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(UUID.randomUUID().toString());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date date = calendar.getTime();

        token.setExpiryAt(date);

        return tokenRepository.save(token);
    }

    @Override
    public Token logout(String tokenValue) {
        Token token = validateToken(tokenValue);
        if (token == null) {
            return null; // Token not found
        }
        token.setDeleted(true);
        return tokenRepository.save(token);
    }

    @Override
    public Token validateToken(String tokenValue) {
        /*
         * 1. Should exist in DB
         * 2. Should not be deleted
         * 3. Should not have expired
         * */

        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeletedAndExpiryAtGreaterThan(tokenValue,
                false, new Date());

        return optionalToken.orElse(null);

    }
}
