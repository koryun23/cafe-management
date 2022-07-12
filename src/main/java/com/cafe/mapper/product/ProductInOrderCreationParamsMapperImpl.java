package com.cafe.mapper.product;

import com.cafe.dto.request.ProductInOrderRegistrationRequestDto;
import com.cafe.service.core.product.ProductInOrderCreationParams;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductInOrderCreationParamsMapperImpl implements ProductInOrderCreationParamsMapper {
    @Override
    public ProductInOrderCreationParams apply(ProductInOrderRegistrationRequestDto dto) {
        return new ProductInOrderCreationParams(
                dto.getProductName(),
                dto.getOrderId(),
                dto.getAmount(),
                LocalDateTime.now()
        );
    }
}
