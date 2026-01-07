package com.aggregationjpa.service;

import com.aggregationjpa.controller.dto.response.ResponseCartDto;
import com.aggregationjpa.controller.dto.response.ResponseCartItemDto;
import com.aggregationjpa.shared.enums.CartAction;

import java.util.List;

public interface CartService {
    ResponseCartDto saveCart();

    ResponseCartDto getCart(Long id);

    List<ResponseCartItemDto> getCartItems(Long cartId);

    void updateCart(Long cartId, Long itemId, CartAction action);
}
