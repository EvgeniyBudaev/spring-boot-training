package com.aggregationkeycloak.controller.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.Instant;
import java.util.UUID;

public record RequestCatalogCreateDto(
        @Null
        Long id,
        @NotNull
        String name,
        String description,
        @Null
        @FutureOrPresent
        Instant createdAt,
        @Null
        @FutureOrPresent
        Instant updatedAt
) {
}
