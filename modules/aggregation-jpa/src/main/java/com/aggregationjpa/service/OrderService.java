package com.aggregationjpa.service;

import com.aggregationjpa.controller.dto.request.RequestOrderCreateDto;

public interface OrderService {
    void createOrder(RequestOrderCreateDto request);
}
