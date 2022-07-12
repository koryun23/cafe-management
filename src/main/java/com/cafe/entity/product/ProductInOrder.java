package com.cafe.entity.product;

import com.cafe.entity.order.Order;
import net.bytebuddy.pool.TypePool;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT_IN_ORDER")
public class ProductInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_IN_ORDER_SEQUENCE")
    @SequenceGenerator(name = "PRODUCT_IN_ORDER_SEQUENCE")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PRODUCT_IN_ORDER_PRODUCT_PRODUCT_ID"), nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PRODUCT_IN_ORDER_ORDER_ORDER_ID"), nullable = false)
    private Order order;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProductInOrderStatusType productInOrderStatusType;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    public ProductInOrder(Product product, Order order, Integer amount, LocalDateTime registeredAt) {
        setProduct(product);
        setOrder(order);
        setAmount(amount);
        setRegisteredAt(registeredAt);
    }

    public ProductInOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        Assert.notNull(product, "Product should not be null");
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        Assert.notNull(order, "Order should not be null");
        this.order = order;
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

    public ProductInOrderStatusType getProductInOrderStatusType() {
        return productInOrderStatusType;
    }

    public void setProductInOrderStatusType(ProductInOrderStatusType productInOrderStatusType) {
        Assert.notNull(productInOrderStatusType, "Product in order status type should not be null");
        this.productInOrderStatusType = productInOrderStatusType;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        Assert.notNull(registeredAt, "Registration date should not be null");
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "ProductInOrder{" +
                "id=" + id +
                ", product=" + product +
                ", order=" + order +
                ", amount=" + amount +
                ", productInOrderStatusType=" + productInOrderStatusType +
                ", registeredAt=" + registeredAt +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrder that = (ProductInOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(product, that.product) &&
                Objects.equals(order, that.order) &&
                Objects.equals(amount, that.amount) &&
                productInOrderStatusType == that.productInOrderStatusType &&
                Objects.equals(registeredAt, that.registeredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, order, amount, productInOrderStatusType, registeredAt);
    }
}
