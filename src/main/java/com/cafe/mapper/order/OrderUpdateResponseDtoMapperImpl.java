package com.cafe.mapper.order;

import com.cafe.dto.response.OrderUpdateResponseDto;
import com.cafe.entity.order.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderUpdateResponseDtoMapperImpl implements OrderUpdateResponseDtoMapper {

    @Override
    public OrderUpdateResponseDto apply(Order order) {
        return new OrderUpdateResponseDto(
                order.getId(),
                order.getTable().getId(),
                order.getOrderStatusType(),
                LocalDateTime.now(),
                HttpStatus.OK
        );
    }
}
