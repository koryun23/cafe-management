package com.cafe.mapper.product;

import com.cafe.dto.response.ProductRegistrationResponseDto;
import com.cafe.entity.product.Product;

import java.util.function.Function;

public interface ProductRegistrationResponseDtoMapper extends Function<Product, ProductRegistrationResponseDto> {

    ProductRegistrationResponseDto apply(Product product);
}
