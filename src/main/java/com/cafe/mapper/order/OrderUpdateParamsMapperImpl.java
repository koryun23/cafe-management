package com.cafe.mapper.order;

import com.cafe.dto.request.OrderUpdateRequestDto;
import com.cafe.service.core.order.OrderUpdateParams;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderUpdateParamsMapperImpl implements OrderUpdateParamsMapepr {

    @Override
    public OrderUpdateParams apply(OrderUpdateRequestDto dto) {
        return new OrderUpdateParams(
                dto.getId(),
                dto.getCafeTableId(),
                dto.getOrderStatusType(),
                LocalDateTime.now()
        );
    }
}
