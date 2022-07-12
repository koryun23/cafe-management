package com.cafe.service.core.product;

import com.cafe.entity.product.Product;

import java.util.Optional;

public interface ProductService {
    Product create(ProductCreationParams params);

    Product getById(Long id);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Integer getAmountByProductName(String name);

    Integer getAmountByProductId(Long id);

    Product updateProduct(ProductUpdateParams params);

    Product getByName(String name);
}
