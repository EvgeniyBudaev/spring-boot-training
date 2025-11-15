package com.aggregation.service;

import com.aggregation.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregation.repository.CatalogRepository;
import com.aggregation.service.mapper.CatalogMapper;
import com.aggregation.controller.dto.request.RequestCatalogCreateDto;
import com.aggregation.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregation.entity.CatalogEntity;
import org.springframework.stereotype.Service;

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
    public ResponseCatalogFindByIdDto findByID(Long id) {
        CatalogEntity catalogEntity = catalogRepository.findByID(id);
        return catalogMapper.toResponseFindByIdDto(catalogEntity);
    }
}
