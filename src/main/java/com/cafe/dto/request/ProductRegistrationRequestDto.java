package com.cafe.dto.request;

import org.springframework.util.Assert;

import java.util.Objects;

public class ProductRegistrationRequestDto {

    private String name;
    private Integer price;
    private Integer amount;

    public ProductRegistrationRequestDto(String name, Integer price, Integer amount) {
        setName(name);
        setPrice(price);
        setAmount(amount);
    }

    public ProductRegistrationRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.notNull(name, "Name should not be null");
        Assert.hasText(name, "Name should not be empty");
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        Assert.notNull(price, "Price should not be null");
        if(price <= 0) {
            throw new IllegalArgumentException("Price should be > 0");
        }
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRegistrationRequestDto that = (ProductRegistrationRequestDto) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, amount);
    }

    @Override
    public String toString() {
        return "ProductRegistrationRequestDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
