package com.aggregation.controller.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class RequestProfileCreateDto {
    @NotNull
    private String sessionId;
    @NotNull
    private String displayName;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime birthday;
    private String description;
    private Integer page;
    private Integer size;
}
