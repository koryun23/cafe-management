package com.cafe.mapper.user;

import com.cafe.dto.request.UserRegistrationRequestDto;
import com.cafe.service.core.user.UserCreationParams;

import java.util.function.Function;

public interface UserCreationParamsMapper extends Function<UserRegistrationRequestDto, UserCreationParams> {
    UserCreationParams apply(UserRegistrationRequestDto user);
}
