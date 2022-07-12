package com.cafe.mapper.product;

import com.cafe.dto.request.ProductInOrderRegistrationRequestDto;
import com.cafe.service.core.product.ProductInOrderCreationParams;

import java.util.function.Function;

public interface ProductInOrderCreationParamsMapper extends Function<ProductInOrderRegistrationRequestDto, ProductInOrderCreationParams> {

    ProductInOrderCreationParams apply(ProductInOrderRegistrationRequestDto dto);
}
