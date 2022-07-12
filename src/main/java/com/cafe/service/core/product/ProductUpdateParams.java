package com.cafe.service.core.product;

import java.util.Objects;

public class ProductUpdateParams {
    private String originalName;
    private String name;
    private Integer amount;
    private Integer price;

    public ProductUpdateParams(String originalName, String name, Integer amount, Integer price) {
        this.originalName = originalName;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductUpdateParams{" +
                "originalName=" + originalName +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductUpdateParams that = (ProductUpdateParams) o;
        return Objects.equals(originalName, that.originalName) && Objects.equals(name, that.name) && Objects.equals(amount, that.amount) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalName, name, amount, price);
    }
}
