package com.cafe.mapper.product;

import com.cafe.dto.request.ProductInOrderUpdateRequestDto;
import com.cafe.service.core.product.ProductInOrderUpdateParams;
import org.springframework.stereotype.Component;

@Component
public class ProductInOrderUpdateParamsMapperImpl implements ProductInOrderUpdateParamsMapper{

    @Override
    public ProductInOrderUpdateParams apply(ProductInOrderUpdateRequestDto dto) {
        return new ProductInOrderUpdateParams(
                dto.getId(),
                dto.getProductName(),
                dto.getOrderId(),
                dto.getAmount(),
                dto.getStatus()
        );
    }
}
