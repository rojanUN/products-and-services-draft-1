package com.swifttech.sr.ps.schema.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.schema.model.request.BusinessEntityCategoryCreateUpdateRequest;
import com.swifttech.sr.ps.schema.service.BusinessEntityCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ProductAndServicesRestController
@RequiredArgsConstructor
public class BusinessEntityCategoryController {

    private final BusinessEntityCategoryService businessEntityCategoryService;

    @PostMapping("business-entity-category/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(businessEntityCategoryService.createBusinessEntityCategory(request));
    }

    @PutMapping("business-entity-category/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(businessEntityCategoryService.updateBusinessEntityCategory(id, request));
    }

    @GetMapping("business-entity-category/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(businessEntityCategoryService.findBusinessEntityCategoryById(id));
    }

    @PostMapping("business-entity-category/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody PaginationRequest request) throws GlobalException {
        return ResponseEntity.ok(businessEntityCategoryService.findBusinessEntityCategoryPaginatedData(request));
    }

}
