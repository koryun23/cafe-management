package com.cafe.controller;

import com.cafe.dto.request.ProductRegistrationRequestDto;
import com.cafe.dto.response.ProductRegistrationResponseDto;
import com.cafe.dto.request.ProductUpdateRequestDto;
import com.cafe.dto.response.ProductUpdateResponseDto;
import com.cafe.facade.core.product.ProductFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "products", consumes = "application/json", produces = "application/json")
public class ProductController {

    private final ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ProductRegistrationResponseDto> registerProduct(@RequestBody ProductRegistrationRequestDto dto) {
        ProductRegistrationResponseDto responseDto = productFacade.registerProduct(dto);
        return ResponseEntity
                .status(responseDto.getHttpStatus())
                .body(responseDto);
    }

    @PutMapping(path = "/update/{originalName}")
    public ResponseEntity<ProductUpdateResponseDto> updateProduct(@RequestBody ProductUpdateRequestDto dto,
                                                                  @PathVariable String originalName) {
        dto.setOriginalName(originalName);
        ProductUpdateResponseDto responseDto = productFacade.updateProduct(dto);
        return ResponseEntity
                .status(responseDto.getHttpStatus())
                .body(responseDto);
    }
}
