package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.ValueApplicationCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ValueApplicationDataRequest;
import com.swifttech.sr.ps.service.ValueApplicationService;
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
public class ValueApplicationController {

    private final ValueApplicationService valueApplicationService;

    @PostMapping("value-application/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ValueApplicationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueApplicationService.createValueApplication(request));
    }

    @PutMapping("value-application/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ValueApplicationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueApplicationService.updateValueApplication(id, request));
    }

    @GetMapping("value-application/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(valueApplicationService.findValueApplicationById(id));
    }

    @PostMapping("value-application/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ValueApplicationDataRequest request) throws GlobalException {
        return ResponseEntity.ok(valueApplicationService.findValueApplicationPaginatedData(request));
    }

}
