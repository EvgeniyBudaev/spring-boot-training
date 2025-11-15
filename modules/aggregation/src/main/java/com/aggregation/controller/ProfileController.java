package com.aggregation.controller;

import com.aggregation.controller.dto.request.RequestProfileCreateDto;
import com.aggregation.controller.dto.response.ResponseProfileCreateDto;
import com.aggregation.controller.dto.response.ResponseProfileFindBySessionIdDto;
import com.aggregation.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ResponseProfileCreateDto> createProfile(
            @ModelAttribute RequestProfileCreateDto dto) {
        System.out.println("controller createProfile: " + dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.create(dto));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<ResponseProfileFindBySessionIdDto> getProfileBySessionID(
            @PathVariable String sessionId
    ) {
        System.out.println("controller getProfileBySessionID sessionId: " + sessionId);
        return ResponseEntity.status(HttpStatus.OK).body(profileService.findBySessionID(sessionId));
    }
}
