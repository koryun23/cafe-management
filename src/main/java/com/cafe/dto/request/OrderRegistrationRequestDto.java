package com.cafe.dto.request;

import com.cafe.entity.order.OrderStatusType;
import org.springframework.util.Assert;

import java.util.Objects;

public class OrderRegistrationRequestDto {

    private Long cafeTableId;
    private OrderStatusType status;
    private String waiterUsername;

    public OrderRegistrationRequestDto(Long cafeTableId, String waiterUsername) {
        setCafeTableId(cafeTableId);
        setWaiterUsername(waiterUsername);
        setStatus(OrderStatusType.OPEN);
    }

    public OrderRegistrationRequestDto() {
    }

    public Long getCafeTableId() {
        return cafeTableId;
    }

    public void setCafeTableId(Long cafeTableId) {
        Assert.notNull(cafeTableId, "Cafe table id should not be null");
        this.cafeTableId = cafeTableId;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        Assert.notNull(status, "order status type should not be null");
        this.status = status;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        Assert.notNull(waiterUsername, "waiter username should not be null");
        Assert.hasText(waiterUsername, "waiter username should not be empty");
        this.waiterUsername = waiterUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRegistrationRequestDto that = (OrderRegistrationRequestDto) o;
        return Objects.equals(cafeTableId, that.cafeTableId) &&
                status == that.status &&
                Objects.equals(waiterUsername, that.waiterUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableId, status, waiterUsername);
    }

    @Override
    public String toString() {
        return "OrderRegistrationRequestDto{" +
                "cafeTableId=" + cafeTableId +
                ", status=" + status +
                '}';
    }
}
