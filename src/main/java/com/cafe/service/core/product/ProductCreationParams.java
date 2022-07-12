package com.cafe.service.core.product;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProductCreationParams {

    private String productName;
    private Integer price;
    private Integer amount;
    private LocalDateTime registeredAt;

    public ProductCreationParams(String productName, Integer price, Integer amount, LocalDateTime registeredAt) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.registeredAt = registeredAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCreationParams that = (ProductCreationParams) o;
        return Objects.equals(productName, that.productName) &&
                Objects.equals(price, that.price) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(registeredAt, that.registeredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, amount, registeredAt);
    }

    @Override
    public String toString() {
        return "ProductCreationParams{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
