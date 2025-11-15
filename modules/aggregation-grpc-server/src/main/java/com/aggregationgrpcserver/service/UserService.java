package com.aggregationgrpcserver.service;

import com.aggregationgrpc.CreateUserRequest;
import com.aggregationgrpc.DeleteUserResponse;
import com.aggregationgrpc.UpdateUserRequest;
import com.aggregationgrpc.UserResponse;
import com.aggregationgrpcserver.entity.UserEntity;
import com.aggregationgrpcserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        userRepository.save(user);

        return buildFrom(user);
    }

    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.error("[getUserById] -> User with id {} no found", id);
            throw new RuntimeException("User not found");
        }

        return buildFrom(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.error("[updateUser] -> User with id {} no found", id);
            throw new RuntimeException("User not found");
        }
        if (request.hasName()) {
            user.setName(request.getName());
        }

        userRepository.save(user);
        return buildFrom(user);
    }

    public DeleteUserResponse deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("[deleteUser] -> User with id {} no found", id);
            return DeleteUserResponse.newBuilder().setSuccess(false).build();
        }
        userRepository.delete(user);
        return DeleteUserResponse.newBuilder().setSuccess(true).build();
    }

    private UserResponse buildFrom(UserEntity userEntity) {
        return UserResponse.newBuilder()
                .setId(userEntity.getId())
                .setName(userEntity.getName())
                .build();
    }
}
