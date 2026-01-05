package com.aggregation.controller;

import com.aggregation.aspect.LogMethodExecutionTime;
import com.aggregation.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregation.service.CatalogService;
import com.aggregation.controller.dto.request.RequestCatalogCreateDto;
import com.aggregation.controller.dto.response.ResponseCatalogCreateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
@Slf4j
@Tag(name = "catalogs", description = "catalogs API")
@RequestMapping("/api/v1/catalogs")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping
    @LogMethodExecutionTime
    public ResponseEntity<ResponseCatalogCreateDto> createCatalog(@ModelAttribute @Valid RequestCatalogCreateDto dto) {
        log.info("controller createCatalog: dto={}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.create(dto));
    }

    @GetMapping("/{id}")
    @LogMethodExecutionTime
    public ResponseEntity<ResponseCatalogFindByIdDto> getCatalogByID(@PathVariable Long id) {
        log.info("controller getCatalogByID: id={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.findByID(id));
    }
}
