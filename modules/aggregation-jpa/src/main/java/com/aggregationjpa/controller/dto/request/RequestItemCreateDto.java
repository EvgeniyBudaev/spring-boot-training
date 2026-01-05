package com.aggregationjpa.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestItemCreateDto(
        @NotNull
        String title,
        String description,
        String imgPath,
        @NotNull
        BigDecimal price
) {
}
