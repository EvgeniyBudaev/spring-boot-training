package com.aggregationkeycloak.service;

import com.aggregationkeycloak.controller.dto.request.RequestCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogListGetDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogUpdateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregationkeycloak.entity.CatalogEntity;
import com.aggregationkeycloak.entity.PaginationEntity;

import java.util.List;

public interface CatalogService {
    ResponseCatalogCreateDto create(RequestCatalogCreateDto dto);

    CatalogEntity update(RequestCatalogUpdateDto dto);

    ResponseCatalogFindByIdDto findByID(Long id);

    PaginationEntity<List<CatalogEntity>> findList(RequestCatalogListGetDto dto);
}
