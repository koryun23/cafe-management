package com.cafe.dto.response;

import com.cafe.entity.table.CafeTableStatusType;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CafeTableRegistrationResponseDto {

    private CafeTableStatusType cafeTableStatusType;
    private Integer numberOfSeats;
    private String code;
    private LocalDateTime registeredAt;
    private HttpStatus httpStatus;

    public CafeTableRegistrationResponseDto(CafeTableStatusType cafeTableStatusType,
                                            Integer numberOfSeats,
                                            String code,
                                            LocalDateTime registeredAt,
                                            HttpStatus httpStatus) {
        setCafeTableStatusType(cafeTableStatusType);
        setNumberOfSeats(numberOfSeats);
        setRegisteredAt(registeredAt);
        setCode(code);
        setHttpStatus(httpStatus);
    }

    public CafeTableRegistrationResponseDto() {
    }

    public CafeTableStatusType getCafeTableStatusType() {
        return cafeTableStatusType;
    }

    public void setCafeTableStatusType(CafeTableStatusType cafeTableStatusType) {
        Assert.notNull(cafeTableStatusType, "Cafe table status type should not be null");
        this.cafeTableStatusType = cafeTableStatusType;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        Assert.notNull(numberOfSeats, "Number of seats should not be null");
        if(numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats should be > 0");
        }
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        Assert.notNull(registeredAt, "Registration date should not be null");
        this.registeredAt = registeredAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        Assert.notNull(code, "Cafe table code should not be null");
        Assert.hasText(code, "Cafe table code should not be empty");
        this.code = code;
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
        CafeTableRegistrationResponseDto that = (CafeTableRegistrationResponseDto) o;
        return cafeTableStatusType == that.cafeTableStatusType &&
                Objects.equals(numberOfSeats, that.numberOfSeats) &&
                Objects.equals(code, that.code) &&
                Objects.equals(registeredAt, that.registeredAt) &&
                Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableStatusType, numberOfSeats, code, registeredAt, httpStatus);
    }

    @Override
    public String toString() {
        return "CafeTableRegistrationResponseDto{" +
                "cafeTableStatusType=" + cafeTableStatusType +
                ", numberOfSeats=" + numberOfSeats +
                ", code='" + code + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
