package com.cafe.service.core.product;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProductInOrderCreationParams {

    private String productName;

    private Long orderId;

    private Integer amount;

    private LocalDateTime createdAt;

    public ProductInOrderCreationParams(String productName,
                                        Long orderId,
                                        Integer amount,
                                        LocalDateTime createdAt) {
        this.productName = productName;
        this.orderId = orderId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrderCreationParams that = (ProductInOrderCreationParams) o;
        return Objects.equals(productName, that.productName) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, orderId, amount, createdAt);
    }

    @Override
    public String toString() {
        return "ProductInOrderCreationParams{" +
                "productName='" + productName + '\'' +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
