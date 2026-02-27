package com.aggregationkeycloak.repository;

import com.aggregationkeycloak.controller.dto.request.RequestCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogListGetDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogUpdateDto;
import com.aggregationkeycloak.entity.CatalogEntity;
import com.aggregationkeycloak.entity.PaginationEntity;

import java.util.List;
import java.util.UUID;

public interface CatalogRepository {
    CatalogEntity createCatalog(RequestCatalogCreateDto dto, UUID createdBy);

    CatalogEntity updateCatalog(RequestCatalogUpdateDto dto);

    CatalogEntity findByID(Long id);

    PaginationEntity<List<CatalogEntity>> findCatalogList(RequestCatalogListGetDto dto);
}
