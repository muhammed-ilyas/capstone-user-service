package com.aim.capstoneuserservice.dtos;

import com.aim.capstoneuserservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String password;

    public static UserDTO from(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
