package com.aggregation.service.mapper;

import com.aggregation.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregation.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregation.entity.CatalogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    ResponseCatalogCreateDto toResponseCreateDto(CatalogEntity entity);

    ResponseCatalogFindByIdDto toResponseFindByIdDto(CatalogEntity entity);
}
