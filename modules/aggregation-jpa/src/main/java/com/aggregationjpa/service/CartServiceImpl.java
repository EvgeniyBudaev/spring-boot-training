package com.aggregationjpa.service;

import com.aggregationjpa.controller.dto.response.ResponseCartDto;
import com.aggregationjpa.controller.dto.response.ResponseCartItemDto;
import com.aggregationjpa.entity.CartEntity;
import com.aggregationjpa.entity.CartItemEntity;
import com.aggregationjpa.entity.ItemEntity;
import com.aggregationjpa.exception.NotFoundException;
import com.aggregationjpa.repository.CartItemRepository;
import com.aggregationjpa.repository.CartRepository;
import com.aggregationjpa.repository.ItemRepository;
import com.aggregationjpa.service.mapper.CartMapper;
import com.aggregationjpa.shared.enums.CartAction;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public ResponseCartDto saveCart() {
        CartEntity savedCart = cartRepository.save(new CartEntity());
        return cartMapper.toResponseCartDto(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseCartDto getCart(Long id) {
        CartEntity cartEntity = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart with id=" + id + " not found"));
        return cartMapper.toResponseCartDto(cartEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseCartItemDto> getCartItems(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new EntityNotFoundException("Cart with id=" + cartId + " not found");
        }

        // Получаем все элементы корзины с жадной загрузкой item (чтобы избежать N+1)
        List<CartItemEntity> cartItems = cartItemRepository.findByCartId(cartId);

        return cartItems.stream()
                .map(cartMapper::toResponseCartItemDto)
                .toList();
    }

    @Override
    @Transactional
    public void updateCart(Long cartId, Long itemId, CartAction action) {
        var cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    log.error("CartServiceImpl::updateCart cartId = {} not found", cartId);
                    return new NotFoundException(
                            "cart not found",
                            "CartServiceImpl::updateCart cartId " + cartId + " does not exist.");
                });
        var itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.error("CartServiceImpl::updateCart itemId = {} not found", itemId);
                    return new EntityNotFoundException("Item with id=" + itemId + " not found");
                });

        // Ищем элемент корзины именно в ЭТОЙ корзине
        Optional<CartItemEntity> existingCartItemOpt = cartItemRepository.findByCartIdAndItemId(cartId, itemId);
        CartItemEntity cartItem = existingCartItemOpt.orElse(null);

        switch (action) {
            case PLUS -> incrementItemQuantityInCart(cartItem, cartEntity, itemEntity);
            case MINUS -> decrementItemQuantityInCart(cartItem, cartEntity, itemEntity);
            case DELETE -> deleteItemFromCart(cartItem, cartEntity, itemEntity);
            default -> throw new IllegalArgumentException("Unsupported cart action: " + action);
        }
    }

    private void incrementItemQuantityInCart(CartItemEntity cartItem, CartEntity cartEntity, ItemEntity itemEntity) {
        if (cartItem == null) {
            // Создаём новый элемент корзины, привязанный к cartEntity
            cartItem = new CartItemEntity();
            cartItem.setCart(cartEntity);
            cartItem.setItem(itemEntity);
            cartItem.setQuantity(1);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        cartItemRepository.save(cartItem);
        log.info("Item {} added/updated in cart {}. New quantity: {}",
                itemEntity.getId(), cartEntity.getId(), cartItem.getQuantity());
    }

    private void decrementItemQuantityInCart(CartItemEntity cartItem, CartEntity cartEntity, ItemEntity itemEntity) {
        if (cartItem == null) {
            throw new IllegalArgumentException("Cannot decrement item "
                    + itemEntity.getId() + ": not in cart " + cartEntity.getId());
        }
        int newQuantity = cartItem.getQuantity() - 1;
        if (newQuantity <= 0) {
            cartItemRepository.delete(cartItem);
            log.info("Item {} removed from cart {} (quantity reached 0)",
                    itemEntity.getId(), cartEntity.getId());
        } else {
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
            log.info("Item {} decremented in cart {}. New quantity: {}",
                    itemEntity.getId(), cartEntity.getId(), newQuantity);
        }
    }

    private void deleteItemFromCart(CartItemEntity cartItem, CartEntity cartEntity, ItemEntity itemEntity) {
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            log.info("Item {} deleted from cart {}", itemEntity.getId(), cartEntity.getId());
        } else {
            log.warn("Attempt to delete non-existing item {} from cart {}", itemEntity.getId(), cartEntity.getId());
        }
    };
}
