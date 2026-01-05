package com.aggregationjpa.controller.dto.response;

public record Paging(
        int pageSize,
        int pageNumber,
        boolean hasPrevious,
        boolean hasNext
) {
}
