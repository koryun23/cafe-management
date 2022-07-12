package com.cafe.service.impl.product;

public class ProductInOrderNotFoundException extends RuntimeException {
    public ProductInOrderNotFoundException(Long id) {
        super(String.format("Could not find a product in order with an id of %d", id));
    }
}
