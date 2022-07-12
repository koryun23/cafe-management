package com.cafe.entity.order;

import com.cafe.entity.product.ProductInOrder;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ID_SEQUENCE")
    @SequenceGenerator(name = "ORDER_ID_SEQUENCE")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<ProductInOrder> productsInOrder;

    @OneToOne
    @JoinColumn(name = "cafe_table_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "ORDER_CAFE_TABLE_ID"))
    private CafeTable table;

    @ManyToOne
    @JoinColumn(name = "waiter_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "ORDER_USER_ID"))
    private User waiter;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatusType orderStatusType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Order() {
    }

    public Order(CafeTable table, User waiter, OrderStatusType orderStatusType, LocalDateTime createdAt) {
        setTable(table);
        setWaiter(waiter);
        setOrderStatusType(orderStatusType);
        setCreatedAt(createdAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public List<ProductInOrder> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(List<ProductInOrder> productsInOrder) {
        Assert.notNull(productsInOrder, "Product in order list should not be null");
        this.productsInOrder = productsInOrder;
    }

    public CafeTable getTable() {
        return table;
    }

    public void setTable(CafeTable table) {
        Assert.notNull(table, "Cafe table should not be null");
        this.table = table;
    }

    public OrderStatusType getOrderStatusType() {
        return orderStatusType;
    }

    public void setOrderStatusType(OrderStatusType orderStatusType) {
        Assert.notNull(orderStatusType, "order status type should not be null");
        this.orderStatusType = orderStatusType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        Assert.notNull(createdAt, "creation date should not be null");
        this.createdAt = createdAt;
    }

    public User getWaiter() {
        return waiter;
    }

    public void setWaiter(User waiter) {
        Assert.notNull(waiter, "Waiter should not be null");
        this.waiter = waiter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(productsInOrder, order.productsInOrder) &&
                Objects.equals(table, order.table) && orderStatusType == order.orderStatusType &&
                Objects.equals(createdAt, order.createdAt) &&
                Objects.equals(waiter, order.waiter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productsInOrder, table, waiter, orderStatusType, createdAt);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                //", productsInOrder=" + productsInOrder +
                ", table=" + table +
                ", waiter=" + waiter +
                ", orderStatusType=" + orderStatusType +
                ", createdAt=" + createdAt +
                '}';
    }
}
