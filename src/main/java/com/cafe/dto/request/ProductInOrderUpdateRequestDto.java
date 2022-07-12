package com.cafe.dto.request;

import com.cafe.entity.product.ProductInOrderStatusType;
import org.springframework.util.Assert;

import java.util.Objects;

public class ProductInOrderUpdateRequestDto {

    private Long id;
    private String productName;
    private Long orderId;
    private Integer amount;
    private String waiterUsername;
    private ProductInOrderStatusType status;

    public ProductInOrderUpdateRequestDto(Long id,
                                          String productName,
                                          Integer amount,
                                          ProductInOrderStatusType status) {
        setId(id);
        setProductName(productName);
        setAmount(amount);
        setStatus(status);
    }

    public ProductInOrderUpdateRequestDto(Long id,
                                          String productName,
                                          Long orderId,
                                          Integer amount,
                                          String waiterUsername,
                                          ProductInOrderStatusType status) {
        setId(id);
        setProductName(productName);
        setOrderId(orderId);
        setAmount(amount);
        setWaiterUsername(waiterUsername);
        setStatus(status);
    }

    public ProductInOrderUpdateRequestDto() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        Assert.notNull(waiterUsername, "Waiter username should not be null");
        Assert.hasText(waiterUsername, "Waiter username should not be empty");
        this.waiterUsername = waiterUsername;
    }

    public ProductInOrderStatusType getStatus() {
        return status;
    }

    public void setStatus(ProductInOrderStatusType status) {
        Assert.notNull(status, "Product in order status type should not be empty");
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrderUpdateRequestDto that = (ProductInOrderUpdateRequestDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(waiterUsername, that.waiterUsername) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, orderId, amount, waiterUsername, status);
    }

    @Override
    public String toString() {
        return "ProductInOrderUpdateRequestDto{" +
                "id=" + id +
                ", productName=" + productName +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", waiterUsername=" + waiterUsername +
                ", status=" + status +
                '}';
    }
}
