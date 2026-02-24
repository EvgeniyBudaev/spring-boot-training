package com.aggregationkeycloak.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record RequestCatalogUpdateDto(
        @Null
        Long id,
        @NotNull
        String name,
        String description
) {
}
