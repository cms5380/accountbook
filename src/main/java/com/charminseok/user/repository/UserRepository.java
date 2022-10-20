package com.charminseok.user.repository;

import com.charminseok.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    User insertUser(User user);
}
