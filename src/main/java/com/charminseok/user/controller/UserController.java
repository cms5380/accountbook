package com.charminseok.user.controller;

import com.charminseok.user.domain.User;
import com.charminseok.user.dto.*;
import com.charminseok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> insertUser(@RequestBody InsertUserDto insertUserDto) {
        try {
            String userEmail = userService.insertUser(insertUserDto);
            return new ResponseEntity<>(userEmail, HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {

        Optional<User> userByEmail = userService.getUserByEmail(email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    @PostMapping(value="/auth/login", produces=MediaType.APPLICATION_JSON_VALUE)
    public JwtResponseDto login(@RequestBody JwtRequestDto jwtRequestDto) {
        try {
            return userService.login(jwtRequestDto);
        } catch (Exception e) {
            return new JwtResponseDto(e.getMessage());
        }
    }

    @PostMapping(value="/auth/logout", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        if(userService.logout(logoutRequestDto)){
            return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그아웃 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
