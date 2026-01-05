package com.aggregation.controller.dto.response;

import java.time.Instant;

public record ResponseCatalogFindByIdDto(
        Long id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
