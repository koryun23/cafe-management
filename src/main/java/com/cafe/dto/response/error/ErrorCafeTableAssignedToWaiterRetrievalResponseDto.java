package com.cafe.dto.response.error;

import com.cafe.dto.response.CafeTablesAssignedToWaiterRetrievalResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class ErrorCafeTableAssignedToWaiterRetrievalResponseDto extends CafeTablesAssignedToWaiterRetrievalResponseDto {

    private List<String> errors;
    private HttpStatus httpStatus;

    public ErrorCafeTableAssignedToWaiterRetrievalResponseDto(List<String> errors, HttpStatus httpStatus) {
        setErrors(errors);
        setHttpStatus(httpStatus);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        Assert.notNull(errors, "error list should not be null");
        this.errors = errors;
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
        ErrorCafeTableAssignedToWaiterRetrievalResponseDto that = (ErrorCafeTableAssignedToWaiterRetrievalResponseDto) o;
        return Objects.equals(errors, that.errors) && httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors, httpStatus);
    }

    @Override
    public String toString() {
        return "ErrorCafeTableAssignedToWaiterRetrievalResponseDto{" +
                "errors=" + errors +
                ", httpStatus=" + httpStatus +
                '}';
    }
}
