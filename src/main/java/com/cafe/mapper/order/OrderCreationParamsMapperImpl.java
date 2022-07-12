package com.cafe.mapper.order;

import com.cafe.dto.request.OrderRegistrationRequestDto;
import com.cafe.service.core.order.OrderCreationParams;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderCreationParamsMapperImpl implements OrderCreationParamsMapper {

    @Override
    public OrderCreationParams apply(OrderRegistrationRequestDto dto) {
        return new OrderCreationParams(
                dto.getCafeTableId(),
                dto.getWaiterUsername(),
                dto.getStatus(),
                LocalDateTime.now()
        );
    }
}
