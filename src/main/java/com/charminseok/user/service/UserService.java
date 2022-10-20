package com.charminseok.user.service;

import com.charminseok.user.dto.LogoutRequestDto;
import com.charminseok.user.security.UserDetailsImpl;
import com.charminseok.user.domain.User;
import com.charminseok.user.dto.InsertUserDto;
import com.charminseok.user.dto.JwtRequestDto;
import com.charminseok.user.dto.JwtResponseDto;
import com.charminseok.user.repository.UserRepository;
import com.charminseok.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public String insertUser(InsertUserDto insertUserDto){
        Optional<User> userByEmail = getUserByEmail(insertUserDto.getEmail());

        if(userByEmail.isPresent()){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        User user = new User(insertUserDto);
        user.encryptPassword(passwordEncoder);

        userRepository.insertUser(user);
        return user.getEmail();
    }

    public JwtResponseDto login(JwtRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return createJwtToken(authentication);
    }

    private JwtResponseDto createJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }

    public Boolean logout(LogoutRequestDto logoutRequestDto) {
        Authentication authentication = jwtTokenProvider.getAuthentication(logoutRequestDto.getAccessToken());
        Long expireTime = jwtTokenProvider.getExpireTime(logoutRequestDto.getAccessToken());
        redisTemplate.opsForValue().set(logoutRequestDto.getAccessToken(), "logout", expireTime, TimeUnit.MILLISECONDS);

        return true;
    }
}
