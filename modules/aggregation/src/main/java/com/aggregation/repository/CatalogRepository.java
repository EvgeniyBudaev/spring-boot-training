package com.aggregation.repository;

import com.aggregation.controller.dto.request.RequestCatalogCreateDto;
import com.aggregation.entity.CatalogEntity;

public interface CatalogRepository {
    CatalogEntity createCatalog(RequestCatalogCreateDto dto);

    CatalogEntity findByID(Long id);
}
