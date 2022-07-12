package com.cafe.service.impl.user;

import com.cafe.entity.user.User;
import com.cafe.repository.UserRepository;
import com.cafe.service.core.user.UserCreationParams;
import com.cafe.service.core.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService testSubject;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        testSubject = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void testFindByIdWhenUserIsNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<User> userOptional = testSubject.findById(1L);
        Assertions.assertThat(userOptional).isEqualTo(Optional.empty());
        Mockito.verify(userRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testFindByIdWhenUserIsFound() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(user));
        Mockito.verify(userRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testGetByIdWhenUserIsNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getById(1L)).isExactlyInstanceOf(UserNotFoundException.class);
        Mockito.verify(userRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testGetByIdWhenUserIsFound() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Assertions.assertThat(testSubject.getById(1L)).isEqualTo(user);
        Mockito.verify(userRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testFindByUsernameWhenUserIsNotFound() {
        Mockito.when(userRepository.findByUsername("john11")).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findByUsername("john11")).isEqualTo(Optional.empty());
        Mockito.verify(userRepository).findByUsername("john11");
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testFindByUsernameWhenUserIsFound() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        Mockito.when(userRepository.findByUsername("john11")).thenReturn(Optional.of(user));
        Assertions.assertThat(testSubject.findByUsername("john11")).isEqualTo(Optional.of(user));
        Mockito.verify(userRepository).findByUsername("john11");
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testGetByUsernameWhenUserIsNotFound() {
        Mockito.when(userRepository.findByUsername("john11")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername("john11")).isExactlyInstanceOf(UserNotFoundException.class);
        Mockito.verify(userRepository).findByUsername("john11");
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testGetByUsernameWhenUserIsFound() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        Mockito.when(userRepository.findByUsername("john11")).thenReturn(Optional.of(user));
        Assertions.assertThat(testSubject.getByUsername("john11")).isEqualTo(user);
        Mockito.verify(userRepository).findByUsername("john11");
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        User user2 = new User("Mary", "Smith", "mary21", "pwd21", LocalDateTime.MAX);
        User user3 = new User("Emily", "Williams", "emily31", "pwd31", LocalDateTime.MAX);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        Assertions.assertThat(testSubject.getAllUsers()).isEqualTo(List.of(user1, user2, user3));
        Mockito.verify(userRepository).findAll();
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testExistsByPasswordOrUsernameWhenPaswordIsTakenAndUsernameIsNotTaken() {
        User user1 = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1));
        Mockito.when(passwordEncoder.matches("pwd11", "pwd11")).thenReturn(true);
        Assertions.assertThat(testSubject.existsByPasswordOrUsername("pwd11", "username")).isEqualTo(true);
        Mockito.verify(userRepository).findAll();
        Mockito.verify(passwordEncoder).matches("pwd11", "pwd11");
        Mockito.verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testCreate() {
        User user = new User("John", "Smith", "john11", "encode", LocalDateTime.MAX);

        User savedUser = new User("John", "Smith", "john11", "encode", LocalDateTime.MAX);
        savedUser.setId(1L);

        Mockito.when(passwordEncoder.encode("pwd11")).thenReturn("encode");
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        Assertions.assertThat(testSubject.create(new UserCreationParams(
                "John", "Smith", "john11", "pwd11", LocalDateTime.MAX
        ))).isEqualTo(savedUser);

        Mockito.verify(passwordEncoder).encode("pwd11");
        Mockito.verify(userRepository).save(user);
        Mockito.verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

    @Test
    public void testCreateWhenParamsObjectIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getById(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    };

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExistsByPasswordOrUsernameWhenPasswordIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.existsByPasswordOrUsername(null, ""))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExistsByPasswordOrUsernameWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.existsByPasswordOrUsername("", null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByUsernameWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByUsernameWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findByUsername(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

}