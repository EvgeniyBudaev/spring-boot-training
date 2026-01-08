package com.aggregationjpa.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record RequestOrderCreateDto(
        @NotNull
        Long cartId
) {
}
