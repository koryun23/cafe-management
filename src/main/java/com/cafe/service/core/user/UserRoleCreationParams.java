package com.cafe.service.core.user;

import com.cafe.entity.user.UserRoleType;

import java.util.Objects;

public class UserRoleCreationParams {

    private Long userId;

    private UserRoleType userRoleType;

    public UserRoleCreationParams(Long userId, UserRoleType userRoleType) {
        this.userId = userId;
        this.userRoleType = userRoleType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleCreationParams that = (UserRoleCreationParams) o;
        return Objects.equals(userId, that.userId) && userRoleType == that.userRoleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userRoleType);
    }

    @Override
    public String toString() {
        return "UserRoleCreationParams{" +
                "userId=" + userId +
                ", userRoleType=" + userRoleType +
                '}';
    }
}
