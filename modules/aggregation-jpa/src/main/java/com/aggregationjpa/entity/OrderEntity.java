package com.aggregationjpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CurrentTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_sequence", allocationSize = 1)
    Long id;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    @ToString.Include
    BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CurrentTimestamp
    Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    List<OrderItemEntity> orderItems;
}
