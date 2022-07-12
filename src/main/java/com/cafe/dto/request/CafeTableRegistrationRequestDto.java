package com.cafe.dto.request;

import com.cafe.entity.table.CafeTableStatusType;
import io.jsonwebtoken.lang.Assert;

import java.util.Objects;

public class CafeTableRegistrationRequestDto {

    private CafeTableStatusType cafeTableStatusType;
    private Integer numberOfSeats;
    private String code;

    public CafeTableRegistrationRequestDto(Integer numberOfSeats, String code) {
        setCafeTableStatusType(CafeTableStatusType.FREE);
        setNumberOfSeats(numberOfSeats);
        setCode(code);
    }

    public CafeTableRegistrationRequestDto() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        Assert.notNull(code, "Code should not be null");
        Assert.hasText(code, "Code should not be empty");
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CafeTableRegistrationRequestDto that = (CafeTableRegistrationRequestDto) o;
        return cafeTableStatusType == that.cafeTableStatusType &&
                Objects.equals(numberOfSeats, that.numberOfSeats) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeTableStatusType, numberOfSeats, code);
    }

    @Override
    public String toString() {
        return "CafeTableRegistrationRequestDto{" +
                "cafeTableStatusType=" + cafeTableStatusType +
                ", numberOfSeats=" + numberOfSeats +
                ", code=" + code +
                '}';
    }
}
