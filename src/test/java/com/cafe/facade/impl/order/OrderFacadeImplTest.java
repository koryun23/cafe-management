package com.cafe.facade.impl.order;

import com.cafe.dto.request.OrderRegistrationRequestDto;
import com.cafe.dto.request.OrderUpdateRequestDto;
import com.cafe.dto.response.OrderRegistrationResponseDto;
import com.cafe.dto.response.OrderUpdateResponseDto;
import com.cafe.dto.response.error.ErrorOrderRegistrationResponseDto;
import com.cafe.dto.response.error.ErrorOrderUpdateResponseDto;
import com.cafe.entity.order.Order;
import com.cafe.entity.order.OrderStatusType;
import com.cafe.entity.product.ProductInOrderStatusType;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableAssignedToWaiter;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;
import com.cafe.facade.core.order.OrderFacade;
import com.cafe.mapper.order.OrderCreationParamsMapper;
import com.cafe.mapper.order.OrderRegistrationResponseDtoMapper;
import com.cafe.mapper.order.OrderUpdateParamsMapepr;
import com.cafe.mapper.order.OrderUpdateResponseDtoMapper;
import com.cafe.service.core.order.OrderCreationParams;
import com.cafe.service.core.order.OrderService;
import com.cafe.service.core.order.OrderUpdateParams;
import com.cafe.service.core.product.ProductInOrderService;
import com.cafe.service.core.table.CafeTableAssignedToWaiterService;
import com.cafe.service.core.table.CafeTableService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderFacadeImplTest {

    private OrderFacade testSubject;

    @Mock
    private OrderService orderService;

    @Mock
    private CafeTableService cafeTableService;

    @Mock
    private CafeTableAssignedToWaiterService cafeTableAssignedToWaiterService;

    @Mock
    private OrderRegistrationResponseDtoMapper orderRegistrationResponseDtoMapper;

    @Mock
    private OrderUpdateResponseDtoMapper orderUpdateResponseDtoMapper;

    @Mock
    private OrderUpdateParamsMapepr orderUpdateParamsMapper;

    @Mock
    private OrderCreationParamsMapper orderRegistrationRequestDtoMapper;

    @Mock
    private ProductInOrderService productInOrderService;

    @BeforeEach
    public void init() {
        testSubject = new OrderFacadeImpl(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testRegisterWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.register(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);

        Mockito.verifyNoInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testRegisterOrderWhenCafeTableIsNotFound() {
        OrderRegistrationRequestDto requestDto = new OrderRegistrationRequestDto(2L, "john11");

        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, user, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableService.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.register(requestDto))
                .isExactlyInstanceOf(ErrorOrderRegistrationResponseDto.class);

        Mockito.verify(cafeTableService).findById(2L);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testRegisterOrderWhenTableIsNotAssignedToWaiter() {
        OrderRegistrationRequestDto requestDto = new OrderRegistrationRequestDto(1L, "john11");

        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, user, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableService.findById(1L)).thenReturn(Optional.of(cafeTable));
        Mockito.when(cafeTableAssignedToWaiterService.findAllByWaiterUsername("john11"))
                .thenReturn(Collections.emptyList());

        Assertions.assertThat(testSubject.register(requestDto))
                .isExactlyInstanceOf(ErrorOrderRegistrationResponseDto.class);

        Mockito.verify(cafeTableAssignedToWaiterService).findAllByWaiterUsername("john11");
        Mockito.verify(cafeTableService).findById(1L);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testRegisterOrderWhenCafeTableIsNotFree() {
        OrderRegistrationRequestDto requestDto = new OrderRegistrationRequestDto(1L, "john11");

        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.TAKEN, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, user, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(cafeTableService.findById(1L)).thenReturn(Optional.of(cafeTable));
        Mockito.when(cafeTableAssignedToWaiterService.findAllByWaiterUsername("john11"))
                .thenReturn(List.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.register(requestDto))
                .isExactlyInstanceOf(ErrorOrderRegistrationResponseDto.class);

        Mockito.verify(cafeTableService).findById(1L);
        Mockito.verify(cafeTableAssignedToWaiterService).findAllByWaiterUsername("john11");

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testRegisterOrder() {
        OrderRegistrationRequestDto requestDto = new OrderRegistrationRequestDto(1L, "john11");

        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, user, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        OrderCreationParams creationParams = new OrderCreationParams(1L, "john11", OrderStatusType.OPEN, LocalDateTime.MAX);

        Order order = new Order(cafeTable, user, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        OrderRegistrationResponseDto responseDto = new OrderRegistrationResponseDto(1L, OrderStatusType.OPEN, LocalDateTime.MAX, HttpStatus.OK);

        Mockito.when(cafeTableService.findById(1L)).thenReturn(Optional.of(cafeTable));
        Mockito.when(cafeTableAssignedToWaiterService.findAllByWaiterUsername("john11"))
                .thenReturn(List.of(cafeTableAssignedToWaiter));
        Mockito.when(orderRegistrationRequestDtoMapper.apply(requestDto)).thenReturn(creationParams);
        Mockito.when(orderService.create(creationParams)).thenReturn(order);
        Mockito.when(orderRegistrationResponseDtoMapper.apply(order)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.register(requestDto)).isEqualTo(responseDto);

        Mockito.verify(cafeTableService).findById(1L);
        Mockito.verify(cafeTableAssignedToWaiterService).findAllByWaiterUsername("john11");
        Mockito.verify(orderRegistrationRequestDtoMapper).apply(requestDto);
        Mockito.verify(orderService).create(creationParams);
        Mockito.verify(orderRegistrationResponseDtoMapper).apply(order);
        Mockito.verify(cafeTableService).markAs(1L, CafeTableStatusType.TAKEN);
        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testUpdateWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.updateOrder(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testUpdateWhenOrderIsNotFound() {

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, OrderStatusType.OPEN, "john11");
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.updateOrder(requestDto))
                .isExactlyInstanceOf(ErrorOrderUpdateResponseDto.class);

        Mockito.verify(orderService).findById(1L);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testUpdateWhenOrderIsNotOpenAndRequestedOrderStatusIsOpen() {
        User waiter = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        waiter.setId(1L);

        UserRole userRole = new UserRole(waiter, UserRoleType.WAITER);
        userRole.setId(1L);
        waiter.setUserRoleList(List.of(userRole));

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        Order order = new Order(cafeTable, waiter, OrderStatusType.CLOSED, LocalDateTime.MAX);
        order.setId(1L);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, OrderStatusType.OPEN, "john11");
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));

        Assertions.assertThat(testSubject.updateOrder(requestDto))
                .isExactlyInstanceOf(ErrorOrderUpdateResponseDto.class);

        Mockito.verify(orderService).findById(1L);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testUpdateOrderWhenCreatorAndUpdatorUsersAreDifferent() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        waiter.setId(1L);

        UserRole userRole = new UserRole(waiter, UserRoleType.WAITER);
        userRole.setId(1L);
        waiter.setUserRoleList(List.of(userRole));

        Order order = new Order(cafeTable, waiter, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, OrderStatusType.OPEN, "mary21");



        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(cafeTableAssignedToWaiterService.findByCafeTableId(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));

        Assertions.assertThat(testSubject.updateOrder(requestDto))
                .isExactlyInstanceOf(ErrorOrderUpdateResponseDto.class);

        Mockito.verify(orderService).findById(1L);
        Mockito.verify(cafeTableAssignedToWaiterService).findByCafeTableId(1L);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testUpdateWhenStatusIsNotChanged() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        waiter.setId(1L);

        UserRole userRole = new UserRole(waiter, UserRoleType.WAITER);
        userRole.setId(1L);
        waiter.setUserRoleList(List.of(userRole));

        Order order = new Order(cafeTable, waiter, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, OrderStatusType.OPEN, "john11");

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        OrderUpdateParams updateParams = new OrderUpdateParams(1L, 1L, OrderStatusType.OPEN, LocalDateTime.MAX);

        OrderUpdateResponseDto responseDto = new OrderUpdateResponseDto(1L, 1L, OrderStatusType.OPEN, LocalDateTime.MAX, HttpStatus.OK);
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(cafeTableAssignedToWaiterService.findByCafeTableId(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));
        Mockito.when(orderUpdateParamsMapper.apply(requestDto)).thenReturn(updateParams);
        Mockito.when(orderService.update(updateParams)).thenReturn(order);
        Mockito.when(orderUpdateResponseDtoMapper.apply(order)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.updateOrder(requestDto)).isEqualTo(responseDto);

        Mockito.verify(orderService).findById(1L);
        Mockito.verify(cafeTableAssignedToWaiterService).findByCafeTableId(1L);
        Mockito.verify(orderUpdateParamsMapper).apply(requestDto);
        Mockito.verify(orderService).update(updateParams);
        Mockito.verify(orderUpdateResponseDtoMapper).apply(order);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testUpdateOrderWhenStatusIsChangedToClosed() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        waiter.setId(1L);
        UserRole userRole = new UserRole(waiter, UserRoleType.WAITER);
        userRole.setId(1L);
        waiter.setUserRoleList(List.of(userRole));

        Order order = new Order(cafeTable, waiter, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        Order updatedOrder = new Order(cafeTable, waiter, OrderStatusType.CLOSED, LocalDateTime.MAX);
        updatedOrder.setId(1L);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, OrderStatusType.CLOSED, "john11");

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        OrderUpdateParams updateParams = new OrderUpdateParams(1L, 1L, OrderStatusType.OPEN, LocalDateTime.MAX);

        OrderUpdateResponseDto responseDto = new OrderUpdateResponseDto(1L, 1L, OrderStatusType.OPEN, LocalDateTime.MAX, HttpStatus.OK);
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(cafeTableAssignedToWaiterService.findByCafeTableId(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));
        Mockito.when(orderUpdateParamsMapper.apply(requestDto)).thenReturn(updateParams);
        Mockito.when(orderService.update(updateParams)).thenReturn(updatedOrder);
        Mockito.when(orderUpdateResponseDtoMapper.apply(updatedOrder)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.updateOrder(requestDto)).isEqualTo(responseDto);

        Mockito.verify(orderService).findById(1L);
        Mockito.verify(cafeTableAssignedToWaiterService).findByCafeTableId(1L);
        Mockito.verify(orderUpdateParamsMapper).apply(requestDto);
        Mockito.verify(orderService).update(updateParams);
        Mockito.verify(orderUpdateResponseDtoMapper).apply(updatedOrder);
        Mockito.verify(cafeTableAssignedToWaiterService).deleteByCafeTableId(1L);
        Mockito.verify(cafeTableService).markAs(1L, CafeTableStatusType.FREE);
        Mockito.verify(productInOrderService).markAllAs(1L, ProductInOrderStatusType.CLOSED);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }

    @Test
    public void testUpdateOrderWhenStatusIsChangedToCancelled() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User waiter = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        waiter.setId(1L);
        UserRole userRole = new UserRole(waiter, UserRoleType.WAITER);
        userRole.setId(1L);
        waiter.setUserRoleList(List.of(userRole));

        Order order = new Order(cafeTable, waiter, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        Order updatedOrder = new Order(cafeTable, waiter, OrderStatusType.CANCELLED, LocalDateTime.MAX);
        updatedOrder.setId(1L);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, OrderStatusType.CLOSED, "john11");

        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = new CafeTableAssignedToWaiter(cafeTable, waiter, LocalDateTime.MAX);
        cafeTableAssignedToWaiter.setId(1L);

        OrderUpdateParams updateParams = new OrderUpdateParams(1L, 1L, OrderStatusType.OPEN, LocalDateTime.MAX);

        OrderUpdateResponseDto responseDto = new OrderUpdateResponseDto(1L, 1L, OrderStatusType.OPEN, LocalDateTime.MAX, HttpStatus.OK);
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(cafeTableAssignedToWaiterService.findByCafeTableId(1L)).thenReturn(Optional.of(cafeTableAssignedToWaiter));
        Mockito.when(orderUpdateParamsMapper.apply(requestDto)).thenReturn(updateParams);
        Mockito.when(orderService.update(updateParams)).thenReturn(updatedOrder);
        Mockito.when(orderUpdateResponseDtoMapper.apply(updatedOrder)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.updateOrder(requestDto)).isEqualTo(responseDto);

        Mockito.verify(orderService).findById(1L);
        Mockito.verify(cafeTableAssignedToWaiterService).findByCafeTableId(1L);
        Mockito.verify(orderUpdateParamsMapper).apply(requestDto);
        Mockito.verify(orderService).update(updateParams);
        Mockito.verify(orderUpdateResponseDtoMapper).apply(updatedOrder);
        Mockito.verify(cafeTableAssignedToWaiterService).deleteByCafeTableId(1L);
        Mockito.verify(cafeTableService).markAs(1L, CafeTableStatusType.FREE);
        Mockito.verify(productInOrderService).markAllAs(1L, ProductInOrderStatusType.CANCELLED);

        Mockito.verifyNoMoreInteractions(
                orderService,
                cafeTableService,
                cafeTableAssignedToWaiterService,
                orderRegistrationResponseDtoMapper,
                orderUpdateResponseDtoMapper,
                orderUpdateParamsMapper,
                orderRegistrationRequestDtoMapper,
                productInOrderService
        );
    }
}
