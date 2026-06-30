package com.swifttech.edx.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.edx.sr.ps.model.request.ValueComponentCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ValueComponentDataRequest;
import com.swifttech.edx.sr.ps.service.ValueComponentService;
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
public class ValueComponentController {

    private final ValueComponentService valueComponentService;

    @PostMapping("value-component/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ValueComponentCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueComponentService.createValueComponent(request));
    }

    @PutMapping("value-component/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ValueComponentCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueComponentService.updateValueComponent(id, request));
    }

    @PatchMapping("value-component/{id}/status")
    public ResponseEntity<GlobalResponse> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueComponentService.updateValueComponentStatus(id, request));
    }

    @GetMapping("value-component/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(valueComponentService.findValueComponentById(id));
    }

    @PostMapping("value-component/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ValueComponentDataRequest request) throws GlobalException {
        return ResponseEntity.ok(valueComponentService.findValueComponentPaginatedData(request));
    }

}
