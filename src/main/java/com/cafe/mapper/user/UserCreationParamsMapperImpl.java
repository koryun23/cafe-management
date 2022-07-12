package com.cafe.mapper.user;

import com.cafe.dto.request.UserRegistrationRequestDto;
import com.cafe.service.core.user.UserCreationParams;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserCreationParamsMapperImpl implements UserCreationParamsMapper{
    @Override
    public UserCreationParams apply(UserRegistrationRequestDto dto) {
        return new UserCreationParams(
                dto.getFirstName(),
                dto.getSecondName(),
                dto.getUsername(),
                dto.getPassword(),
                LocalDateTime.now()
        );
    }
}
