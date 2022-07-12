package com.cafe.service.impl.order;

import com.cafe.entity.order.Order;
import com.cafe.entity.order.OrderStatusType;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.entity.user.User;
import com.cafe.repository.OrderRepository;
import com.cafe.service.core.order.OrderCreationParams;
import com.cafe.service.core.order.OrderService;
import com.cafe.service.core.order.OrderUpdateParams;
import com.cafe.service.core.table.CafeTableService;
import com.cafe.service.core.user.UserService;
import com.cafe.service.impl.table.CafeTableNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderService testSubject;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CafeTableService cafeTableService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        testSubject = new OrderServiceImpl(orderRepository, cafeTableService, userService);
    }

    @Test
    public void testFindByIdWhenOrderIsNotFound() {
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.empty());
        Mockito.verify(orderRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(orderRepository, cafeTableService);
    }

    @Test
    public void testFindByIdWhenOrderIsFound() {
        Order order = new Order(
                new CafeTable(CafeTableStatusType.FREE, 5, "qwerty"),
                new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX),
                OrderStatusType.OPEN,
                LocalDateTime.MAX
        );
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(order));
        Mockito.verify(orderRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(orderRepository, cafeTableService);
    }

    @Test
    public void testGetByIdWhenOrderIsNotFound() {
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getById(1L)).isExactlyInstanceOf(OrderNotFoundException.class);
        Mockito.verify(orderRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(orderRepository, cafeTableService);
    }

    @Test
    public void testGetByIdWhenOrderIsFound() {
        Order order = new Order(
                new CafeTable(CafeTableStatusType.FREE, 5, "qwerty"),
                new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX),
                OrderStatusType.OPEN,
                LocalDateTime.MAX
        );
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Assertions.assertThat(testSubject.getById(1L)).isEqualTo(order);
        Mockito.verify(orderRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(orderRepository, cafeTableService);
    }

    @Test
    public void testCreate() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        Order order = new Order(cafeTable, user, OrderStatusType.OPEN, LocalDateTime.MAX);

        Order savedOrder = new Order(cafeTable, user, OrderStatusType.OPEN, LocalDateTime.MAX);
        savedOrder.setId(1L);

        Mockito.when(cafeTableService.getById(1L)).thenReturn(cafeTable);
        Mockito.when(orderRepository.save(order)).thenReturn(savedOrder);
        Mockito.when(userService.getByUsername("john11")).thenReturn(user);
        Assertions.assertThat(testSubject.create(new OrderCreationParams(
                1L, "john11", OrderStatusType.OPEN, LocalDateTime.MAX
        ))).isEqualTo(savedOrder);

        Mockito.verify(cafeTableService).getById(1L);
        Mockito.verify(orderRepository).save(order);
        Mockito.verify(userService).getByUsername("john11");
        Mockito.verifyNoMoreInteractions(cafeTableService, orderRepository);
    }

    @Test
    public void testUpdate() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        Order newOrder = new Order(cafeTable, user, OrderStatusType.CLOSED, LocalDateTime.MAX);
        newOrder.setId(1L);

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(newOrder));
        Mockito.when(cafeTableService.getById(1L)).thenReturn(cafeTable);
        Mockito.when(orderRepository.save(newOrder)).thenReturn(newOrder);

        Assertions.assertThat(testSubject.update(new OrderUpdateParams(
                1L, 1L, OrderStatusType.CLOSED, LocalDateTime.MAX
        ))).isEqualTo(newOrder);

        Mockito.verify(cafeTableService).getById(1L);
        Mockito.verify(orderRepository).save(newOrder);
        Mockito.verify(orderRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, orderRepository);
    }

    @Test
    public void testCreateWhenParamsIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateWhenCafeTableIsNotFound() {
        Mockito.when(cafeTableService.getById(1L)).thenThrow(CafeTableNotFoundException.class);
        Assertions.assertThatThrownBy(() -> testSubject.create(
                new OrderCreationParams(1L, "john11", OrderStatusType.OPEN, LocalDateTime.MAX)
        )).isExactlyInstanceOf(CafeTableNotFoundException.class);
        Mockito.verify(cafeTableService).getById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, orderRepository);
    }

    @Test
    public void testUpdateWhenParamsIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateWhenCafeTableIsNotFound() {
        Mockito.when(cafeTableService.getById(1L)).thenThrow(CafeTableNotFoundException.class);
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));
        Assertions.assertThatThrownBy(() -> testSubject.update(
                new OrderUpdateParams(1L, 1L, OrderStatusType.OPEN, LocalDateTime.now())
        )).isExactlyInstanceOf(CafeTableNotFoundException.class);
        Mockito.verify(cafeTableService).getById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableService, orderRepository);
    }

    @Test
    public void testGetByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}