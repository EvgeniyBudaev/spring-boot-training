package com.aggregationkeycloak.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CatalogEntity {
    private Long id;
    private String name;
    private String description;
    private UUID createdBy;
    private Instant createdAt;
    private Instant updatedAt;
}
