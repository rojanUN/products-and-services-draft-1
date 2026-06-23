package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.ProductCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductDataRequest;
import com.swifttech.sr.ps.service.ProductService;
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
public class ProductController {

    private final ProductService productService;

    @PostMapping("product/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ProductCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("product/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ProductCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @GetMapping("product/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PostMapping("product/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ProductDataRequest request) throws GlobalException {
        return ResponseEntity.ok(productService.findProductPaginatedData(request));
    }

}
