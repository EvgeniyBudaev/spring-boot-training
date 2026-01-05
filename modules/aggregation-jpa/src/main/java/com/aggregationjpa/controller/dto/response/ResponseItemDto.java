package com.aggregationjpa.controller.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record ResponseItemDto(
        Long id,
        String title,
        String description,
        String imgPath,
        BigDecimal price,
        Instant createdAt
) {
}
