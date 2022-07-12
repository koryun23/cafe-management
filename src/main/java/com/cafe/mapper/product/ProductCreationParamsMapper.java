package com.cafe.mapper.product;

import com.cafe.dto.request.ProductRegistrationRequestDto;
import com.cafe.service.core.product.ProductCreationParams;

import java.util.function.Function;

public interface ProductCreationParamsMapper extends Function<ProductRegistrationRequestDto, ProductCreationParams> {

    ProductCreationParams apply(ProductRegistrationRequestDto dto);
}
