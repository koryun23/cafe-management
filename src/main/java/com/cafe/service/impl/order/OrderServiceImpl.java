package com.cafe.service.impl.order;

import com.cafe.entity.order.Order;
import com.cafe.entity.table.CafeTableAssignedToWaiter;
import com.cafe.repository.OrderRepository;
import com.cafe.service.core.order.OrderUpdateParams;
import com.cafe.service.core.table.CafeTableService;
import com.cafe.service.core.order.OrderCreationParams;
import com.cafe.service.core.order.OrderService;
import com.cafe.service.core.user.UserService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final CafeTableService cafeTableService;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CafeTableService cafeTableService,
                            UserService userService) {
        Assert.notNull(orderRepository, "Order repository should not be null");
        Assert.notNull(cafeTableService, "Cafe table service should not be null");
        Assert.notNull(userService, "User service should not be null");
        this.orderRepository = orderRepository;
        this.cafeTableService = cafeTableService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Order create(OrderCreationParams params) {
        Assert.notNull(params, "Order creation params object should not be null");
        LOGGER.info("Creating a new order according to the order creation params - {}", params);
        Order order = orderRepository.save(new Order(
                cafeTableService.getById(params.getCafeTableId()),
                userService.getByUsername(params.getWaiterUsername()),
                params.getOrderStatusType(),
                params.getCreatedAt()
        ));
        LOGGER.info("Successfully created a new order according to the order creation params - {}, result - {}", params, order);
        return order;
    } // tested

    @Transactional
    @Override
    public Order update(OrderUpdateParams params) {
        Assert.notNull(params, "order update params should not be null");
        LOGGER.info("Updating an order according to the order update params - {}", params);
        Order existingOrder = orderRepository.findById(params.getId()).orElseThrow(() -> new OrderNotFoundException(params.getId()));
        Order order = new Order(
                cafeTableService.getById(params.getCafeTableId()),
                existingOrder.getWaiter(),
                params.getOrderStatusType(),
                params.getUpdatedAt()
        );
        order.setId(params.getId());
        Order savedOrder = orderRepository.save(order);
        LOGGER.info("Successfully updated an order according to the order update params - {}, result - {}", params, savedOrder);
        return savedOrder;
    }

    @Transactional(readOnly = true)
    @Override
    public Order getById(Long id) {
        Assert.notNull(id, "Order id should not be null");
        LOGGER.info("Retrieving an order having an id of {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        LOGGER.info("Successfully retrieved an order having an id of {}, result - {}", id, order);
        return order;
    } // tested

    @Override
    public Optional<Order> findById(Long id) {
        Assert.notNull(id, "Order id should not be null");
        LOGGER.info("Retrieving an optional of an order having an id of {}", id);
        Optional<Order> orderOptional = orderRepository.findById(id);
        LOGGER.info("Successfully retrieved an order having an id of {}, result - {}", id, orderOptional);
        return orderOptional;
    } // tested
}
