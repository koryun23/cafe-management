package com.cafe.mapper.product;

import com.cafe.dto.response.ProductRegistrationResponseDto;
import com.cafe.entity.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductRegistrationResponseDtoMapperImpl implements ProductRegistrationResponseDtoMapper {
    @Override
    public ProductRegistrationResponseDto apply(Product product) {
        return new ProductRegistrationResponseDto(
                product.getName(),
                product.getPrice(),
                product.getAmount(),
                LocalDateTime.now(),
                HttpStatus.OK
        );
    }
}
