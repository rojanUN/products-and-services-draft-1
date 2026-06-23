package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.ValueMovementCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ValueMovementDataRequest;
import com.swifttech.sr.ps.service.ValueMovementService;
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
public class ValueMovementController {

    private final ValueMovementService valueMovementService;

    @PostMapping("value-movement/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ValueMovementCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueMovementService.createValueMovement(request));
    }

    @PutMapping("value-movement/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ValueMovementCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(valueMovementService.updateValueMovement(id, request));
    }

    @GetMapping("value-movement/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(valueMovementService.findValueMovementById(id));
    }

    @PostMapping("value-movement/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ValueMovementDataRequest request) throws GlobalException {
        return ResponseEntity.ok(valueMovementService.findValueMovementPaginatedData(request));
    }

}
