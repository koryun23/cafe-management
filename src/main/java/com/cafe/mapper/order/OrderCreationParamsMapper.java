package com.cafe.mapper.order;

import com.cafe.dto.request.OrderRegistrationRequestDto;
import com.cafe.service.core.order.OrderCreationParams;

import java.util.function.Function;

public interface OrderCreationParamsMapper extends Function<OrderRegistrationRequestDto, OrderCreationParams> {
}
