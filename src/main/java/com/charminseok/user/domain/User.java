package com.charminseok.user.domain;

import com.charminseok.user.dto.InsertUserDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public User(InsertUserDto insertUserDto){
        this.email = insertUserDto.getEmail();
        this.password = insertUserDto.getPassword();
        this.name = insertUserDto.getName();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
