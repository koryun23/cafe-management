package com.cafe.facade.impl.user;

import com.cafe.dto.request.UserRegistrationRequestDto;
import com.cafe.dto.response.UserListRetrievalResponseDto;
import com.cafe.dto.response.UserRegistrationResponseDto;
import com.cafe.dto.response.error.ErrorUserRegistrationResponseDto;
import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;
import com.cafe.facade.core.user.UserFacade;
import com.cafe.mapper.user.UserCreationParamsMapper;
import com.cafe.mapper.user.UserRegistrationResponseDtoMapper;
import com.cafe.service.core.user.UserCreationParams;
import com.cafe.service.core.user.UserRoleCreationParams;
import com.cafe.service.core.user.UserRoleService;
import com.cafe.service.core.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserFacadeImplTest {
    private UserFacade testSubject;

    @Mock
    private UserService userService;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private UserCreationParamsMapper userCreationParamsMapper;

    @Mock
    private UserRegistrationResponseDtoMapper userRegistrationResponseDtoMapper;

    @BeforeEach
    public void init() {
        testSubject = new UserFacadeImpl(
                userService,
                userRoleService,
                userCreationParamsMapper,
                userRegistrationResponseDtoMapper
        );
    }

    @Test
    public void testRegisterUserWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.registerUser(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testRegisterUserWithTakenCredentials() {
        Mockito.when(userService.existsByPasswordOrUsername("pwd11", "john11")).thenReturn(true);
        UserRegistrationResponseDto response = testSubject.registerUser(new UserRegistrationRequestDto(
                "john11",
                "pwd11",
                "John",
                "Smith",
                List.of(UserRoleType.MANAGER))
        );
        Assertions.assertThat(response).isExactlyInstanceOf(ErrorUserRegistrationResponseDto.class);

        Mockito.verify(userService).existsByPasswordOrUsername("pwd11", "john11");
        Mockito.verifyNoMoreInteractions(userService, userRoleService, userCreationParamsMapper, userRegistrationResponseDtoMapper);
    }

    @Test
    public void testRegisterUser() {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "john11", "pwd11", "John", "Smith", List.of(UserRoleType.MANAGER)
        );
        UserCreationParams userCreationParams = new UserCreationParams(
                "John", "Smith", "john11", "pwd11", LocalDateTime.MAX
        );
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRoleCreationParams userRoleCreationParams = new UserRoleCreationParams(1L, UserRoleType.MANAGER);

        UserRole userRole = new UserRole(user, UserRoleType.MANAGER);
        userRole.setId(1L);

        UserRegistrationResponseDto responseDto = new UserRegistrationResponseDto(
                "john11", "pwd11", "John", "Smith", List.of(UserRoleType.MANAGER), LocalDateTime.MAX, HttpStatus.OK
        );

        Mockito.when(userService.existsByPasswordOrUsername("pwd11", "john11")).thenReturn(false);
        Mockito.when(userCreationParamsMapper.apply(requestDto)).thenReturn(userCreationParams);
        Mockito.when(userService.create(userCreationParams)).thenReturn(user);
        Mockito.when(userRoleService.create(userRoleCreationParams)).thenReturn(userRole);
        Mockito.when(userRegistrationResponseDtoMapper.apply(user)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.registerUser(requestDto)).isEqualTo(responseDto);

        Mockito.verify(userService).existsByPasswordOrUsername("pwd11", "john11");
        Mockito.verify(userCreationParamsMapper).apply(requestDto);
        Mockito.verify(userService).create(userCreationParams);
        Mockito.verify(userRoleService).create(userRoleCreationParams);
        Mockito.verify(userRegistrationResponseDtoMapper).apply(user);
        Mockito.verifyNoMoreInteractions(userService, userRoleService, userRegistrationResponseDtoMapper);
    }

    @Test
    public void testGetAll() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);
        UserRegistrationResponseDto registrationResponseDto = new UserRegistrationResponseDto(
                "john11", "pwd11", "John", "Smith", List.of(UserRoleType.MANAGER), LocalDateTime.MAX, HttpStatus.OK
        );
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));
        Mockito.when(userRegistrationResponseDtoMapper.apply(user)).thenReturn(registrationResponseDto);

        Assertions.assertThat(testSubject.getAll()).isEqualTo(new UserListRetrievalResponseDto(List.of(registrationResponseDto), HttpStatus.OK));

        Mockito.verify(userService).getAllUsers();
        Mockito.verify(userRegistrationResponseDtoMapper).apply(user);
        Mockito.verifyNoMoreInteractions(userService, userRegistrationResponseDtoMapper);
    }
}