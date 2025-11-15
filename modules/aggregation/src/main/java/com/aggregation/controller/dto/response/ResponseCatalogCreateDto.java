package com.aggregation.controller.dto.response;

import java.io.Serializable;
import java.time.Instant;

public record ResponseCatalogCreateDto(
        Long id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
) implements Serializable {
}
