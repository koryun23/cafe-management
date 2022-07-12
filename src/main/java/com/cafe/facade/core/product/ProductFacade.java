package com.cafe.facade.core.product;

import com.cafe.dto.request.ProductInOrderRegistrationRequestDto;
import com.cafe.dto.request.ProductInOrderUpdateRequestDto;
import com.cafe.dto.request.ProductRegistrationRequestDto;
import com.cafe.dto.request.ProductUpdateRequestDto;
import com.cafe.dto.response.ProductInOrderRegistrationResponseDto;
import com.cafe.dto.response.ProductInOrderUpdateResponseDto;
import com.cafe.dto.response.ProductRegistrationResponseDto;
import com.cafe.dto.response.ProductUpdateResponseDto;

public interface ProductFacade {
    ProductRegistrationResponseDto registerProduct(ProductRegistrationRequestDto dto);

    ProductInOrderRegistrationResponseDto registerProductInOrder(ProductInOrderRegistrationRequestDto dto);

    ProductInOrderUpdateResponseDto updateProductInOrder(ProductInOrderUpdateRequestDto dto);

    ProductUpdateResponseDto updateProduct(ProductUpdateRequestDto dto);
}
