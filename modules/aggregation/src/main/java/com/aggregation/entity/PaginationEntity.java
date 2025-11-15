package com.aggregation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationEntity<T> {
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer page;
    private Integer size;
    private Integer numberEntities;
    private Integer totalPages;
    private T content;

    public PaginationEntity(Integer page, Integer size, Integer numberEntities) {
        this.hasPrevious = page > 1;
        this.hasNext = (page * size) < numberEntities;
        this.page = page;
        this.size = size;
        this.numberEntities = numberEntities;
        this.totalPages = getTotalPages(size, numberEntities);
    }

    private Integer getTotalPages(Integer size, Integer numberEntities) {
        return (numberEntities + size - 1) / size;
    }
}
