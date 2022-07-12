package com.cafe.mapper.product;

import com.cafe.dto.response.ProductInOrderRegistrationResponseDto;
import com.cafe.entity.product.ProductInOrder;

import java.util.function.Function;

public interface ProductInOrderRegistrationResponseDtoMapper extends Function<ProductInOrder, ProductInOrderRegistrationResponseDto> {

    ProductInOrderRegistrationResponseDto apply(ProductInOrder product);
}
