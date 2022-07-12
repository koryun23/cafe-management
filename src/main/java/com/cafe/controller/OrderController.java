package com.cafe.controller;

import com.cafe.handler.BasicAuthorizationHttpServletRequestHandler;
import com.cafe.dto.request.OrderRegistrationRequestDto;
import com.cafe.dto.response.OrderRegistrationResponseDto;
import com.cafe.dto.request.OrderUpdateRequestDto;
import com.cafe.dto.response.OrderUpdateResponseDto;
import com.cafe.entity.order.OrderStatusType;
import com.cafe.facade.core.order.OrderFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "orders", consumes = "application/json", produces = "application/json")
public class OrderController {

    private final OrderFacade orderFacade;
    
    private final BasicAuthorizationHttpServletRequestHandler basicAuthorizationHttpServletRequestHandler;
    
    public OrderController(OrderFacade orderFacade,
                           BasicAuthorizationHttpServletRequestHandler basicAuthorizationHttpServletRequestHandler) {
        this.orderFacade = orderFacade;
        this.basicAuthorizationHttpServletRequestHandler = basicAuthorizationHttpServletRequestHandler;
    }

    @PostMapping(path = "/register/{cafeTableId}")
    public ResponseEntity<OrderRegistrationResponseDto> register(HttpServletRequest request,
                                                                 @RequestBody OrderRegistrationRequestDto dto,
                                                                 @PathVariable Long cafeTableId) {
        String username = basicAuthorizationHttpServletRequestHandler
                .getUsernameAndPassword(request)
                .getUsername();
        dto.setStatus(OrderStatusType.OPEN);
        dto.setWaiterUsername(username);
        dto.setCafeTableId(cafeTableId);
        OrderRegistrationResponseDto responseDto = orderFacade.register(dto);
        return ResponseEntity
                .status(responseDto.getHttpStatus())
                .body(responseDto);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<OrderUpdateResponseDto> updateOrder(HttpServletRequest request,
                                                              @RequestBody OrderUpdateRequestDto dto,
                                                              @PathVariable Long id) {
        String username = basicAuthorizationHttpServletRequestHandler.getUsernameAndPassword(request).getUsername();
        dto.setWaiterUsername(username);
        dto.setId(id);
        OrderUpdateResponseDto responseDto = orderFacade.updateOrder(dto);
        return ResponseEntity
                .status(responseDto.getHttpStatus())
                .body(responseDto);
    }
}
