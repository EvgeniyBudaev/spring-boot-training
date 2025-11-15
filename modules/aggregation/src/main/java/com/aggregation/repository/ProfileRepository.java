package com.aggregation.repository;

import com.aggregation.controller.dto.request.RequestProfileCreateDto;
import com.aggregation.entity.ProfileEntity;

public interface ProfileRepository {
    ProfileEntity createProfile(RequestProfileCreateDto dto);

    ProfileEntity findBySessionID(String sessionId);
}
