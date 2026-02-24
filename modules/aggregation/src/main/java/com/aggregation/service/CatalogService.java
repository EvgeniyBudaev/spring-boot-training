package com.aggregation.service;

import com.aggregation.controller.dto.request.RequestCatalogCreateDto;
import com.aggregation.controller.dto.request.RequestCatalogListGetDto;
import com.aggregation.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregation.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregation.entity.CatalogEntity;
import com.aggregation.entity.PaginationEntity;

import java.util.List;

public interface CatalogService {
    ResponseCatalogCreateDto create(RequestCatalogCreateDto dto);

    ResponseCatalogFindByIdDto findByID(Long id);

    PaginationEntity<List<CatalogEntity>> findList(RequestCatalogListGetDto dto);
}
