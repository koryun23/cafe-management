package com.cafe.repository;

import com.cafe.entity.product.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {

    List<ProductInOrder> findAllByOrderId(Long orderId);
}
