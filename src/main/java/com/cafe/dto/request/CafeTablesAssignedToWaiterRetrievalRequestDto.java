package com.cafe.dto.request;

import io.jsonwebtoken.lang.Assert;

import java.util.Objects;

public class CafeTablesAssignedToWaiterRetrievalRequestDto {

    private String waiterUsername;

    public CafeTablesAssignedToWaiterRetrievalRequestDto() {
    }

    public CafeTablesAssignedToWaiterRetrievalRequestDto(String waiterUsername) {
        setWaiterUsername(waiterUsername);
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
        CafeTablesAssignedToWaiterRetrievalRequestDto that = (CafeTablesAssignedToWaiterRetrievalRequestDto) o;
        return Objects.equals(waiterUsername, that.waiterUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waiterUsername);
    }

    @Override
    public String toString() {
        return "CafeTableListRetrievalRequestDto{" +
                "waiterUsername=" + waiterUsername +
                '}';
    }
}
