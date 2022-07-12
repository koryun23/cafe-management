package com.cafe.service.core.order;

import com.cafe.entity.order.Order;

import java.util.Optional;

public interface OrderService {
    Order create(OrderCreationParams params);

    Order update(OrderUpdateParams params);

    Order getById(Long id);

    Optional<Order> findById(Long id);
}
