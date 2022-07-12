package com.cafe.dto.response;

import com.cafe.entity.table.CafeTableAssignedToWaiter;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class CafeTablesAssignedToWaiterRetrievalResponseDto {

    private List<CafeTableAssignedToWaiter> cafeTableAssignedToWaiterList;
    private HttpStatus httpStatus;

    public CafeTablesAssignedToWaiterRetrievalResponseDto(List<CafeTableAssignedToWaiter> cafeTableAssignedToWaiterList,
                                                          HttpStatus httpStatus) {
        setCafeTableAssignedToWaiterList(cafeTableAssignedToWaiterList);
    }

    public CafeTablesAssignedToWaiterRetrievalResponseDto() {
    }

    public List<CafeTableAssignedToWaiter> getCafeTableAssignedToWaiterList() {
        return cafeTableAssignedToWaiterList;
    }

    public void setCafeTableAssignedToWaiterList(List<CafeTableAssignedToWaiter> cafeTableAssignedToWaiterList) {
        Assert.notNull(cafeTableAssignedToWaiterList, "List of cafe tables assigned to waiter should not be null");
        this.cafeTableAssignedToWaiterList = cafeTableAssignedToWaiterList;
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
        CafeTablesAssignedToWaiterRetrievalResponseDto that = (CafeTablesAssignedToWaiterRetrievalResponseDto) o;
        return Objects.equals(cafeTableAssignedToWaiterList, that.cafeTableAssignedToWaiterList) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableAssignedToWaiterList, httpStatus);
    }

    @Override
    public String toString() {
        return "CafeTablesAssignedToWaiterRetrievalResponseDto{" +
                "cafeTableAssignedToWaiterList=" + cafeTableAssignedToWaiterList +
                '}';
    }
}
