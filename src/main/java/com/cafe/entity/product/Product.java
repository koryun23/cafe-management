package com.cafe.entity.product;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_SEQUENCE")
    @SequenceGenerator(name = "PRODUCT_ID_SEQUENCE")
    private Long id;

    @Column(name = "product_name", nullable = false, unique = true, length = 70)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    public Product() {
    }

    public Product(String name, Integer price, Integer amount, LocalDateTime registeredAt) {
        setName(name);
        setPrice(price);
        setAmount(amount);
        setRegisteredAt(registeredAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.notNull(name, "Product name should not be null");
        Assert.hasText(name, "Product name should not be empty");
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        Assert.notNull(price, "Price should not be null");
        if(price <= 0) {
            throw new IllegalArgumentException("Price should be > 0");
        }
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        Assert.notNull(amount, "Amount should not be null");
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount should be > 0");
        }
        this.amount = amount;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        Assert.notNull(registeredAt, "Registration date should not be null");
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(amount, product.amount) &&
                Objects.equals(registeredAt, product.registeredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, amount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
