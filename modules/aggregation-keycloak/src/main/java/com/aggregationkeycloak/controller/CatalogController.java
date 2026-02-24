package com.aggregationkeycloak.controller;

import com.aggregationkeycloak.aspect.LogMethodExecutionTime;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogListGetDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogUpdateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.response.ResponseCatalogFindByIdDto;
import com.aggregationkeycloak.entity.CatalogEntity;
import com.aggregationkeycloak.entity.PaginationEntity;
import com.aggregationkeycloak.service.CatalogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ResponseCatalogCreateDto> createCatalog(@ModelAttribute @Valid RequestCatalogCreateDto dto) {
        log.info("controller createCatalog: dto={}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.create(dto));
    }

    @PatchMapping
    @LogMethodExecutionTime
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CatalogEntity> updateCatalog(@RequestBody @Valid RequestCatalogUpdateDto dto) {
        log.info("controller updateCatalog: dto={}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.update(dto));
    }

    @GetMapping("/{id}")
    @LogMethodExecutionTime
    //    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseCatalogFindByIdDto> getCatalogByID(@PathVariable Long id) {
        log.info("controller getCatalogByID: id={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.findByID(id));
    }

    @GetMapping
    @LogMethodExecutionTime
    @Secured("ROLE_guest")
    public ResponseEntity<PaginationEntity<List<CatalogEntity>>> getCatalogList(
            @ModelAttribute RequestCatalogListGetDto dto) {
        log.info("controller getCatalogList: dto={}", dto);
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.findList(dto));
    }
}
