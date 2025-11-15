package com.aggregationgrpcclient.service.grpc;

import com.aggregationgrpc.*;
import com.aggregationgrpcclient.controller.dto.response.UserResponseDto;
import com.aggregationgrpcclient.service.grpc.mapper.UserMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcUserService {
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;
    private final UserMapper userMapper;

    public GrpcUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void init() {
        // Создаём канал напрямую через gRPC API
        // обязательно для незашифрованного соединения
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9092)
                .usePlaintext() // обязательно для незашифрованного соединения
                .build();

        this.userServiceStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public UserResponseDto createUser(String name) {
        try {
            CreateUserRequest request = CreateUserRequest.newBuilder()
                    .setName(name)
                    .build();

            UserResponse userResponse = userServiceStub.createUser(request);
            log.info("Created user: id={}, name={}", userResponse.getId(), userResponse.getName());
            return userMapper.toDto(userResponse);
        } catch (StatusRuntimeException e) {
            log.error("gRPC error while creating user: {}", e.getStatus(), e);
            throw new RuntimeException("Failed to create user via gRPC", e);
        }
    }

    public UserResponseDto getUser(Long id) {
        try {
            GetUserRequest request = GetUserRequest.newBuilder()
                    .setId(id)
                    .build();

            UserResponse userResponse = userServiceStub.getUser(request);
            log.info("Fetched user: id={}, name={}", userResponse.getId(), userResponse.getName());
            return userMapper.toDto(userResponse);
        } catch (StatusRuntimeException e) {
            log.error("gRPC error while fetching user id={}: {}", id, e.getStatus(), e);
            throw new RuntimeException("User not found or gRPC error", e);
        }
    }

    public UserResponseDto updateUser(Long id, String name) {
        try {
            UpdateUserRequest request = UpdateUserRequest.newBuilder()
                    .setId(id)
                    .setName(name)
                    .build();

            UserResponse userResponse = userServiceStub.updateUser(request);
            log.info("Updated user: id={}, name={}", userResponse.getId(), userResponse.getName());
            return userMapper.toDto(userResponse);
        } catch (StatusRuntimeException e) {
            log.error("gRPC error while updating user id={}: {}", id, e.getStatus(), e);
            throw new RuntimeException("Failed to update user via gRPC", e);
        }
    }

    public boolean deleteUser(Long id) {
        try {
            DeleteUserRequest request = DeleteUserRequest.newBuilder()
                    .setId(id)
                    .build();

            DeleteUserResponse response = userServiceStub.deleteUser(request);
            boolean success = response.getSuccess();
            log.info("Delete user id={}, success={}", id, success);
            return success;
        } catch (StatusRuntimeException e) {
            log.error("gRPC error while deleting user id={}: {}", id, e.getStatus(), e);
            throw new RuntimeException("Failed to delete user via gRPC", e);
        }
    }
}
