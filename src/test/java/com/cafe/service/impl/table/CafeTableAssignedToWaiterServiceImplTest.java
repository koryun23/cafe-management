package com.cafe.service.impl.table;

import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableAssignedToWaiter;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;
import com.cafe.repository.CafeTableAssignedToWaiterRepository;
import com.cafe.service.core.table.CafeTableAssignedToWaiterCreationParams;
import com.cafe.service.core.table.CafeTableAssignedToWaiterService;
import com.cafe.service.core.table.CafeTableService;
import com.cafe.service.core.user.UserService;
import com.cafe.service.impl.user.UserNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CafeTableAssignedToWaiterServiceImplTest {

    private CafeTableAssignedToWaiterService testSubject;

    @Mock
    private CafeTableAssignedToWaiterRepository cafeTableAssignedToWaiterRepository;

    @Mock
    private CafeTableService cafeTableService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        testSubject = new CafeTableAssignedToWaiterServiceImpl(
                cafeTableAssignedToWaiterRepository,
                cafeTableService,
                userService
        );
    }

    @Test
    public void testFindByIdWhenDoesNotExist() {
        Mockito.when(cafeTableAssignedToWaiterRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.empty());
        Mockito.verify(cafeTableAssignedToWaiterRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableAssignedToWaiterRepository, cafeTableService, userService);
    }

    @Test
    public void testFindByIdWhenExists() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("Mary", "Smith", "mary21", "pwd21", LocalDateTime.MAX);
        waiter.setUserRoleList(List.of(new UserRole(waiter, UserRoleType.WAITER)));
        waiter.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableAssignedToWaiterRepository.findById(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(cafeTableAssignedToWaiter));

        Mockito.verify(cafeTableAssignedToWaiterRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableAssignedToWaiterRepository, cafeTableService, userService);
    }

    @Test
    public void testGetByIdWhenDoesNotExist() {
        Mockito.when(cafeTableAssignedToWaiterRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getById(1L)).isExactlyInstanceOf(CafeTableAssignedToWaiterException.class);
        Mockito.verify(cafeTableAssignedToWaiterRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableAssignedToWaiterRepository, cafeTableService, userService);
    }

    @Test
    public void testGetByIdWhenExists() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("Mary", "Smith", "mary21", "pwd21", LocalDateTime.MAX);
        waiter.setUserRoleList(List.of(new UserRole(waiter, UserRoleType.WAITER)));
        waiter.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableAssignedToWaiterRepository.findById(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.getById(1L)).isEqualTo(cafeTableAssignedToWaiter);

        Mockito.verify(cafeTableAssignedToWaiterRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableAssignedToWaiterRepository, cafeTableService, userService);
    }

    @Test
    public void testFindByCafeTableIdWhenCafeTableDoesNotExist() {
        Mockito.when(cafeTableAssignedToWaiterRepository.findByCafeTableId(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findByCafeTableId(1L)).isEqualTo(Optional.empty());
        Mockito.verify(cafeTableAssignedToWaiterRepository).findByCafeTableId(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, cafeTableAssignedToWaiterRepository, userService);
    }

    @Test
    public void testFindByCafeTableIdWhenCafeTableExists() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("Mary", "Smith", "mary21", "pwd21", LocalDateTime.MAX);
        waiter.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableAssignedToWaiterRepository.findByCafeTableId(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.findByCafeTableId(1L)).isEqualTo(Optional.of(cafeTableAssignedToWaiter));

        Mockito.verify(cafeTableAssignedToWaiterRepository).findByCafeTableId(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, cafeTableAssignedToWaiterRepository, userService);
    }

    @Test
    public void testExistsByWaiterUsernameWhenResultIsFalse() {
        Mockito.when(cafeTableAssignedToWaiterRepository.existsByWaiterUsername("mary21")).thenReturn(false);
        Assertions.assertThat(testSubject.existsByWaiterUsername("mary21")).isEqualTo(false);
        Mockito.verify(cafeTableAssignedToWaiterRepository).existsByWaiterUsername("mary21");
        Mockito.verifyNoMoreInteractions(cafeTableService, cafeTableAssignedToWaiterRepository, userService);
    }

    @Test
    public void testExistsByWaiterUsernameWhenResultIsTrue() {
        Mockito.when(cafeTableAssignedToWaiterRepository.existsByWaiterUsername("mary21")).thenReturn(true);
        Assertions.assertThat(testSubject.existsByWaiterUsername("mary21")).isEqualTo(true);
        Mockito.verify(cafeTableAssignedToWaiterRepository).existsByWaiterUsername("mary21");
        Mockito.verifyNoMoreInteractions(cafeTableService, cafeTableAssignedToWaiterRepository, userService);
    }

    @Test
    public void testFindAllByWaiterUsername() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("Mary", "Smith", "mary21", "pwd21", LocalDateTime.MAX);
        waiter.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableAssignedToWaiterRepository.findAllByWaiterUsername("mary21"))
                .thenReturn(List.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.findAllByWaiterUsername("mary21")).isEqualTo(List.of(cafeTableAssignedToWaiter));

        Mockito.verify(cafeTableAssignedToWaiterRepository).findAllByWaiterUsername("mary21");
        Mockito.verifyNoMoreInteractions(cafeTableService, cafeTableAssignedToWaiterRepository, userService);
    }

    @Test
    public void testFindAllByWaiterId() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("Mary", "Smith", "mary21", "pwd21", LocalDateTime.MAX);
        waiter.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableAssignedToWaiterRepository.findAllByWaiterId(1L))
                .thenReturn(List.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.findAllByWaiterId(1L)).isEqualTo(List.of(cafeTableAssignedToWaiter));

        Mockito.verify(cafeTableAssignedToWaiterRepository).findAllByWaiterId(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, cafeTableAssignedToWaiterRepository, userService);
    }

    @Test
    public void testCreate() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("Mary", "Smith", "mary21", "pwd21", LocalDateTime.MAX);
        waiter.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);

        CafeTableAssignedToWaiter savedCafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        savedCafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableAssignedToWaiterRepository.save(cafeTableAssignedToWaiter)).thenReturn(savedCafeTableAssignedToWaiter);
        Mockito.when(cafeTableService.getById(1L)).thenReturn(cafeTable);
        Mockito.when(userService.getByUsername("mary21")).thenReturn(waiter);

        Assertions.assertThat(testSubject.create(
                new CafeTableAssignedToWaiterCreationParams(1L, "mary21", LocalDateTime.MAX))
        ).isEqualTo(savedCafeTableAssignedToWaiter);

        Mockito.verify(cafeTableAssignedToWaiterRepository).save(cafeTableAssignedToWaiter);
        Mockito.verify(cafeTableService).getById(1L);
        Mockito.verify(userService).getByUsername("mary21");
    }

    @Test
    public void testCreateWhenCafeTableIsNotFound() {
        Mockito.when(cafeTableService.getById(1L)).thenThrow(CafeTableNotFoundException.class);
        Assertions.assertThatThrownBy(() -> testSubject.create(new CafeTableAssignedToWaiterCreationParams(
                1L, "mary21", LocalDateTime.MAX
        ))).isExactlyInstanceOf(CafeTableNotFoundException.class);
        Mockito.verify(cafeTableService).getById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, userService, cafeTableAssignedToWaiterRepository);
    }

    @Test
    public void testCreateWhenUserIsNotFound() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        Mockito.when(cafeTableService.getById(1L)).thenReturn(cafeTable);
        Mockito.when(userService.getByUsername("mary21")).thenThrow(UserNotFoundException.class);
        Assertions.assertThatThrownBy(() -> testSubject.create(new CafeTableAssignedToWaiterCreationParams(
                1L, "mary21", LocalDateTime.MAX
        ))).isExactlyInstanceOf(UserNotFoundException.class);
        Mockito.verify(userService).getByUsername("mary21");
        Mockito.verify(cafeTableService).getById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, userService, cafeTableAssignedToWaiterRepository);
    }
    @Test
    public void testCreateWhenParamsObjectIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindAllByWaiterIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findAllByWaiterId(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByCafeTableIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findByCafeTableId(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExistsByWaiterUsernameWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.existsByWaiterUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindAllByWaiterUsernameWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findAllByWaiterUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDeleteByCafeTableIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.deleteByCafeTableId(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
