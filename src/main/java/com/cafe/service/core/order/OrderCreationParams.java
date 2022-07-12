package com.cafe.service.core.order;

import com.cafe.entity.order.OrderStatusType;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderCreationParams {

    private Long cafeTableId;
    private String waiterUsername;
    private OrderStatusType orderStatusType;
    private LocalDateTime createdAt;

    public OrderCreationParams(Long cafeTableId, String waiterUsername, OrderStatusType orderStatusType, LocalDateTime createdAt) {
        this.cafeTableId = cafeTableId;
        this.waiterUsername = waiterUsername;
        this.orderStatusType = orderStatusType;
        this.createdAt = createdAt;
    }

    public Long getCafeTableId() {
        return cafeTableId;
    }

    public void setCafeTableId(Long cafeTableId) {
        this.cafeTableId = cafeTableId;
    }

    public OrderStatusType getOrderStatusType() {
        return orderStatusType;
    }

    public void setOrderStatusType(OrderStatusType orderStatusType) {
        this.orderStatusType = orderStatusType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        this.waiterUsername = waiterUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderCreationParams that = (OrderCreationParams) o;
        return Objects.equals(cafeTableId, that.cafeTableId) && Objects.equals(waiterUsername, that.waiterUsername) && orderStatusType == that.orderStatusType && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableId, waiterUsername, orderStatusType, createdAt);
    }

    @Override
    public String toString() {
        return "OrderCreationParams{" +
                "cafeTableId=" + cafeTableId +
                ", waiterUsername='" + waiterUsername + '\'' +
                ", orderStatusType=" + orderStatusType +
                ", createdAt=" + createdAt +
                '}';
    }
}
