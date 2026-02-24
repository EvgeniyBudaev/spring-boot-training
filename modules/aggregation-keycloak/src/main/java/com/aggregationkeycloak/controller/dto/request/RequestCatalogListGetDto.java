package com.aggregationkeycloak.controller.dto.request;

import jakarta.validation.constraints.Null;

public record RequestCatalogListGetDto(
        @Null
        Integer page,
        @Null
        Integer size
) {
}
