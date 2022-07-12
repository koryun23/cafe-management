package com.cafe.dto.request;

import org.springframework.util.Assert;

import java.util.Objects;

public class ProductInOrderRegistrationRequestDto {

    private String productName;
    private Long orderId;
    private Integer amount;
    private String waiterUsername;

    public ProductInOrderRegistrationRequestDto(String productName,
                                                Integer amount) {
        setProductName(productName);
        setAmount(amount);
    }

    public ProductInOrderRegistrationRequestDto(String productName,
                                                Long orderId,
                                                Integer amount,
                                                String waiterUsername) {
        setProductName(productName);
        setOrderId(orderId);
        setAmount(amount);
        setWaiterUsername(waiterUsername);
    }

    public ProductInOrderRegistrationRequestDto() {
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

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        Assert.notNull(waiterUsername, "Waiter username should not be null");
        Assert.hasText(waiterUsername, "Waiter username should not be empty");
        this.waiterUsername = waiterUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrderRegistrationRequestDto that = (ProductInOrderRegistrationRequestDto) o;
        return Objects.equals(productName, that.productName) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(waiterUsername, that.waiterUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, orderId, amount, waiterUsername);
    }

    @Override
    public String toString() {
        return "ProductInOrderRegistrationRequestDto{" +
                "productName='" + productName + '\'' +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", waiterUsername=" + waiterUsername +
                '}';
    }
}
