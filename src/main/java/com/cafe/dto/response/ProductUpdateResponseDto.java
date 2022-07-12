package com.cafe.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProductUpdateResponseDto {

    private String name;
    private Integer price;
    private Integer amount;
    private LocalDateTime updatedAt;
    private HttpStatus httpStatus;

    public ProductUpdateResponseDto() {
    }

    public ProductUpdateResponseDto(String name, Integer price, Integer amount, LocalDateTime updatedAt, HttpStatus httpStatus) {
        setName(name);
        setPrice(price);
        setAmount(amount);
        setUpdatedAt(updatedAt);
        setHttpStatus(httpStatus);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.notNull(name, "name should not be null");
        Assert.hasText(name, "name should not be empty");
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
        if(amount < 0) {
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        Assert.notNull(httpStatus, "http status should not be null");
        this.httpStatus = httpStatus;
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
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, amount, updatedAt, httpStatus);
    }

    @Override
    public String toString() {
        return "ProductUpdateResponseDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
