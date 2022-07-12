package com.cafe.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class UserListRetrievalResponseDto {

    private List<UserRegistrationResponseDto> userList;
    private HttpStatus httpStatus;

    public UserListRetrievalResponseDto(List<UserRegistrationResponseDto> userList, HttpStatus httpStatus) {
        setUserList(userList);
        setHttpStatus(httpStatus);
    }

    public UserListRetrievalResponseDto() {
    }

    public List<UserRegistrationResponseDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserRegistrationResponseDto> userList) {
        Assert.notNull(userList, "user list should not be null");
        this.userList = userList;
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
        UserListRetrievalResponseDto that = (UserListRetrievalResponseDto) o;
        return Objects.equals(userList, that.userList) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userList, httpStatus);
    }

    @Override
    public String toString() {
        return "UserListRetrievalResponseDto{" +
                "userDtoList=" + userList +
                '}';
    }
}
