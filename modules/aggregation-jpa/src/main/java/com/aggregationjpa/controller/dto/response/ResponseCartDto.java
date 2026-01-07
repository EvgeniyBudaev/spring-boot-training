package com.aggregationjpa.controller.dto.response;

import java.time.Instant;

public record ResponseCartDto(
        Long id,
        Instant createdAt
) {
}
