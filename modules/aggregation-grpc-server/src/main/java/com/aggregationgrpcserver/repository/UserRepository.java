package com.aggregationgrpcserver.repository;

import com.aggregationgrpcserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
