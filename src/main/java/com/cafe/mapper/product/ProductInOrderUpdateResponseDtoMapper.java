package com.cafe.mapper.product;

import com.cafe.dto.response.ProductInOrderUpdateResponseDto;
import com.cafe.entity.product.ProductInOrder;

import java.util.function.Function;

public interface ProductInOrderUpdateResponseDtoMapper extends Function<ProductInOrder, ProductInOrderUpdateResponseDto> {
}
