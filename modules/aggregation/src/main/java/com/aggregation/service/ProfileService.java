package com.aggregation.service;

import com.aggregation.controller.dto.request.RequestProfileCreateDto;
import com.aggregation.controller.dto.response.ResponseProfileCreateDto;
import com.aggregation.controller.dto.response.ResponseProfileFindBySessionIdDto;

public interface ProfileService {
    ResponseProfileCreateDto create(RequestProfileCreateDto dto);

    ResponseProfileFindBySessionIdDto findBySessionID(String sessionId);
}
