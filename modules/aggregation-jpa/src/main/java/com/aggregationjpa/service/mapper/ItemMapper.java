package com.aggregationjpa.service.mapper;

import com.aggregationjpa.controller.dto.request.RequestItemCreateDto;
import com.aggregationjpa.controller.dto.request.RequestItemUpdateDto;
import com.aggregationjpa.controller.dto.response.ResponseItemDto;
import com.aggregationjpa.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ItemEntity toEntity(RequestItemCreateDto request);

    @Mapping(target = "id", ignore = true) // не обновляем id
    @Mapping(target = "createdAt", ignore = true) // не обновляем createdAt
    void updateEntityFromDto(RequestItemUpdateDto dto, @MappingTarget ItemEntity entity);

    ResponseItemDto toResponseItemDto(ItemEntity item);
}
