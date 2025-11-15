package com.aggregation.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ResponseProfileFindBySessionIdDto {
    private String sessionId;
    private String displayName;
    private LocalDate birthday;
    private String description;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastOnline;
}
