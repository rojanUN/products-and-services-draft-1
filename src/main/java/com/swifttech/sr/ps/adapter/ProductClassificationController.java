package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductClassificationDataRequest;
import com.swifttech.sr.ps.service.ProductClassificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ProductAndServicesRestController
@RequiredArgsConstructor
public class ProductClassificationController {

    private final ProductClassificationService productClassificationService;

    @PostMapping("product-classification/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ProductClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productClassificationService.createProductClassification(request));
    }

    @PutMapping("product-classification/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ProductClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productClassificationService.updateProductClassification(id, request));
    }

    @PatchMapping("product-classification/{id}/status")
    public ResponseEntity<GlobalResponse> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productClassificationService.updateProductClassificationStatus(id, request));
    }

    @GetMapping("product-classification/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(productClassificationService.findProductClassificationById(id));
    }

    @PostMapping("product-classification/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ProductClassificationDataRequest request) throws GlobalException {
        return ResponseEntity.ok(productClassificationService.findProductClassificationPaginatedData(request));
    }

    @GetMapping("product-classification/hierarchy")
    public ResponseEntity<GlobalResponse> findHierarchy() throws GlobalException {
        return ResponseEntity.ok(productClassificationService.findProductClassificationHierarchy());
    }

}
