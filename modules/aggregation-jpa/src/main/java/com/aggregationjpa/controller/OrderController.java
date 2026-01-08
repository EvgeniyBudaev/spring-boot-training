package com.aggregationjpa.controller;

import com.aggregationjpa.aspect.LogMethodExecutionTime;
import com.aggregationjpa.controller.dto.request.RequestOrderCreateDto;
import com.aggregationjpa.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@Tag(name = "orders", description = "orders API")
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/buy")
    @LogMethodExecutionTime
    public ResponseEntity<Void> createOrder(@Valid @RequestBody RequestOrderCreateDto request) {
        log.info("controller createOrder: request={}", request);
        orderService.createOrder(request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
