package com.aggregationjpa.service.mapper;

import com.aggregationjpa.controller.dto.response.ResponseCartDto;
import com.aggregationjpa.controller.dto.response.ResponseCartItemDto;
import com.aggregationjpa.entity.CartEntity;
import com.aggregationjpa.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {

    ResponseCartDto toResponseCartDto(CartEntity cartEntity);

    ResponseCartItemDto toResponseCartItemDto(CartItemEntity cartItemEntity);
}
