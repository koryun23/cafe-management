package com.cafe.dto.response;

import com.cafe.entity.order.OrderStatusType;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderRegistrationResponseDto {

    private Long cafeTableId;
    private OrderStatusType orderStatusType;
    private LocalDateTime registeredAt;
    private HttpStatus httpStatus;

    public OrderRegistrationResponseDto(Long cafeTableId,
                                        OrderStatusType orderStatusType,
                                        LocalDateTime registeredAt,
                                        HttpStatus httpStatus) {
        setCafeTableId(cafeTableId);
        setOrderStatusType(orderStatusType);
        setRegisteredAt(registeredAt);
        setHttpStatus(httpStatus);
    }

    public OrderRegistrationResponseDto() {
    }

    public Long getCafeTableId() {
        return cafeTableId;
    }

    public void setCafeTableId(Long cafeTableId) {
        Assert.notNull(cafeTableId, "Cafe table id should not be null");
        this.cafeTableId = cafeTableId;
    }

    public OrderStatusType getOrderStatusType() {
        return orderStatusType;
    }

    public void setOrderStatusType(OrderStatusType orderStatusType) {
        Assert.notNull(orderStatusType, "order status type should not be null");
        this.orderStatusType = orderStatusType;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        Assert.notNull(registeredAt, "registration date should not be null");
        this.registeredAt = registeredAt;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        Assert.notNull(httpStatus, "Http status should not be null");
        this.httpStatus = httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRegistrationResponseDto that = (OrderRegistrationResponseDto) o;
        return Objects.equals(cafeTableId, that.cafeTableId) &&
                orderStatusType == that.orderStatusType &&
                Objects.equals(registeredAt, that.registeredAt) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableId, orderStatusType, registeredAt, httpStatus);
    }

    @Override
    public String toString() {
        return "OrderRegistrationResponseDto{" +
                "cafeTableId=" + cafeTableId +
                ", orderStatusType=" + orderStatusType +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
