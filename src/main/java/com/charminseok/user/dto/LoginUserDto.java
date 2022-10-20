package com.charminseok.user.dto;

import lombok.Data;

@Data
public class LoginUserDto {
    private String name;
    private String password;
    private String email;
}
