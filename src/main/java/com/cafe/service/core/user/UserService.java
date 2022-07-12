package com.cafe.service.core.user;

import com.cafe.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(UserCreationParams params);

    User getById(Long id);

    Optional<User> findById(Long id);

    boolean existsByPasswordOrUsername(String rawPassword, String username);

    User getByUsername(String username);

    List<User> getAllUsers();

    Optional<User> findByUsername(String username);
}
