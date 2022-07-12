package com.cafe.dto.response;

import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CafeTableAssignmentResponseDto {

    private Long tableId;
    private String waiterUsername;
    private LocalDateTime assignedAt;
    private HttpStatus httpStatus;

    public CafeTableAssignmentResponseDto(Long tableId,
                                          String waiterUsername,
                                          LocalDateTime assignedAt,
                                          HttpStatus httpStatus) {
        setTableId(tableId);
        setWaiterUsername(waiterUsername);
        setAssignedAt(assignedAt);
        setHttpStatus(httpStatus);
    }

    public CafeTableAssignmentResponseDto() {
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        Assert.notNull(tableId, "Table id should not be null");
        this.tableId = tableId;
    }

    public String getWaiterUsername() {
        return waiterUsername;
    }

    public void setWaiterUsername(String waiterUsername) {
        Assert.notNull(waiterUsername, "Waiter username should not be null");
        Assert.hasText(waiterUsername, "Waiter username should not be empty");
        this.waiterUsername = waiterUsername;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        Assert.notNull(assignedAt, "Assignation date object should not be null");
        this.assignedAt = assignedAt;
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
        CafeTableAssignmentResponseDto that = (CafeTableAssignmentResponseDto) o;
        return Objects.equals(tableId, that.tableId) &&
                Objects.equals(waiterUsername, that.waiterUsername) &&
                Objects.equals(assignedAt, that.assignedAt) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, waiterUsername, assignedAt, httpStatus);
    }

    @Override
    public String toString() {
        return "CafeTableAssignmentResponseDto{" +
                "tableId=" + tableId +
                ", waiterUsername=" + waiterUsername +
                ", assignedAt=" + assignedAt +
                '}';
    }


}
