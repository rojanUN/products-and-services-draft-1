package com.swifttech.sr.ps.schema.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.schema.model.request.ValueComponentTypeCreateUpdateRequest;
import com.swifttech.sr.ps.schema.service.ValueComponentTypeService;
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
public class ValueComponentTypeController {

    private final ValueComponentTypeService valueComponentTypeService;

    @PostMapping("value-component-type/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ValueComponentTypeCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueComponentTypeService.createValueComponentType(request));
    }

    @PutMapping("value-component-type/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ValueComponentTypeCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueComponentTypeService.updateValueComponentType(id, request));
    }

    @GetMapping("value-component-type/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(valueComponentTypeService.findValueComponentTypeById(id));
    }

    @PostMapping("value-component-type/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody PaginationRequest request) throws GlobalException {
        return ResponseEntity.ok(valueComponentTypeService.findValueComponentTypePaginatedData(request));
    }

}
