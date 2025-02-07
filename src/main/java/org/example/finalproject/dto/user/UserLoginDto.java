package org.example.finalproject.dto;

import lombok.Data;

public class UserLoginDto extends UserDto {
    public UserLoginDto(String username, String password) {
        super(username, password);
    }
}
