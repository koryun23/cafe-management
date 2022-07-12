package com.cafe.dto.response;

import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ProductInOrderUpdateResponseDto {

    private Long id;
    private String productName;
    private Long orderId;
    private Integer amount;
    private LocalDateTime updatedAt;
    private HttpStatus httpStatus;

    public ProductInOrderUpdateResponseDto(Long id,
                                           String productName,
                                           Long orderId,
                                           Integer amount,
                                           LocalDateTime updatedAt,
                                           HttpStatus httpStatus) {
        setId(id);
        setProductName(productName);
        setOrderId(orderId);
        setAmount(amount);
        setUpdatedAt(updatedAt);
        setHttpStatus(httpStatus);
    }

    public ProductInOrderUpdateResponseDto() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        Assert.notNull(productName, "Product name should not be null");
        Assert.hasText(productName, "Product name should not be empty");
        this.productName = productName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        Assert.notNull(orderId, "Order id should not be null");
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        Assert.notNull(amount, "Amount should not be null");
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount should be > 0");
        }
        this.amount = amount;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        Assert.notNull(updatedAt, "Updating date should not be null");
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        Assert.notNull(httpStatus, "Http status should not be null");
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "ProductInOrderUpdateResponseDto{" +
                "id=" + id +
                ", productName=" + productName +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrderUpdateResponseDto that = (ProductInOrderUpdateResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, orderId, amount, updatedAt, httpStatus);
    }
}
