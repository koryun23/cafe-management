package com.cafe.service.core.order;

import com.cafe.entity.order.OrderStatusType;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderUpdateParams {
    private Long id;
    private Long cafeTableId;
    private OrderStatusType orderStatusType;
    private LocalDateTime updatedAt;

    public OrderUpdateParams(Long id,
                             Long cafeTableId,
                             OrderStatusType orderStatusType,
                             LocalDateTime updatedAt) {
        this.id = id;
        this.cafeTableId = cafeTableId;
        this.orderStatusType = orderStatusType;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "OrderUpdateParams{" +
                "id=" + id +
                ", cafeTableId=" + cafeTableId +
                ", orderStatusType=" + orderStatusType +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderUpdateParams that = (OrderUpdateParams) o;
        return Objects.equals(id, that.id) && Objects.equals(cafeTableId, that.cafeTableId) && orderStatusType == that.orderStatusType && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cafeTableId, orderStatusType, updatedAt);
    }
}
