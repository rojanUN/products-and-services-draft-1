package com.swifttech.edx.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.edx.sr.ps.model.request.ProductAndServiceClassificationCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ProductAndServiceClassificationDataRequest;
import com.swifttech.edx.sr.ps.service.ProductAndServiceClassificationService;
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
public class ProductAndServiceClassificationController {

    private final ProductAndServiceClassificationService productAndServiceClassificationService;

    @PostMapping("product-and-service-classification/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productAndServiceClassificationService.createProductAndServiceClassification(request));
    }

    @PutMapping("product-and-service-classification/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productAndServiceClassificationService.updateProductAndServiceClassification(id, request));
    }

    @GetMapping("product-and-service-classification/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(productAndServiceClassificationService.findProductAndServiceClassificationById(id));
    }

    @PostMapping("product-and-service-classification/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ProductAndServiceClassificationDataRequest request) throws GlobalException {
        return ResponseEntity.ok(productAndServiceClassificationService.findProductAndServiceClassificationPaginatedData(request));
    }

}
