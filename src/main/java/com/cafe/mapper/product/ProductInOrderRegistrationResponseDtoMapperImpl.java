package com.cafe.mapper.product;

import com.cafe.dto.response.ProductInOrderRegistrationResponseDto;
import com.cafe.entity.product.ProductInOrder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductInOrderRegistrationResponseDtoMapperImpl implements ProductInOrderRegistrationResponseDtoMapper {

    @Override
    public ProductInOrderRegistrationResponseDto apply(ProductInOrder productInOrder) {
        return new ProductInOrderRegistrationResponseDto(
                productInOrder.getProduct().getName(),
                productInOrder.getOrder().getId(),
                productInOrder.getAmount(),
                LocalDateTime.now(),
                HttpStatus.OK
        );
    }
}
