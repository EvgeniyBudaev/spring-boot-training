package com.aggregationjpa.service;

import com.aggregationjpa.controller.dto.request.RequestOrderCreateDto;
import com.aggregationjpa.entity.CartItemEntity;
import com.aggregationjpa.entity.ItemEntity;
import com.aggregationjpa.entity.OrderEntity;
import com.aggregationjpa.entity.OrderItemEntity;
import com.aggregationjpa.repository.CartItemRepository;
import com.aggregationjpa.repository.CartRepository;
import com.aggregationjpa.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void createOrder(RequestOrderCreateDto request) {
        Long cartId = request.cartId();

        if (!cartRepository.existsById(cartId)) {
            throw new EntityNotFoundException("Cart with id=" + cartId + " not found");
        }

        List<CartItemEntity> cartItems = cartItemRepository.findByCartId(cartId);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart with id=" + cartId + " is empty");
        }

        // Создаём заказ
        OrderEntity order = new OrderEntity();
        order.setOrderItems(new ArrayList<>());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemEntity cartItem : cartItems) {
            ItemEntity item = cartItem.getItem();
            if (item == null) {
                throw new IllegalStateException("Item not found for cartItem id=" + cartItem.getId());
            }

            int quantity = cartItem.getQuantity();
            if (quantity <= 0) {
                throw new IllegalStateException("Invalid quantity: " + quantity + " for item id=" + item.getId());
            }

            BigDecimal priceAtOrder = item.getPrice();
            if (priceAtOrder == null || priceAtOrder.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("Invalid price for item id=" + item.getId());
            }

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setItemId(item.getId());
            orderItem.setQuantity(quantity);
            orderItem.setPriceAtOrder(priceAtOrder);

            order.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(priceAtOrder.multiply(BigDecimal.valueOf(quantity)));
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        // Опционально: очистка корзины после оформления заказа. Метод deleteByCartId / deleteById нужно добавить
//        cartItemRepository.deleteByCartId(cartId);
        // или cartRepository.deleteById(cartId); — если корзина больше не нужна
    }
}
