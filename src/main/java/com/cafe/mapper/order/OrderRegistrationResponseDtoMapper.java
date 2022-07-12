package com.cafe.mapper.order;

import com.cafe.dto.response.OrderRegistrationResponseDto;
import com.cafe.entity.order.Order;

import java.util.function.Function;

public interface OrderRegistrationResponseDtoMapper extends Function<Order, OrderRegistrationResponseDto> {
    OrderRegistrationResponseDto apply(Order order);
}
