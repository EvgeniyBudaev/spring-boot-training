package com.aggregationkeycloak.controller.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ResponseCatalogFindByIdDto(
        Long id,
        String name,
        String description,
        UUID createdBy,
        Instant createdAt,
        Instant updatedAt
) {
}
