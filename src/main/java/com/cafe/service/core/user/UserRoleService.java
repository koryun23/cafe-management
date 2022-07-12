package com.cafe.service.core.user;

import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;

public interface UserRoleService {
    UserRole create(UserRoleCreationParams params);

    UserRoleType getRoleType(String username);
}
