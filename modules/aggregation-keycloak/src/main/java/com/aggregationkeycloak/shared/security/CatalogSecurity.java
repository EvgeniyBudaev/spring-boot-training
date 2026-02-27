package com.aggregationkeycloak.shared.security;

import com.aggregationkeycloak.repository.CatalogRepositoryImpl;
import com.aggregationkeycloak.shared.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("catalogSecurity")
@RequiredArgsConstructor
public class CatalogSecurity {
    private final CatalogRepositoryImpl catalogRepository;

    public boolean isOwnerOrAdmin(Long catalogId) {
        // 1. Проверка на админа
        if (SecurityUtils.isAdmin()) {
            return true;
        }

        // 2. Проверка на владельца
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UUID ownerId = catalogRepository.findOwnerIdByCatalogId(catalogId);

        return currentUserId != null && currentUserId.equals(ownerId);
    }
}