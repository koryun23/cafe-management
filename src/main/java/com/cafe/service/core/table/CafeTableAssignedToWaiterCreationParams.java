package com.cafe.service.core.table;

import java.time.LocalDateTime;
import java.util.Objects;

public class CafeTableAssignedToWaiterCreationParams {

    private Long cafeTableId;
    private String waiterUsername;
    private LocalDateTime assignedAt;

    public CafeTableAssignedToWaiterCreationParams(Long cafeTableId, String waiterUsername, LocalDateTime assignedAt) {
        this.cafeTableId = cafeTableId;
        this.waiterUsername = waiterUsername;
        this.assignedAt = assignedAt;
    }

    public Long getCafeTableId() {
        return cafeTableId;
    }

    public void setCafeTableId(Long cafeTableId) {
        this.cafeTableId = cafeTableId;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterId) {
        this.waiterUsername = waiterUsername;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CafeTableAssignedToWaiterCreationParams that = (CafeTableAssignedToWaiterCreationParams) o;
        return Objects.equals(cafeTableId, that.cafeTableId) &&
                Objects.equals(waiterUsername, that.waiterUsername) &&
                Objects.equals(assignedAt, that.assignedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableId, waiterUsername, assignedAt);
    }

    @Override
    public String toString() {
        return "CafeTableAssignedToWaiterCreationParams{" +
                "cafeTableId=" + cafeTableId +
                ", waiterUsername=" + waiterUsername +
                ", assignedAt=" + assignedAt +
                '}';
    }
}
