package com.cafe.facade.core.order;

import com.cafe.dto.request.OrderRegistrationRequestDto;
import com.cafe.dto.response.OrderRegistrationResponseDto;
import com.cafe.dto.request.OrderUpdateRequestDto;
import com.cafe.dto.response.OrderUpdateResponseDto;

public interface OrderFacade {
    OrderRegistrationResponseDto register(OrderRegistrationRequestDto dto);

    OrderUpdateResponseDto updateOrder(OrderUpdateRequestDto dto);

    boolean foundTableWithIdAssignedToWaiterWithUsername(Long tableId, String username);
}
