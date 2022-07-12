package com.cafe.service.impl.product;

import com.cafe.entity.product.ProductInOrder;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("No product found having an id of " + id);
    }

    public ProductNotFoundException(String name) {
        super(String.format("No product found named as '%s'", name));
    }
}
