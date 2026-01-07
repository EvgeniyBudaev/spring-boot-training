package com.aggregationjpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CurrentTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_items")
@Data
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_id_seq")
    @SequenceGenerator(name = "order_items_id_seq", sequenceName = "order_items_id_seq", allocationSize = 1)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_items_order")
    )
    @ToString.Exclude
    private OrderEntity order;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "quantity", nullable = false)
    int quantity;

    @Column(name = "price_at_order", nullable = false, precision = 19, scale = 2)
    @ToString.Include
    BigDecimal priceAtOrder;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CurrentTimestamp
    Instant createdAt = Instant.now();
}
