package com.cafe.service.impl.user;

import com.cafe.entity.user.User;
import com.cafe.repository.UserRepository;
import com.cafe.repository.UserRoleRepository;
import com.cafe.service.core.user.UserCreationParams;
import com.cafe.service.core.user.UserService;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User create(UserCreationParams params) {
        LOGGER.info("Creating a new user according to the user creation params - {}", params);
        Assert.notNull(params, "User creation params should not be null");
        User user = userRepository.save(new User(
                params.getFirstName(),
                params.getSecondName(),
                params.getUsername(),
                passwordEncoder.encode(params.getPassword()),
                params.getCreatedAt()
        ));
        LOGGER.info("Successfully created a new user according to the user creation params - {}, result - {}", params, user);
        return user;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public User getById(Long id) {
        Assert.notNull(id, "user id should not be null");
        LOGGER.info("Retrieving a user having an id of {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        LOGGER.info("Successfully retrieved a user having an id of {}", id);
        return user;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        Assert.notNull(id, "user id should not be null");
        LOGGER.info("Retrieving an optional of a user having an id of {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional of a user having an id of {}", id);
        return userOptional;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public boolean existsByPasswordOrUsername(String rawPassword, String username) {
        Assert.notNull(rawPassword, "Raw password should not be null");
        Assert.hasText(rawPassword, "Raw password should not be empty");
        Assert.notNull(username, "Username should not be null");
        Assert.hasText(username, "Username should not be empty");
        LOGGER.info("Checking if a user with the given password exists");
        List<User> allUsers = userRepository.findAll();
        for(User user : allUsers) {
            if(passwordEncoder.matches(rawPassword, user.getPassword()) || username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public User getByUsername(String username) {
        Assert.notNull(username, "username should not be null");
        LOGGER.info("Retrieving a user having a username of '{}'", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        LOGGER.info("Successfully retrieved a user having a username of '{}', result - {}", username, user);
        return user;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Retrieving all registered users");
        List<User> allUsers = userRepository.findAll();
        LOGGER.info("Successfully retrieved all registered users, result - {}", allUsers);
        return allUsers;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        Assert.notNull(username, "Username should not be null");
        Assert.hasText(username, "Username should not be empty");
        LOGGER.info("Retrieving an optional of a user having a username of {}", username);
        Optional<User> userOptional = userRepository.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional of a user having a username of {}, result - {}", username, userOptional);
        return userOptional;
    } // tested
}
