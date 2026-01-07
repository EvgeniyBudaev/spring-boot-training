package com.aggregationjpa.repository;

import com.aggregationjpa.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartIdAndItemId(Long cartId, Long itemId);

    // Опционально: если нужна выборка с жадной загрузкой item
    @Query("""
        SELECT ci
        FROM CartItemEntity ci
        JOIN FETCH ci.item
        WHERE ci.cart.id = :cartId AND ci.item.id = :itemId
        """)
    Optional<CartItemEntity> findWithItemByCartIdAndItemId(@Param("cartId") Long cartId, @Param("itemId") Long itemId);

    @Query("""
    SELECT ci
    FROM CartItemEntity ci
    JOIN FETCH ci.item
    WHERE ci.cart.id = :cartId
    """)
//    @EntityGraph(attributePaths = "item") // или используйте JOIN FETCH в @Query
    List<CartItemEntity> findByCartId(@Param("cartId") Long cartId);
}
