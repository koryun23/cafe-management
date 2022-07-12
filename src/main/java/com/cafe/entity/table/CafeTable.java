package com.cafe.entity.table;

import com.cafe.entity.order.Order;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TABLES")
public class CafeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAFE_TABLE_SEQUENCE")
    @SequenceGenerator(name = "CAFE_TABLE_SEQUENCE")
    private Long id;

    @OneToOne(mappedBy = "table", cascade = CascadeType.PERSIST)
    private Order order;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CafeTableStatusType cafeTableStatusType;

    @Column(name = "seats", nullable = false)
    private Integer numberOfSeats;

    @Column(name = "code", nullable = false, length = 8, unique = true)
    private String code;

    public CafeTable() {
    }

    public CafeTable(CafeTableStatusType cafeTableStatusType,
                     Integer numberOfSeats,
                     String code) {
        setCafeTableStatusType(cafeTableStatusType);
        setNumberOfSeats(numberOfSeats);
        setCode(code);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        Assert.notNull(order, "Order should not be null");
        this.order = order;
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

    public CafeTableStatusType getCafeTableStatusType() {
        return cafeTableStatusType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        Assert.notNull(code, "Code should not be null");
        Assert.notNull(code, "Code should not be empty");
        this.code = code;
    }

    public void setCafeTableStatusType(CafeTableStatusType cafeTableStatusType) {
        Assert.notNull(cafeTableStatusType, "Cafe table status type should not be null");
        this.cafeTableStatusType = cafeTableStatusType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CafeTable cafeTable = (CafeTable) o;
        return Objects.equals(id, cafeTable.id) && Objects.equals(order, cafeTable.order) && cafeTableStatusType == cafeTable.cafeTableStatusType && Objects.equals(numberOfSeats, cafeTable.numberOfSeats) && Objects.equals(code, cafeTable.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, cafeTableStatusType, numberOfSeats, code);
    }

    @Override
    public String toString() {
        return "CafeTable{" +
                "id=" + id +
                ", cafeTableStatusType=" + cafeTableStatusType +
                ", numberOfSeats=" + numberOfSeats +
                ", code='" + code + '\'' +
                '}';
    }
}
