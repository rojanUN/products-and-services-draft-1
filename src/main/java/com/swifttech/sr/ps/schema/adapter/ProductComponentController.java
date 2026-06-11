package com.swifttech.sr.ps.schema.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.schema.model.request.ProductComponentCreateUpdateRequest;
import com.swifttech.sr.ps.schema.service.ProductComponentService;
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
public class ProductComponentController {

    private final ProductComponentService productComponentService;

    @PostMapping("product-component/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ProductComponentCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productComponentService.createProductComponent(request));
    }

    @PutMapping("product-component/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ProductComponentCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productComponentService.updateProductComponent(id, request));
    }

    @GetMapping("product-component/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(productComponentService.findProductComponentById(id));
    }

    @PostMapping("product-component/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody PaginationRequest request) throws GlobalException {
        return ResponseEntity.ok(productComponentService.findProductComponentPaginatedData(request));
    }

}
