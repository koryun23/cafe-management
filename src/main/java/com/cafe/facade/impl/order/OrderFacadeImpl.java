package com.cafe.facade.impl.order;

import com.cafe.dto.OrderRegistrationRequestDto;
import com.cafe.dto.OrderRegistrationResponseDto;
import com.cafe.dto.OrderUpdateRequestDto;
import com.cafe.dto.OrderUpdateResponseDto;
import com.cafe.entity.order.Order;
import com.cafe.entity.order.OrderStatusType;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.facade.core.order.OrderFacade;
import com.cafe.mapper.order.*;
import com.cafe.service.core.order.OrderService;
import com.cafe.service.core.table.CafeTableService;
import com.cafe.service.impl.table.CafeTableNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class OrderFacadeImpl implements OrderFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFacadeImpl.class);
    private final OrderService orderService;
    private final CafeTableService cafeTableService;
    private final OrderRegistrationResponseDtoMapper orderRegistrationResponseDtoMapper;
    private final OrderUpdateResponseDtoMapper orderUpdateResponseDtoMapper;
    private final OrderUpdateParamsMapepr orderUpdateRequestDtoMapper;
    private final OrderCreationParamsMapper orderRegistrationRequestDtoMapper;

    public OrderFacadeImpl(OrderService orderService,
                           CafeTableService cafeTableService,
                           OrderRegistrationResponseDtoMapper orderRegistrationResponseDtoMapper,
                           OrderUpdateResponseDtoMapper orderUpdateResponseDtoMapper,
                           OrderUpdateParamsMapepr orderUpdateRequestDtoMapper,
                           OrderCreationParamsMapper orderRegistrationRequestDtoMapper) {
        this.orderService = orderService;
        this.cafeTableService = cafeTableService;
        this.orderRegistrationResponseDtoMapper = orderRegistrationResponseDtoMapper;
        this.orderUpdateResponseDtoMapper = orderUpdateResponseDtoMapper;
        this.orderUpdateRequestDtoMapper = orderUpdateRequestDtoMapper;
        this.orderRegistrationRequestDtoMapper = orderRegistrationRequestDtoMapper;
    }

    @Override
    public OrderRegistrationResponseDto register(OrderRegistrationRequestDto dto) {
        Assert.notNull(dto, "Order registration request should not be null");
        LOGGER.info("Registering a new order according to the order registration request dto - {}", dto);

        //TODO: CHECK IF THE REQUESTED TABLE IS ALREADY ASSIGNED TO THE WAITER
        Optional<CafeTable> cafeTableOptional = cafeTableService.findById(dto.getCafeTableId());
        if(cafeTableOptional.isEmpty()) {
            return new OrderRegistrationResponseDto(List.of(String.format("No table found having an id of %d", dto.getCafeTableId())));
        }
        CafeTable cafeTable = cafeTableOptional.get();
        if(cafeTable.getCafeTableStatusType() != CafeTableStatusType.FREE) {
            return new OrderRegistrationResponseDto(List.of(String.format("The cafe table with an id of %d is not free, its status is %s", dto.getCafeTableId(), cafeTable.getCafeTableStatusType())));
        }
        Order order = orderService.create(orderRegistrationRequestDtoMapper.apply(dto));
        System.out.println(order);
        OrderRegistrationResponseDto orderRegistrationResponseDto = orderRegistrationResponseDtoMapper.apply(order);
        cafeTableService.markAs(order.getTable().getId(), CafeTableStatusType.TAKEN);
        LOGGER.info("Successfully registered a new order according to the order registration request dto - {}, response - {}", dto, orderRegistrationResponseDto);
        return orderRegistrationResponseDto;
    }

    @Override
    public OrderUpdateResponseDto updateOrder(OrderUpdateRequestDto dto) {
        Assert.notNull(dto, "Order update request dto should not be null");
        LOGGER.info("Updating an order according to the order update request dto - {}", dto);
        Order order = orderService.update(orderUpdateRequestDtoMapper.apply(dto));
        if(order.getOrderStatusType() != OrderStatusType.OPEN) {
            cafeTableService.markAs(order.getTable().getId(), CafeTableStatusType.FREE);
        }
        OrderUpdateResponseDto responseDto = orderUpdateResponseDtoMapper.apply(order);
        LOGGER.info("Successfully updated an order according to the order update request dto - {}, response - {}", dto, responseDto);
        return responseDto;
    }
}
