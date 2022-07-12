package com.cafe.service.impl.user;

import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;
import com.cafe.repository.UserRoleRepository;
import com.cafe.service.core.user.UserRoleCreationParams;
import com.cafe.service.core.user.UserRoleService;
import com.cafe.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);
    private final UserRoleRepository userRoleRepository;
    private final UserService userService;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserService userService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public UserRole create(UserRoleCreationParams params) {
        Assert.notNull(params, "User Role Creation params should not be null");
        LOGGER.info("Creating a new user role according to user role creation params - {}", params);
        UserRole userRole = userRoleRepository.save(new UserRole(userService.getById(params.getUserId()), params.getUserRoleType()));
        LOGGER.info("Successfully created a new new user role according to the user role creation params - {}, result - {}", params, userRole);
        return userRole;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public UserRoleType getRoleType(String username) {
        Assert.notNull(username, "username should not be null");
        Assert.hasText(username, "username should not be empty");
        LOGGER.info("Retrieving the role type of a user with username '{}'", username);
        UserRoleType userRoleType = userRoleRepository.findByUserUsername(username).orElseThrow(() -> new UserNotFoundException(username)).getUserRoleType();
        LOGGER.info("Successfully retrieved the role type of a user with username '{}', result - {}", username, userRoleType);
        return userRoleType;
    } // tested
}
