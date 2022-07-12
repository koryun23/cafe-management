package com.cafe.mapper.product;

import com.cafe.dto.response.ProductInOrderUpdateResponseDto;
import com.cafe.entity.product.ProductInOrder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductInOrderUpdateResponseDtoMapperImpl implements ProductInOrderUpdateResponseDtoMapper{

    @Override
    public ProductInOrderUpdateResponseDto apply(ProductInOrder productInOrder) {
        return new ProductInOrderUpdateResponseDto(
                productInOrder.getId(),
                productInOrder.getProduct().getName(),
                productInOrder.getOrder().getId(),
                productInOrder.getAmount(),
                LocalDateTime.now(),
                HttpStatus.OK
        );
    }
}
