package com.aggregationjpa.controller;

import com.aggregationjpa.aspect.LogMethodExecutionTime;
import com.aggregationjpa.controller.dto.request.RequestItemCreateDto;
import com.aggregationjpa.controller.dto.request.RequestItemUpdateDto;
import com.aggregationjpa.controller.dto.response.PagedResponseItemDto;
import com.aggregationjpa.controller.dto.response.ResponseItemDto;
import com.aggregationjpa.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Slf4j
@Tag(name = "items", description = "items API")
@AllArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @LogMethodExecutionTime
    public ResponseEntity<Void> createItem(@Valid @RequestBody RequestItemCreateDto request) {
        log.info("controller createItem: request={}", request);
        itemService.saveItem(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @LogMethodExecutionTime
    public ResponseEntity<ResponseItemDto> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody RequestItemUpdateDto request
    ) {
        log.info("controller updateItem: id={}, request={}", id, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.updateItem(id, request));
    }

    @DeleteMapping("/{id}")
    @LogMethodExecutionTime
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.info("controller deleteItem: id={}", id);
        itemService.deleteItem(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @LogMethodExecutionTime
    public ResponseEntity<ResponseItemDto> getItem(@PathVariable Long id) {
        log.info("controller getItem: id={}", id);

        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItem(id));
    }

    @GetMapping
    @LogMethodExecutionTime
    public ResponseEntity<PagedResponseItemDto> getItems(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(defaultValue = "5") @Min(1) int pageSize
    ) {
        log.info("controller getItems: search={}, sortBy={}, sortOrder={}, pageNumber={}, pageSize={}", search, sortBy, sortOrder, pageNumber, pageSize);
        PagedResponseItemDto result = itemService.getItems(search, sortBy, sortOrder, pageNumber, pageSize);

        return ResponseEntity.ok(result);
    }
}
