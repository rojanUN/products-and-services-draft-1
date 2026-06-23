package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.ProductTypeCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductTypeDataRequest;
import com.swifttech.sr.ps.service.ProductTypeService;
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
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PostMapping("product-type/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ProductTypeCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productTypeService.createProductType(request));
    }

    @PutMapping("product-type/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ProductTypeCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productTypeService.updateProductType(id, request));
    }

    @GetMapping("product-type/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(productTypeService.findProductTypeById(id));
    }

    @PostMapping("product-type/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ProductTypeDataRequest request) throws GlobalException {
        return ResponseEntity.ok(productTypeService.findProductTypePaginatedData(request));
    }

}
