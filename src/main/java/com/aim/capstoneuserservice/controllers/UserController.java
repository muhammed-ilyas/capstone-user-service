package com.aim.capstoneuserservice.controllers;

import com.aim.capstoneuserservice.dtos.LoginRequestDTO;
import com.aim.capstoneuserservice.dtos.LoginResponseDTO;
import com.aim.capstoneuserservice.dtos.LogoutRequestDTO;
import com.aim.capstoneuserservice.dtos.SignupRequestDTO;
import com.aim.capstoneuserservice.dtos.UserDTO;
import com.aim.capstoneuserservice.models.Token;
import com.aim.capstoneuserservice.models.User;
import com.aim.capstoneuserservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO signUp(@RequestBody SignupRequestDTO signupRequest) {
        User user = userService.signup(
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getPassword()
        );
        return UserDTO.from(user);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDto) {
        Token token = userService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());

        LoginResponseDTO loginResponseDto = new LoginResponseDTO();
        loginResponseDto.setTokenValue(token.getValue());
        return loginResponseDto;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO logoutRequestDto) {
        Token token = userService.logout(logoutRequestDto.getTokenValue());
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable("token") String token) {
        Token responseToken = userService.validateToken(token);
        ResponseEntity<Boolean> responseEntity;
        if (responseToken == null) {
            responseEntity = new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        } else {
            responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
        }
        return responseEntity;
    }
}
