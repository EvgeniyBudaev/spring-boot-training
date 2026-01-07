package com.aggregationjpa.controller.dto.response;

import java.time.Instant;

public record ResponseCartItemDto(
        Long id,
        ResponseItemDto item,
        int quantity,
        Instant createdAt
) {
}
