package com.aggregationkeycloak.controller;

import com.aggregationkeycloak.aspect.LogMethodExecutionTime;
import com.aggregationkeycloak.controller.dto.request.RequestUserCreateDto;
import com.aggregationkeycloak.controller.dto.request.RequestUserUpdateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseUserDetailDto;
import com.aggregationkeycloak.controller.dto.response.ResponseUserDto;
import com.aggregationkeycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.keycloak.representations.idm.UserRepresentation;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @LogMethodExecutionTime
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody RequestUserCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @PutMapping
    @LogMethodExecutionTime
    public ResponseEntity<UserRepresentation> updateUser(@RequestBody RequestUserUpdateDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(dto));
    }

    @DeleteMapping("/{id}")
    @LogMethodExecutionTime
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/all")
    @LogMethodExecutionTime
    public ResponseEntity<UserRepresentation> getUserDetailAll(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDetailAll(id));
    }

    @GetMapping("/{id}")
    @LogMethodExecutionTime
    public ResponseEntity<ResponseUserDetailDto> getUserDetail(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDetail(id));
    }

    @PutMapping("/{id}/send-verification-email")
    @LogMethodExecutionTime
    public ResponseEntity<?> sendVerificationEmail(@PathVariable String id) {
        userService.sendVerificationEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/forgot-password")
    @LogMethodExecutionTime
    public ResponseEntity<?> forgotPassword(@RequestParam String username) {
        userService.forgotPassword(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/roles")
    @LogMethodExecutionTime
    public ResponseEntity<?> getUserRoles(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserRoles(id));
    }

    @GetMapping("/{id}/groups")
    @LogMethodExecutionTime
    public ResponseEntity<?> getUserGroups(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserGroups(id));
    }
}
