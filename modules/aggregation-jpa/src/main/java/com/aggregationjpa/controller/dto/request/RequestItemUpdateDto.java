package com.aggregationjpa.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RequestItemUpdateDto(
        @NotBlank String title,
        String description,
        String imgPath,
        @NotNull @PositiveOrZero BigDecimal price
) {
}
