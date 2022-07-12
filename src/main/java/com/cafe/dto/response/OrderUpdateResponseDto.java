package com.cafe.dto.response;

import com.cafe.entity.order.OrderStatusType;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderUpdateResponseDto {

    private Long id;
    private Long cafeTableId;
    private OrderStatusType orderStatusType;
    private LocalDateTime updatedAt;
    private HttpStatus httpStatus;

    public OrderUpdateResponseDto(Long id,
                                  Long cafeTableId,
                                  OrderStatusType orderStatusType,
                                  LocalDateTime updatedAt,
                                  HttpStatus httpStatus) {
        setId(id);
        setCafeTableId(cafeTableId);
        setOrderStatusType(orderStatusType);
        setUpdatedAt(updatedAt);
        setHttpStatus(httpStatus);
    }

    public OrderUpdateResponseDto() {
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
        Assert.notNull(orderStatusType, "Order status type should not be null");
        this.orderStatusType = orderStatusType;
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
        this.httpStatus = httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderUpdateResponseDto that = (OrderUpdateResponseDto) o;
        return Objects.equals(cafeTableId, that.cafeTableId) &&
                orderStatusType == that.orderStatusType &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(id, that.id) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableId, orderStatusType, updatedAt, id, httpStatus);
    }

    @Override
    public String toString() {
        return "OrderUpdateResponseDto{" +
                "id=" + id +
                ", cafeTableId=" + cafeTableId +
                ", orderStatusType=" + orderStatusType +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
