package com.aggregation.service;

import com.aggregation.controller.dto.response.ResponseProfileCreateDto;
import com.aggregation.controller.dto.response.ResponseProfileFindBySessionIdDto;
import com.aggregation.controller.dto.request.RequestProfileCreateDto;
import com.aggregation.entity.ProfileEntity;
import com.aggregation.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ResponseProfileCreateDto create(RequestProfileCreateDto requestProfileCreateDto) {
        ProfileEntity profileEntity = profileRepository.createProfile(requestProfileCreateDto);
        return ResponseProfileCreateDto.builder()
                .sessionId(profileEntity.getSessionId())
                .build();
    }

    @Override
    public ResponseProfileFindBySessionIdDto findBySessionID(String sessionId) {
        ProfileEntity profileEntity = profileRepository.findBySessionID(sessionId);
        return ResponseProfileFindBySessionIdDto.builder()
                .sessionId(profileEntity.getSessionId())
                .displayName(profileEntity.getDisplayName())
                .birthday(profileEntity.getBirthday())
                .description(profileEntity.getDescription())
                .isDeleted(profileEntity.getIsDeleted())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .lastOnline(profileEntity.getLastOnline())
                .build();
    }
}
