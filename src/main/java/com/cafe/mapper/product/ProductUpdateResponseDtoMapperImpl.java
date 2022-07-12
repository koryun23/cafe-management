package com.cafe.mapper.product;

import com.cafe.dto.response.ProductUpdateResponseDto;
import com.cafe.entity.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductUpdateResponseDtoMapperImpl implements ProductUpdateResponseDtoMapper {

    @Override
    public ProductUpdateResponseDto apply(Product product) {
        return new ProductUpdateResponseDto(
                product.getName(),
                product.getPrice(),
                product.getAmount(),
                LocalDateTime.now(),
                HttpStatus.OK
        );
    }
}
