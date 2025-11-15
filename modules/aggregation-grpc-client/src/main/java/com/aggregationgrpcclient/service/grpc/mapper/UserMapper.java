package com.aggregationgrpcclient.service.grpc.mapper;

import com.aggregationgrpc.UserResponse;
import com.aggregationgrpcclient.controller.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDto toDto(UserResponse userResponse);
}
