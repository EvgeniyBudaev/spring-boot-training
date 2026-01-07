package com.aggregationjpa.controller;

import com.aggregationjpa.aspect.LogMethodExecutionTime;
import com.aggregationjpa.controller.dto.response.ResponseCartDto;
import com.aggregationjpa.controller.dto.response.ResponseCartItemDto;
import com.aggregationjpa.service.CartService;
import com.aggregationjpa.shared.enums.CartAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
@Tag(name = "carts", description = "carts API")
@AllArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @LogMethodExecutionTime
    public ResponseEntity<ResponseCartDto> createCart() {
        log.info("controller createCart");

        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.saveCart());
    }

    @GetMapping("/{id}")
    @LogMethodExecutionTime
    public ResponseEntity<ResponseCartDto> getCart(@PathVariable Long id) {
        log.info("controller getCart: id={}", id);

        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCart(id));
    }

    @GetMapping("/{id}/items")
    @LogMethodExecutionTime
    public ResponseEntity<List<ResponseCartItemDto>> getCartItems(@PathVariable Long id) {
        log.info("controller getCartItems: id={}", id);
        List<ResponseCartItemDto> items = cartService.getCartItems(id);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{cartId}/items")
    @LogMethodExecutionTime
    public ResponseEntity<Void> updateCart(
            @PathVariable Long cartId,
            @RequestParam Long id,
            @RequestParam CartAction action
    ) {
        log.info("controller updateCart: cartId={}, id={}, action={}", cartId, id, action);
        cartService.updateCart(cartId, id, action);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
