package com.cafe.service.impl.product;

import com.cafe.entity.order.Order;
import com.cafe.entity.order.OrderStatusType;
import com.cafe.entity.product.Product;
import com.cafe.entity.product.ProductInOrder;
import com.cafe.entity.product.ProductInOrderStatusType;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.repository.ProductInOrderRepository;
import com.cafe.service.core.order.OrderService;
import com.cafe.service.core.product.ProductInOrderCreationParams;
import com.cafe.service.core.product.ProductInOrderService;
import com.cafe.service.core.product.ProductInOrderUpdateParams;
import com.cafe.service.core.product.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductInOrderServiceImplTest {

    private ProductInOrderService testSubject;

    @Mock
    private ProductInOrderRepository productInOrderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void init() {
        testSubject = new ProductInOrderServiceImpl(productInOrderRepository, productService, orderService);
    }

    @Test
    public void testCreate() {
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        Order order = new Order(cafeTable, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        ProductInOrder productInOrder = new ProductInOrder(product, order, 3);

        ProductInOrder savedProductInOrder = new ProductInOrder(
            product, order, 3
        );
        savedProductInOrder.setId(1L);

        Mockito.when(productInOrderRepository.save(productInOrder)).thenReturn(savedProductInOrder);
        Mockito.when(productService.findByName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(orderService.getById(1L)).thenReturn(order);

        Assertions.assertThat(testSubject.create(new ProductInOrderCreationParams(
                "Pepsi", order.getId(), 3
        ))).isEqualTo(savedProductInOrder);

        Mockito.verify(productInOrderRepository).save(productInOrder);
        Mockito.verify(productService).findByName("Pepsi");
        Mockito.verify(orderService).getById(1L);
        Mockito.verifyNoMoreInteractions(productInOrderRepository, productService, orderService);
    }

    @Test
    public void testUpdate() {
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        Order order = new Order(cafeTable, OrderStatusType.OPEN, LocalDateTime.MAX);
        order.setId(1L);

        ProductInOrder updatedProductInOrder = new ProductInOrder(
                product, order, 6
        );
        updatedProductInOrder.setId(1L);
        updatedProductInOrder.setProductInOrderStatusType(ProductInOrderStatusType.ACTIVE);
        Mockito.when(productService.getByName("Pepsi")).thenReturn(product);
        Mockito.when(orderService.getById(1L)).thenReturn(order);
        Mockito.when(productInOrderRepository.save(updatedProductInOrder)).thenReturn(updatedProductInOrder);

        Assertions.assertThat(testSubject.update(new ProductInOrderUpdateParams(
                1L,
                "Pepsi",
                order.getId(),
                6,
                ProductInOrderStatusType.ACTIVE
        ))).isEqualTo(updatedProductInOrder);

        Mockito.verify(productService).getByName("Pepsi");
        Mockito.verify(orderService).getById(1L);
        Mockito.verify(productInOrderRepository).save(updatedProductInOrder);
        Mockito.verifyNoMoreInteractions(productService, orderService, productInOrderRepository);
    }
}