package com.cafe.mapper.product;

import com.cafe.dto.request.ProductInOrderUpdateRequestDto;
import com.cafe.service.core.product.ProductInOrderUpdateParams;

import java.util.function.Function;

public interface ProductInOrderUpdateParamsMapper extends Function<ProductInOrderUpdateRequestDto, ProductInOrderUpdateParams> {
}
