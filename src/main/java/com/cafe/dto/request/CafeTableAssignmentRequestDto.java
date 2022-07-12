package com.cafe.dto.request;

import org.springframework.util.Assert;

import java.util.Objects;

public class CafeTableAssignmentRequestDto {

    private Long cafeTableId;
    private String waiterUsername;

    public CafeTableAssignmentRequestDto(Long tableId, String waiterUsername) {
        setCafeTableId(tableId);
        setWaiterUsername(waiterUsername);
    }

    public CafeTableAssignmentRequestDto() {
    }

    public Long getCafeTableId() {
        return cafeTableId;
    }

    public void setCafeTableId(Long tableId) {
        Assert.notNull(tableId, "Table id should not be null");
        this.cafeTableId = tableId;
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
        CafeTableAssignmentRequestDto that = (CafeTableAssignmentRequestDto) o;
        return Objects.equals(cafeTableId, that.cafeTableId) &&
                Objects.equals(waiterUsername, that.waiterUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableId, waiterUsername);
    }

    @Override
    public String toString() {
        return "CafeTableAssignmentRequestDto{" +
                "tableId=" + cafeTableId +
                ", waiterUsername=" + waiterUsername +
                '}';
    }
}
