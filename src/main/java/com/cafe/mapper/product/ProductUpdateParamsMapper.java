package com.cafe.mapper.product;

import com.cafe.dto.request.ProductUpdateRequestDto;
import com.cafe.service.core.product.ProductUpdateParams;

import java.util.function.Function;

public interface ProductUpdateParamsMapper extends Function<ProductUpdateRequestDto, ProductUpdateParams> {

    ProductUpdateParams apply(ProductUpdateRequestDto dto);
}
