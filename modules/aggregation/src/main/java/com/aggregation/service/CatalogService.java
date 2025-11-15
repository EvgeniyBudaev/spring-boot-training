package com.aggregation.service;

import com.aggregation.controller.dto.request.RequestCatalogCreateDto;
import com.aggregation.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregation.controller.dto.response.ResponseCatalogFindByIdDto;

public interface CatalogService {
    ResponseCatalogCreateDto create(RequestCatalogCreateDto dto);

    ResponseCatalogFindByIdDto findByID(Long id);
}
