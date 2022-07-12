package com.cafe.dto.request;

import com.cafe.entity.order.OrderStatusType;
import org.springframework.util.Assert;

import java.util.Objects;

public class OrderUpdateRequestDto {

    private Long id;
    private Long cafeTableId;
    private OrderStatusType orderStatusType;
    private String waiterUsername;

    public OrderUpdateRequestDto(Long id,
                                 Long cafeTableId,
                                 OrderStatusType orderStatusType,
                                 String waiterUsername) {
        setId(id);
        setCafeTableId(cafeTableId);
        setOrderStatusType(orderStatusType);
        setWaiterUsername(waiterUsername);
    }

    public OrderUpdateRequestDto(Long cafeTableId, OrderStatusType orderStatusType) {
        setCafeTableId(cafeTableId);
        setOrderStatusType(orderStatusType);
    }

    public OrderUpdateRequestDto() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        Assert.notNull(waiterUsername, "Waiter username should not be null");
        Assert.hasText(waiterUsername, "Waiter username should not be empty");
        this.waiterUsername = waiterUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderUpdateRequestDto that = (OrderUpdateRequestDto) o;
        return Objects.equals(cafeTableId, that.cafeTableId) &&
                orderStatusType == that.orderStatusType &&
                Objects.equals(id, that.id) &&
                Objects.equals(waiterUsername, that.waiterUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableId, orderStatusType, id);
    }

    @Override
    public String toString() {
        return "OrderUpdateRequestDto{" +
                "id=" + id +
                ",cafeTableId=" + cafeTableId +
                ", orderStatusType=" + orderStatusType +
                ", waiterUsername=" + waiterUsername +
                '}';
    }
}
