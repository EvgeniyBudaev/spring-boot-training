package com.aggregationjpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "items")
@Data
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_id_seq")
    @SequenceGenerator(name = "items_id_seq", sequenceName = "items_id_seq", allocationSize = 1)
    Long id;

    @Column(name = "title", nullable = false, unique = true)
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "img_path")
    String imgPath;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    BigDecimal price;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
