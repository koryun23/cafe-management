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
import com.cafe.service.core.user.UserRoleCreationParams;
import com.cafe.service.core.user.UserRoleService;
import com.cafe.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

@Component
public class UserFacadeImpl implements UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class);
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserCreationParamsMapper userCreationParamsMapper;
    private final UserRegistrationResponseDtoMapper userRegistrationResponseDtoMapper;

    public UserFacadeImpl(UserService userService,
                          UserRoleService userRoleService,
                          UserCreationParamsMapper userCreationParamsMapper,
                          UserRegistrationResponseDtoMapper userRegistrationResponseDtoMapper) {
        Assert.notNull(userService, "user service should not be null");
        Assert.notNull(userRoleService, "user role service should not be null");
        Assert.notNull(userCreationParamsMapper, "user creation params mapper should not be null");
        Assert.notNull(userRegistrationResponseDtoMapper, "user registration response dto mapper should not be null");
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userCreationParamsMapper = userCreationParamsMapper;
        this.userRegistrationResponseDtoMapper = userRegistrationResponseDtoMapper;
    }

    @Override
    public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto dto) {
        Assert.notNull(dto, "User registration request dto should not be null");
        LOGGER.info("Registering a new user according to the user registration request dto - {}", dto);
        if(userService.existsByPasswordOrUsername(dto.getPassword(), dto.getUsername())) {
            return new ErrorUserRegistrationResponseDto(
                    List.of("Cannot register as on of the given credentials is already taken"),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        User user = userService.create(userCreationParamsMapper.apply(dto));

        List<UserRole> userRoleList = new LinkedList<>();

        for(UserRoleType roleType : dto.getRoleList()) {
            UserRole userRole = userRoleService.create(new UserRoleCreationParams(user.getId(), roleType));
            userRoleList.add(userRole);
        }

        user.setUserRoleList(userRoleList);

        UserRegistrationResponseDto responseDto = userRegistrationResponseDtoMapper.apply(user);
        LOGGER.info("Successfully registered a new user according to the user registration request dto - {}, result - {}", dto, responseDto);
        return responseDto;
    }

    @Override
    public UserListRetrievalResponseDto getAll() {
        LOGGER.info("Retrieving all registered users");
        List<User> allUsers = userService.getAllUsers();
        List<UserRegistrationResponseDto> allUserDtos = new LinkedList<>();
        for(User user : allUsers) {
            allUserDtos.add(userRegistrationResponseDtoMapper.apply(user));
        }
        UserListRetrievalResponseDto responseDto = new UserListRetrievalResponseDto(allUserDtos, HttpStatus.OK);
        LOGGER.info("Successfully retrieved all registered users, result - {}", responseDto);
        return responseDto;
    }
}
