package com.cafe.service.core.product;

import com.cafe.entity.product.ProductInOrder;
import com.cafe.entity.product.ProductInOrderStatusType;

public interface ProductInOrderService {

    ProductInOrder create(ProductInOrderCreationParams params);

    ProductInOrder update(ProductInOrderUpdateParams params);

    void markAllAs(Long orderId, ProductInOrderStatusType status);
}
