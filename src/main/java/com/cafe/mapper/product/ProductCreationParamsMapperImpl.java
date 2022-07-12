package com.cafe.mapper.product;

import com.cafe.dto.request.ProductRegistrationRequestDto;
import com.cafe.service.core.product.ProductCreationParams;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductCreationParamsMapperImpl implements ProductCreationParamsMapper {

    @Override
    public ProductCreationParams apply(ProductRegistrationRequestDto dto) {
        return new ProductCreationParams(
                dto.getName(),
                dto.getPrice(),
                dto.getAmount(),
                LocalDateTime.now()
        );
    }
}
