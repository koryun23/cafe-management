package com.cafe.dto.response;

import com.cafe.entity.user.UserRoleType;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class UserRegistrationResponseDto {

    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private List<UserRoleType> roleList;
    private LocalDateTime registeredAt;
    private HttpStatus httpStatus;

    public UserRegistrationResponseDto(String username,
                                       String password,
                                       String firstName,
                                       String secondName,
                                       List<UserRoleType> roleList,
                                       LocalDateTime registeredAt,
                                       HttpStatus httpStatus) {
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setSecondName(secondName);
        setRoleList(roleList);
        setRegisteredAt(registeredAt);
        setHttpStatus(httpStatus);
    }

    public UserRegistrationResponseDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Assert.notNull(username, "username should not be null");
        Assert.hasText(username, "username should not be empty");
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        Assert.notNull(firstName, "First Name should not be null");
        Assert.hasText(firstName, "First name should not be empty");
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        Assert.notNull(firstName, "Second Name should not be null");
        Assert.hasText(firstName, "Second name should not be empty");
        this.secondName = secondName;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        Assert.notNull(registeredAt, "registration date should not be null");
        this.registeredAt = registeredAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Assert.notNull(password, "password should not be null");
        Assert.hasText(password, "password should not be empty");
        this.password = password;
    }

    public List<UserRoleType> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<UserRoleType> roleList) {
        Assert.notNull(roleList, "User role type list should not be null");
        this.roleList = roleList;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        Assert.notNull(httpStatus, "http status should not be null");
        this.httpStatus = httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationResponseDto that = (UserRegistrationResponseDto) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName) &&
                Objects.equals(registeredAt, that.registeredAt) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, secondName, registeredAt, httpStatus);
    }

    @Override
    public String toString() {
        return "UserRegistrationResponseDto{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", password=" + password +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
