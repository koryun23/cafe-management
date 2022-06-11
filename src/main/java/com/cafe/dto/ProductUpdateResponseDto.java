package com.cafe.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProductUpdateResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private Integer amount;
    private LocalDateTime updatedAt;

    public ProductUpdateResponseDto(Long id, String name, Integer price, Integer amount, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductUpdateResponseDto that = (ProductUpdateResponseDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, amount, updatedAt, id);
    }

    @Override
    public String toString() {
        return "ProductUpdateResponseDto{" +
                "id='" + id + '\'' +
                ",name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
