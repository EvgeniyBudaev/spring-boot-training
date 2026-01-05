package com.aggregationjpa.repository;

import com.aggregationjpa.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Page<ItemEntity> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title,
            String description,
            Pageable pageable
    );
}
