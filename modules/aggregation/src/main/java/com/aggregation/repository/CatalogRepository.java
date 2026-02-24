package com.aggregation.repository;

import com.aggregation.controller.dto.request.RequestCatalogCreateDto;
import com.aggregation.controller.dto.request.RequestCatalogListGetDto;
import com.aggregation.entity.CatalogEntity;
import com.aggregation.entity.PaginationEntity;

import java.util.List;

public interface CatalogRepository {
    CatalogEntity createCatalog(RequestCatalogCreateDto dto);

    CatalogEntity findByID(Long id);

    PaginationEntity<List<CatalogEntity>> findCatalogList(RequestCatalogListGetDto dto);
}
