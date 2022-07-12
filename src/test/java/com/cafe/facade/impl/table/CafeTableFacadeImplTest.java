package com.cafe.facade.impl.table;

import com.cafe.dto.request.CafeTableAssignmentRequestDto;
import com.cafe.dto.request.CafeTableRegistrationRequestDto;
import com.cafe.dto.request.CafeTablesAssignedToWaiterRetrievalRequestDto;
import com.cafe.dto.response.CafeTableAssignmentResponseDto;
import com.cafe.dto.response.CafeTableRegistrationResponseDto;
import com.cafe.dto.response.CafeTablesAssignedToWaiterRetrievalResponseDto;
import com.cafe.dto.response.error.ErrorCafeTableAssignmentResponseDto;
import com.cafe.dto.response.error.ErrorCafeTableRegistrationResponseDto;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableAssignedToWaiter;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;
import com.cafe.facade.core.table.CafeTableFacade;
import com.cafe.mapper.table.CafeTableAssignedToWaiterCreationParamsMapper;
import com.cafe.mapper.table.CafeTableAssignmentResponseDtoMapper;
import com.cafe.mapper.table.CafeTableCreationParamsMapper;
import com.cafe.mapper.table.CafeTableRegistrationResponseDtoMapper;
import com.cafe.service.core.table.CafeTableAssignedToWaiterCreationParams;
import com.cafe.service.core.table.CafeTableAssignedToWaiterService;
import com.cafe.service.core.table.CafeTableCreationParams;
import com.cafe.service.core.table.CafeTableService;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CafeTableFacadeImplTest {

    private CafeTableFacade testSubject;

    @Mock
    private CafeTableService cafeTableService;

    @Mock
    private UserService userService;

    @Mock
    private CafeTableAssignedToWaiterService cafeTableAssignedToWaiterService;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private CafeTableCreationParamsMapper cafeTableCreationParamsMapper;

    @Mock
    private CafeTableRegistrationResponseDtoMapper cafeTableRegistrationResponseDtoMapper;

    @Mock
    private CafeTableAssignedToWaiterCreationParamsMapper cafeTableAssignedToWaiterCreationParamsMapper;

    @Mock
    private CafeTableAssignmentResponseDtoMapper cafeTableAssignmentResponseDtoMapper;

    @BeforeEach
    public void init() {
        testSubject = new CafeTableFacadeImpl(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testRegisterCafeTableWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.register(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testRegisterCafeTableWhenCodeIsAlreadyTaken() {
        Mockito.when(cafeTableService.existsByCode("qwerty")).thenReturn(true);
        CafeTableRegistrationResponseDto responseDto = testSubject.register(new CafeTableRegistrationRequestDto(5, "qwerty"));
        Assertions.assertThat(responseDto).isExactlyInstanceOf(ErrorCafeTableRegistrationResponseDto.class);
        Mockito.verify(cafeTableService).existsByCode("qwerty");
        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testRegisterCafeTable() {
        CafeTableRegistrationRequestDto requestDto = new CafeTableRegistrationRequestDto(5, "qwerty");
        CafeTableCreationParams creationParams = new CafeTableCreationParams(CafeTableStatusType.FREE, 5, "qwerty");
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableRegistrationResponseDto responseDto = new CafeTableRegistrationResponseDto(CafeTableStatusType.FREE, 5, "qwerty", LocalDateTime.MAX, HttpStatus.OK);

        Mockito.when(cafeTableService.existsByCode("qwerty")).thenReturn(false);
        Mockito.when(cafeTableCreationParamsMapper.apply(requestDto)).thenReturn(creationParams);
        Mockito.when(cafeTableService.create(creationParams)).thenReturn(cafeTable);
        Mockito.when(cafeTableRegistrationResponseDtoMapper.apply(cafeTable)).thenReturn(responseDto);

        CafeTableRegistrationResponseDto result = testSubject.register(new CafeTableRegistrationRequestDto(5, "qwerty"));
        Assertions.assertThat(result).isEqualTo(responseDto);

        Mockito.verify(cafeTableService).existsByCode("qwerty");
        Mockito.verify(cafeTableCreationParamsMapper).apply(requestDto);
        Mockito.verify(cafeTableService).create(creationParams);
        Mockito.verify(cafeTableRegistrationResponseDtoMapper).apply(cafeTable);
        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testAssignTableToWaiterWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.assignTableToWaiter(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testAssignTableToWaiterWhenWaiterIsNotFound() {
        Mockito.when(userService.findByUsername("mary21")).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.assignTableToWaiter(new CafeTableAssignmentRequestDto(
                1L, "mary21"
        ))).isExactlyInstanceOf(ErrorCafeTableAssignmentResponseDto.class);
        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testAssignTableToWaiterWhenUserIsNotWaiter() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.MANAGER);
        userRole.setId(1L);

        user.setUserRoleList(List.of(userRole));

        Mockito.when(userService.findByUsername("john11")).thenReturn(Optional.of(user));
        Mockito.when(userRoleService.getRoleType("john11")).thenReturn(UserRoleType.MANAGER);

        Assertions.assertThat(testSubject.assignTableToWaiter(new CafeTableAssignmentRequestDto(
                1L, "john11"
        ))).isExactlyInstanceOf(ErrorCafeTableAssignmentResponseDto.class);

        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testAssignTableToWaiterWhenTableIsNotFound() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);
        userRole.setId(1L);

        user.setUserRoleList(List.of(userRole));

        Mockito.when(userService.findByUsername("john11")).thenReturn(Optional.of(user));
        Mockito.when(userRoleService.getRoleType("john11")).thenReturn(UserRoleType.WAITER);
        Mockito.when(cafeTableService.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.assignTableToWaiter(new CafeTableAssignmentRequestDto(
                1L, "john11"
        ))).isExactlyInstanceOf(ErrorCafeTableAssignmentResponseDto.class);

        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testAssignTableToWaiterWhenTableIsNotFree() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);
        userRole.setId(1L);

        user.setUserRoleList(List.of(userRole));

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.TAKEN, 5, "qwerty");
        cafeTable.setId(1L);

        Mockito.when(userService.findByUsername("john11")).thenReturn(Optional.of(user));
        Mockito.when(userRoleService.getRoleType("john11")).thenReturn(UserRoleType.WAITER);
        Mockito.when(cafeTableService.findById(1L)).thenReturn(Optional.of(cafeTable));

        Assertions.assertThat(testSubject.assignTableToWaiter(new CafeTableAssignmentRequestDto(
                1L, "john11"
        ))).isExactlyInstanceOf(ErrorCafeTableAssignmentResponseDto.class);

        Mockito.verify(userService).findByUsername("john11");
        Mockito.verify(userRoleService).getRoleType("john11");
        Mockito.verify(cafeTableService).findById(1L);

        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testAssignTableToWaiterWhenTableIsAlreadyAssignedToAnotherWaiter() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);
        userRole.setId(1L);

        user.setUserRoleList(List.of(userRole));

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, user, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(userService.findByUsername("john11")).thenReturn(Optional.of(user));
        Mockito.when(userRoleService.getRoleType("john11")).thenReturn(UserRoleType.WAITER);
        Mockito.when(cafeTableService.findById(1L)).thenReturn(Optional.of(cafeTable));
        Mockito.when(cafeTableAssignedToWaiterService.findByCafeTableId(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.assignTableToWaiter(new CafeTableAssignmentRequestDto(
                1L, "john11"
        ))).isExactlyInstanceOf(ErrorCafeTableAssignmentResponseDto.class);

        Mockito.verify(userService).findByUsername("john11");
        Mockito.verify(userRoleService).getRoleType("john11");
        Mockito.verify(cafeTableService).findById(1L);
        Mockito.verify(cafeTableAssignedToWaiterService).findByCafeTableId(1L);

        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testAssignTableToWaiter() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);
        userRole.setId(1L);

        user.setUserRoleList(List.of(userRole));

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableAssignmentRequestDto requestDto = new CafeTableAssignmentRequestDto(1L, "john11");
        CafeTableAssignedToWaiterCreationParams creationParams = new CafeTableAssignedToWaiterCreationParams(1L, "john11", LocalDateTime.MAX);
        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, user, LocalDateTime.MAX);
        CafeTableAssignmentResponseDto responseDto = new CafeTableAssignmentResponseDto(1L, "john11", LocalDateTime.MAX, HttpStatus.OK);

        Mockito.when(userService.findByUsername("john11")).thenReturn(Optional.of(user));
        Mockito.when(userRoleService.getRoleType("john11")).thenReturn(UserRoleType.WAITER);
        Mockito.when(cafeTableService.findById(1L)).thenReturn(Optional.of(cafeTable));
        Mockito.when(cafeTableAssignedToWaiterService.findByCafeTableId(1L)).thenReturn(Optional.empty());
        Mockito.when(cafeTableAssignedToWaiterCreationParamsMapper.apply(requestDto)).thenReturn(creationParams);
        Mockito.when(cafeTableAssignedToWaiterService.create(creationParams)).thenReturn(cafeTableAssignedToWaiter);
        Mockito.when(cafeTableAssignmentResponseDtoMapper.apply(cafeTableAssignedToWaiter)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.assignTableToWaiter(new CafeTableAssignmentRequestDto(
                1L, "john11"
        ))).isEqualTo(responseDto);

        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }

    @Test
    public void testRetrieveCafeTableList() {
        User waiter = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        waiter.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        CafeTablesAssignedToWaiterRetrievalResponseDto responseDto = new CafeTablesAssignedToWaiterRetrievalResponseDto(List.of(cafeTableAssignedToWaiter), HttpStatus.OK);
        Mockito.when(cafeTableAssignedToWaiterService.findAllByWaiterUsername("john11")).thenReturn(List.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.retrieveCafeTableList(new CafeTablesAssignedToWaiterRetrievalRequestDto(
                "john11"
        ))).isEqualTo(responseDto);

        Mockito.verify(cafeTableAssignedToWaiterService).findAllByWaiterUsername("john11");

        Mockito.verifyNoMoreInteractions(
                cafeTableService,
                userService,
                cafeTableAssignedToWaiterService,
                userRoleService,
                cafeTableCreationParamsMapper,
                cafeTableRegistrationResponseDtoMapper,
                cafeTableAssignedToWaiterCreationParamsMapper,
                cafeTableAssignmentResponseDtoMapper
        );
    }
}