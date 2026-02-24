package com.aggregationkeycloak.service;

import com.aggregationkeycloak.controller.dto.request.RequestCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogListGetDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogUpdateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregationkeycloak.entity.CatalogEntity;
import com.aggregationkeycloak.entity.PaginationEntity;
import com.aggregationkeycloak.repository.CatalogRepository;
import com.aggregationkeycloak.service.mapper.CatalogMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    public CatalogServiceImpl(CatalogRepository catalogRepository, CatalogMapper catalogMapper) {
        this.catalogRepository = catalogRepository;
        this.catalogMapper = catalogMapper;
    }

    @Override
    public ResponseCatalogCreateDto create(RequestCatalogCreateDto dto) {
        CatalogEntity catalogEntity = catalogRepository.createCatalog(dto);
        return catalogMapper.toResponseCreateDto(catalogEntity);
    }

    @Override
    public CatalogEntity update(RequestCatalogUpdateDto dto) {
        return catalogRepository.updateCatalog(dto);
    }

    @Override
    public ResponseCatalogFindByIdDto findByID(Long id) {
        CatalogEntity catalogEntity = catalogRepository.findByID(id);
        return catalogMapper.toResponseFindByIdDto(catalogEntity);
    }

    @Override
    public PaginationEntity<List<CatalogEntity>> findList(RequestCatalogListGetDto dto) {
        return catalogRepository.findCatalogList(dto);
    }
}
