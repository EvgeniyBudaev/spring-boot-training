package com.aggregationkeycloak.controller.dto.response;

import java.time.Instant;

public record ResponseCatalogCreateDto(
        Long id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
