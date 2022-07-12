package com.cafe.dto.response.error;

import com.cafe.dto.response.OrderRegistrationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class ErrorOrderRegistrationResponseDto extends OrderRegistrationResponseDto {

    private List<String> errors;
    private HttpStatus status;

    public ErrorOrderRegistrationResponseDto(List<String> errors, HttpStatus httpStatus) {
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
        return status;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        Assert.notNull(httpStatus, "Http status should not be null");
        this.status = httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ErrorOrderRegistrationResponseDto that = (ErrorOrderRegistrationResponseDto) o;
        return Objects.equals(errors, that.errors) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), errors, status);
    }

    @Override
    public String toString() {
        return "ErrorOrderRegistrationResponseDto{" +
                "errors=" + errors +
                ", status=" + status +
                '}';
    }
}
