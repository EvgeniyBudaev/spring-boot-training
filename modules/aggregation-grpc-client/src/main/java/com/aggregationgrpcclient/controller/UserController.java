package com.aggregationgrpcclient.controller;

import com.aggregationgrpc.UserResponse;
import com.aggregationgrpcclient.aspect.LogMethodExecutionTime;
import com.aggregationgrpcclient.controller.dto.response.UserResponseDto;
import com.aggregationgrpcclient.service.grpc.GrpcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final GrpcUserService grpcUserService;

    @PostMapping
    @LogMethodExecutionTime
    public UserResponseDto createUser(@RequestBody Map<String, String> payload) {
        System.out.println("UserController createUser name: " + payload.get("name"));
        return grpcUserService.createUser(payload.get("name"));
    }

    @GetMapping("/{id}")
    @LogMethodExecutionTime
    public UserResponseDto getUser(@PathVariable Long id) {
        System.out.println("UserController getUser id: " + id);
        return grpcUserService.getUser(id);
    }

    @PutMapping
    @LogMethodExecutionTime
    public UserResponseDto updateUser(@RequestBody Map<String, String> payload) {
        System.out.println("UserController updateUser name: " + payload.get("name"));
        return grpcUserService.updateUser(Long.valueOf(payload.get("id")), payload.get("name"));
    }

    @DeleteMapping("/{id}")
    @LogMethodExecutionTime
    public boolean deleteUser(@PathVariable Long id) {
        System.out.println("UserController deleteUser id: " + id);
        return grpcUserService.deleteUser(id);
    }
}
