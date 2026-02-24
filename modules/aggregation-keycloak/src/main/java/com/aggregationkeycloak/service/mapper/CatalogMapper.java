package com.aggregationkeycloak.service.mapper;

import com.aggregationkeycloak.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregationkeycloak.entity.CatalogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CatalogMapper {
    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    ResponseCatalogCreateDto toResponseCreateDto(CatalogEntity entity);

    ResponseCatalogFindByIdDto toResponseFindByIdDto(CatalogEntity entity);
}
