package com.swifttech.sr.ps.schema.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.schema.model.request.ServiceClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.schema.service.ServiceClassificationService;
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
public class ServiceClassificationController {

    private final ServiceClassificationService serviceClassificationService;

    @PostMapping("service-classification/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceClassificationService.createServiceClassification(request));
    }

    @PutMapping("service-classification/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceClassificationService.updateServiceClassification(id, request));
    }

    @PatchMapping("service-classification/{id}/status")
    public ResponseEntity<GlobalResponse> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceClassificationService.updateServiceClassificationStatus(id, request));
    }

    @GetMapping("service-classification/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(serviceClassificationService.findServiceClassificationById(id));
    }

    @PostMapping("service-classification/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody PaginationRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceClassificationService.findServiceClassificationPaginatedData(request));
    }

    @GetMapping("service-classification/hierarchy")
    public ResponseEntity<GlobalResponse> findHierarchy() throws GlobalException {
        return ResponseEntity.ok(serviceClassificationService.findServiceClassificationHierarchy());
    }

}
