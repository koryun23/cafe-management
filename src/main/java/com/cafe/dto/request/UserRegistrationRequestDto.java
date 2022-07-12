package com.cafe.dto.request;

import com.cafe.entity.user.UserRoleType;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class UserRegistrationRequestDto {

    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private List<UserRoleType> roleList;

    public UserRegistrationRequestDto(String username,
                                      String password,
                                      String firstName,
                                      String secondName,
                                      List<UserRoleType> roleList) {
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setSecondName(secondName);
        setRoleList(roleList);
    }

    public UserRegistrationRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Assert.notNull(username, "username should not be null");
        Assert.hasText(username, "Username should not be empty");
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        Assert.notNull(firstName, "First name should not be null");
        Assert.hasText(firstName, "First name should not be empty");
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        Assert.notNull(secondName, "Second name should not be null");
        Assert.hasText(secondName, "Second name should not be empty");
        this.secondName = secondName;
    }

    public List<UserRoleType> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<UserRoleType> roleList) {
        Assert.notNull(roleList, "role list should not be null");
        this.roleList = roleList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Assert.notNull(password, "Password should not be null");
        Assert.hasText(password, "Password should not be empty");
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationRequestDto that = (UserRegistrationRequestDto) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName) &&
                Objects.equals(roleList, that.roleList) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, secondName, roleList, password);
    }

    @Override
    public String toString() {
        return "UserRegistrationRequestDto{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", roleList=" + roleList +
                '}';
    }
}
