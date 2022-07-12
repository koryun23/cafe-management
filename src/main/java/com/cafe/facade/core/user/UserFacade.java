package com.cafe.facade.core.user;

import com.cafe.dto.request.UserRegistrationRequestDto;
import com.cafe.dto.response.UserListRetrievalResponseDto;
import com.cafe.dto.response.UserRegistrationResponseDto;

public interface UserFacade {

    UserRegistrationResponseDto registerUser(UserRegistrationRequestDto dto);

    UserListRetrievalResponseDto getAll();
}
