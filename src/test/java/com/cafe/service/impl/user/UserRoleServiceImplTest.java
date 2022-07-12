package com.cafe.service.impl.user;

import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;
import com.cafe.repository.UserRoleRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    private UserRoleService testSubject;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        testSubject = new UserRoleServiceImpl(userRoleRepository, userService);
    }

    @Test
    public void testGetRoleTypeByUsernameWhenUserIsNotFound() {
        Mockito.when(userRoleRepository.findByUserUsername("john11")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getRoleType("john11")).isExactlyInstanceOf(UserNotFoundException.class);
        Mockito.verify(userRoleRepository).findByUserUsername("john11");
        Mockito.verifyNoMoreInteractions(userRoleRepository, userService);
    }

    @Test
    public void testGetRoleTypeByUsernameWhenUserIsFound() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        UserRole userRole = new UserRole(user, UserRoleType.MANAGER);
        user.setUserRoleList(List.of(userRole));
        Mockito.when(userRoleRepository.findByUserUsername("john11")).thenReturn(Optional.of(userRole));
        Assertions.assertThat(testSubject.getRoleType("john11")).isEqualTo(UserRoleType.MANAGER);
        Mockito.verifyNoMoreInteractions(userRoleRepository, userService);
    }

    @Test
    public void testCreate() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);

        UserRole savedUserRole = new UserRole(user, UserRoleType.WAITER);
        savedUserRole.setId(1L);

        Mockito.when(userRoleRepository.save(userRole)).thenReturn(savedUserRole);
        Mockito.when(userService.getById(1L)).thenReturn(user);

        Assertions.assertThat(testSubject.create(new UserRoleCreationParams(
                1L, UserRoleType.WAITER
        ))).isEqualTo(savedUserRole);

        Mockito.verify(userRoleRepository).save(userRole);
        Mockito.verify(userService).getById(1L);
        Mockito.verifyNoMoreInteractions(userRoleRepository, userService);
    }

    @Test
    public void testCreateWhenParamsObjectIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetRoleTypeByUsernameWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getRoleType(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}