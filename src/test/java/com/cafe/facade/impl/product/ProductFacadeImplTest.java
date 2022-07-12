package com.cafe.facade.impl.product;

import com.cafe.dto.request.ProductInOrderRegistrationRequestDto;
import com.cafe.dto.request.ProductInOrderUpdateRequestDto;
import com.cafe.dto.request.ProductRegistrationRequestDto;
import com.cafe.dto.request.ProductUpdateRequestDto;
import com.cafe.dto.response.ProductInOrderRegistrationResponseDto;
import com.cafe.dto.response.ProductInOrderUpdateResponseDto;
import com.cafe.dto.response.ProductRegistrationResponseDto;
import com.cafe.dto.response.ProductUpdateResponseDto;
import com.cafe.dto.response.error.ErrorProductInOrderRegistrationResponseDto;
import com.cafe.dto.response.error.ErrorProductInOrderUpdateResponseDto;
import com.cafe.dto.response.error.ErrorProductRegistrationResponseDto;
import com.cafe.dto.response.error.ErrorProductUpdateResponseDto;
import com.cafe.entity.order.Order;
import com.cafe.entity.order.OrderStatusType;
import com.cafe.entity.product.Product;
import com.cafe.entity.product.ProductInOrder;
import com.cafe.entity.product.ProductInOrderStatusType;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import com.cafe.entity.user.UserRoleType;
import com.cafe.facade.core.product.ProductFacade;
import com.cafe.mapper.product.*;
import com.cafe.service.core.order.OrderService;
import com.cafe.service.core.order.OrderUpdateParams;
import com.cafe.service.core.product.*;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductFacadeImplTest {

    private ProductFacade testSubject;

    @Mock
    private ProductService productService;

    @Mock
    private ProductInOrderService productInOrderService;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductCreationParamsMapper productCreationParamsMapper;

    @Mock
    private ProductRegistrationResponseDtoMapper productRegistrationResponseDtoMapper;

    @Mock
    private ProductUpdateParamsMapper productUpdateParamsMapper;

    @Mock
    private ProductUpdateResponseDtoMapper productUpdateResponseDtoMapper;

    @Mock
    private ProductInOrderCreationParamsMapper productInOrderCreationParamsMapper;

    @Mock
    private ProductInOrderRegistrationResponseDtoMapper productInOrderRegistrationResponseDtoMapper;

    @Mock
    private ProductInOrderUpdateParamsMapper productInOrderUpdateParamsMapper;

    @Mock
    private ProductInOrderUpdateResponseDtoMapper productInOrderUpdateResponseDtoMapper;

    @BeforeEach
    public void init() {
        testSubject = new ProductFacadeImpl(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterProductWhenNameIsAlreadyTaken() {
        Product product = new Product("Pepsi", 4, 400, LocalDateTime.MAX);
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));
        Assertions.assertThat(testSubject.registerProduct(new ProductRegistrationRequestDto(
                "Pepsi", 2, 500
        ))).isExactlyInstanceOf(ErrorProductRegistrationResponseDto.class);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterProduct() {
        ProductRegistrationRequestDto requestDto = new ProductRegistrationRequestDto("Pepsi", 4, 400);
        ProductCreationParams creationParams = new ProductCreationParams("Pepsi", 4, 400, LocalDateTime.MAX);
        Product product = new Product("Pepsi", 4, 400, LocalDateTime.MAX);
        product.setId(1L);

        ProductRegistrationResponseDto responseDto = new ProductRegistrationResponseDto("Pepsi", 4, 400, LocalDateTime.MAX, HttpStatus.OK);
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.empty());
        Mockito.when(productCreationParamsMapper.apply(requestDto)).thenReturn(creationParams);
        Mockito.when(productService.create(creationParams)).thenReturn(product);
        Mockito.when(productRegistrationResponseDtoMapper.apply(product)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.registerProduct(requestDto)).isEqualTo(responseDto);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(productCreationParamsMapper).apply(requestDto);
        Mockito.verify(productService).create(creationParams);
        Mockito.verify(productRegistrationResponseDtoMapper).apply(product);

        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.registerProduct(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testUpdateWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.updateProduct(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateProductWhenProductDoesNotExist() {
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.updateProduct(new ProductUpdateRequestDto(
                "Pepsi", "Pepsi", 4, 400
        ))).isExactlyInstanceOf(ErrorProductUpdateResponseDto.class);
        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testUpdate() {
        Product product = new Product("Pepsi", 2, 400, LocalDateTime.MAX);
        product.setId(1L);

        ProductUpdateRequestDto requestDto = new ProductUpdateRequestDto("Pepsi", "Pepsi", 4, 400);
        ProductUpdateParams productUpdateParams = new ProductUpdateParams("Pepsi", "Pepsi", 400, 4);

        Product updatedProduct = new Product("Pepsi", 400, 4, LocalDateTime.MAX);
        updatedProduct.setId(1L);

        ProductUpdateResponseDto responseDto = new ProductUpdateResponseDto("Pepsi", 4, 400, LocalDateTime.MAX, HttpStatus.OK);
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(updatedProduct));
        Mockito.when(productUpdateParamsMapper.apply(requestDto)).thenReturn(productUpdateParams);
        Mockito.when(productService.updateProduct(productUpdateParams)).thenReturn(updatedProduct);
        Mockito.when(productUpdateResponseDtoMapper.apply(updatedProduct)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.updateProduct(requestDto)).isEqualTo(responseDto);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(productUpdateParamsMapper).apply(requestDto);
        Mockito.verify(productService).updateProduct(productUpdateParams);
        Mockito.verify(productUpdateResponseDtoMapper).apply(updatedProduct);

        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterProductInOrderWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.registerProductInOrder(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterProductInOrderWhenProductIsNotFound() {
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.empty());
        ProductInOrderRegistrationRequestDto requestDto = new ProductInOrderRegistrationRequestDto("Pepsi", 2);
        requestDto.setOrderId(1L);
        requestDto.setWaiterUsername("john11");
        Assertions.assertThat(testSubject.registerProductInOrder(requestDto))
                .isExactlyInstanceOf(ErrorProductInOrderRegistrationResponseDto.class);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterProductInOrderWhenActualProductAmountIsLessThanProductInOrderAmount() {

        Product product = new Product("Pepsi", 4, 30, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));

        ProductInOrderRegistrationRequestDto requestDto = new ProductInOrderRegistrationRequestDto(
                "Pepsi", 31
        );
        requestDto.setWaiterUsername("john11");
        requestDto.setOrderId(1L);
        Assertions.assertThat(testSubject.registerProductInOrder(requestDto)).isExactlyInstanceOf(ErrorProductInOrderRegistrationResponseDto.class);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterProductInOrderWhenOrderIsNotFound() {
        Product product = new Product("Pepsi", 4, 30, LocalDateTime.MAX);
        product.setId(1L);

        ProductInOrderRegistrationRequestDto requestDto = new ProductInOrderRegistrationRequestDto("Pepsi", 3);
        requestDto.setWaiterUsername("john11");
        requestDto.setOrderId(1L);

        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.registerProductInOrder(requestDto))
                .isExactlyInstanceOf(ErrorProductInOrderRegistrationResponseDto.class);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(orderService).findById(1L);
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testRegisterProductInOrder() {
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);
        userRole.setId(1L);
        user.setUserRoleList(List.of(userRole));

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        Product product = new Product("Pepsi", 4, 30, LocalDateTime.MAX);
        product.setId(1L);

        Order order = new Order(cafeTable, user, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        ProductInOrder productInOrder = new ProductInOrder(product, order, 3, LocalDateTime.MAX);
        productInOrder.setId(1L);

        ProductInOrderRegistrationRequestDto requestDto = new ProductInOrderRegistrationRequestDto(
                "Pepsi",  3
        );
        requestDto.setWaiterUsername("john11");
        requestDto.setOrderId(1L);

        ProductInOrderCreationParams creationParams = new ProductInOrderCreationParams(
                "Pepsi", 1L, 3, LocalDateTime.MAX
        );

        ProductInOrderRegistrationResponseDto responseDto = new ProductInOrderRegistrationResponseDto(
                "Pepsi", 1L, 3, LocalDateTime.MAX, HttpStatus.OK
        );

        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(productInOrderCreationParamsMapper.apply(requestDto)).thenReturn(creationParams);
        Mockito.when(productInOrderService.create(creationParams)).thenReturn(productInOrder);
        Mockito.when(productInOrderRegistrationResponseDtoMapper.apply(productInOrder)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.registerProductInOrder(requestDto)).isEqualTo(responseDto);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(orderService).findById(1L);
        Mockito.verify(productInOrderCreationParamsMapper).apply(requestDto);
        Mockito.verify(productInOrderService).create(creationParams);
        Mockito.verify(productInOrderRegistrationResponseDtoMapper).apply(productInOrder);
        Mockito.verify(productService).updateProduct(new ProductUpdateParams(
                "Pepsi", "Pepsi", 27, 4
        ));
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testUpdateProductInOrderWhenRequestDtoIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.updateProductInOrder(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testUpdateProductInOrderWhenProductIsNotFound() {
        ProductInOrderUpdateRequestDto productInOrderUpdateRequestDto = new ProductInOrderUpdateRequestDto(
                1L, "Pepsi", 3, ProductInOrderStatusType.ACTIVE
        );
        productInOrderUpdateRequestDto.setWaiterUsername("john11");
        productInOrderUpdateRequestDto.setOrderId(1L);
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.updateProductInOrder(productInOrderUpdateRequestDto))
                .isExactlyInstanceOf(ErrorProductInOrderUpdateResponseDto.class);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testUpdateProductInOrderWhenOrderIsNotFound() {
        ProductInOrderUpdateRequestDto requestDto = new ProductInOrderUpdateRequestDto(
                1L, "Pepsi", 3, ProductInOrderStatusType.ACTIVE
        );
        requestDto.setWaiterUsername("john11");
        requestDto.setOrderId(1L);
        Product product = new Product("Pepsi", 4, 400, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.updateProductInOrder(requestDto))
                .isExactlyInstanceOf(ErrorProductInOrderUpdateResponseDto.class);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(orderService).findById(1L);
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testUpdateProductInOrderWhenOrderIsNotOpen() {
        ProductInOrderUpdateRequestDto requestDto = new ProductInOrderUpdateRequestDto(
                1L, "Pepsi", 3, ProductInOrderStatusType.ACTIVE
        );
        requestDto.setWaiterUsername("john11");
        requestDto.setOrderId(1L);
        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);
        userRole.setId(1L);
        user.setUserRoleList(List.of(userRole));

        Product product = new Product("Pepsi", 4, 400, LocalDateTime.MAX);
        product.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        Order order = new Order(cafeTable, user, OrderStatusType.CLOSED, LocalDateTime.MAX);
        order.setId(1L);

        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));

        Assertions.assertThat(testSubject.updateProductInOrder(requestDto))
                .isExactlyInstanceOf(ErrorProductInOrderUpdateResponseDto.class);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(orderService).findById(1L);
        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }

    @Test
    public void testUpdateProductInOrder() {
        ProductInOrderUpdateRequestDto requestDto = new ProductInOrderUpdateRequestDto(
                1L, "Pepsi", 3, ProductInOrderStatusType.ACTIVE
        );
        requestDto.setWaiterUsername("john11");
        requestDto.setOrderId(1L);
        Product product = new Product("Pepsi", 4, 400, LocalDateTime.MAX);
        product.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        User user = new User("John", "Smith", "john11", "pwd11", LocalDateTime.MAX);
        user.setId(1L);

        UserRole userRole = new UserRole(user, UserRoleType.WAITER);
        userRole.setId(1L);
        user.setUserRoleList(List.of(userRole));

        Order order = new Order(cafeTable, user, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        ProductInOrder productInOrder = new ProductInOrder(
                product, order, 3, LocalDateTime.MAX
        );
        product.setId(1L);

        ProductInOrderUpdateParams updateParams = new ProductInOrderUpdateParams(
                1L, "Pepsi", 1L, 3, ProductInOrderStatusType.ACTIVE
        );

        ProductInOrderUpdateResponseDto responseDto = new ProductInOrderUpdateResponseDto(
                1L, "Pepsi", 1L, 3, LocalDateTime.MAX, HttpStatus.OK
        );
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(productInOrderUpdateParamsMapper.apply(requestDto)).thenReturn(updateParams);
        Mockito.when(productInOrderService.update(updateParams)).thenReturn(productInOrder);
        Mockito.when(productInOrderUpdateResponseDtoMapper.apply(productInOrder)).thenReturn(responseDto);

        Assertions.assertThat(testSubject.updateProductInOrder(requestDto)).isEqualTo(responseDto);

        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(orderService).findById(1L);
        Mockito.verify(productInOrderUpdateParamsMapper).apply(requestDto);
        Mockito.verify(productInOrderService).update(updateParams);
        Mockito.verify(productInOrderUpdateResponseDtoMapper).apply(productInOrder);

        Mockito.verifyNoMoreInteractions(
                productService,
                productInOrderService,
                orderService,
                productCreationParamsMapper,
                productRegistrationResponseDtoMapper,
                productUpdateParamsMapper,
                productUpdateResponseDtoMapper,
                productInOrderCreationParamsMapper,
                productInOrderRegistrationResponseDtoMapper,
                productInOrderUpdateParamsMapper,
                productInOrderUpdateResponseDtoMapper
        );
    }
}