package com.aggregationjpa.service;

import com.aggregationjpa.controller.dto.request.RequestItemCreateDto;
import com.aggregationjpa.controller.dto.request.RequestItemUpdateDto;
import com.aggregationjpa.controller.dto.response.PagedResponseItemDto;
import com.aggregationjpa.controller.dto.response.ResponseItemDto;

public interface ItemService {
    void saveItem(RequestItemCreateDto request);

    ResponseItemDto updateItem(Long id, RequestItemUpdateDto request);

    void deleteItem(Long id);

    ResponseItemDto getItem(Long id);

    PagedResponseItemDto getItems(String search, String sortBy, String sortOrder, int pageNumber, int pageSize);
}
