package com.cafe.mapper.order;

import com.cafe.dto.response.OrderRegistrationResponseDto;
import com.cafe.entity.order.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderRegistrationResponseDtoMapperImpl implements OrderRegistrationResponseDtoMapper{

    @Override
    public OrderRegistrationResponseDto apply(Order order) {
        return new OrderRegistrationResponseDto(
                order.getTable().getId(),
                order.getOrderStatusType(),
                LocalDateTime.now(),
                HttpStatus.OK
        );
    }
}
