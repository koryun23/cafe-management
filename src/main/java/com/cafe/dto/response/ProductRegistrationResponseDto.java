package com.cafe.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ProductRegistrationResponseDto {

    private String name;
    private Integer price;
    private Integer amount;
    private LocalDateTime registeredAt;
    private HttpStatus httpStatus;

    public ProductRegistrationResponseDto(String name,
                                          Integer price,
                                          Integer amount,
                                          LocalDateTime registeredAt,
                                          HttpStatus httpStatus) {
        setName(name);
        setPrice(price);
        setAmount(amount);
        setRegisteredAt(registeredAt);
        setHttpStatus(httpStatus);
    }

    public ProductRegistrationResponseDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.notNull(name, "Product name should not be null");
        Assert.hasText(name, "Product name should not be empty");
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        Assert.notNull(price, "price should not be null");
        if(price <= 0) {
            throw new IllegalArgumentException("price should be > 0");
        }
        this.price = price;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        Assert.notNull(registeredAt, "registration date should not be null");
        this.registeredAt = registeredAt;
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRegistrationResponseDto that = (ProductRegistrationResponseDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(registeredAt, that.registeredAt) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, amount, registeredAt, httpStatus);
    }

    @Override
    public String toString() {
        return "ProductRegistrationResponseDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
