package com.swifttech.edx.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.edx.sr.ps.model.request.ProductComponentCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ProductComponentDataRequest;
import com.swifttech.edx.sr.ps.service.ProductComponentService;
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
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ProductComponentDataRequest request) throws GlobalException {
        return ResponseEntity.ok(productComponentService.findProductComponentPaginatedData(request));
    }

}
