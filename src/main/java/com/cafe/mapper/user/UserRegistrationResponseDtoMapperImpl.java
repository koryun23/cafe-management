package com.cafe.mapper.user;

import com.cafe.dto.response.UserRegistrationResponseDto;
import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserRegistrationResponseDtoMapperImpl implements UserRegistrationResponseDtoMapper {
    @Override
    public UserRegistrationResponseDto apply(User user) {
        return new UserRegistrationResponseDto(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getSecondName(),
                user.getUserRoleList().stream().map(UserRole::getUserRoleType).collect(Collectors.toList()),
                user.getCreatedAt(),
                HttpStatus.OK
        );
    }
}
