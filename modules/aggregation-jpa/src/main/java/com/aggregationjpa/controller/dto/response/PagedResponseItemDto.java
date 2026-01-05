package com.aggregationjpa.controller.dto.response;

import java.util.List;

public record PagedResponseItemDto(
        List<ResponseItemDto> content,
        String search,
        String sortBy,
        String sortOrder,
        Paging paging
) {

}
