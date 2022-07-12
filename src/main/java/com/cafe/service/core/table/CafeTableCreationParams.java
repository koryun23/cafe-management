package com.cafe.service.core.table;

import com.cafe.entity.table.CafeTableStatusType;

import java.util.Objects;

public class CafeTableCreationParams {

    private CafeTableStatusType cafeTableStatusType;

    private Integer numberOfSeats;

    private String code;

    public CafeTableCreationParams(CafeTableStatusType cafeTableStatusType,
                                   Integer numberOfSeats,
                                   String code) {
        this.cafeTableStatusType = cafeTableStatusType;
        this.numberOfSeats = numberOfSeats;
        this.code = code;
    }

    public CafeTableStatusType getCafeTableStatusType() {
        return cafeTableStatusType;
    }

    public void setCafeTableStatusType(CafeTableStatusType cafeTableStatusType) {
        this.cafeTableStatusType = cafeTableStatusType;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CafeTableCreationParams that = (CafeTableCreationParams) o;
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
        return "CafeTableCreationParams{" +
                "cafeTableStatusType=" + cafeTableStatusType +
                ", numberOfSeats=" + numberOfSeats +
                ", code=" + code +
                '}';
    }
}
