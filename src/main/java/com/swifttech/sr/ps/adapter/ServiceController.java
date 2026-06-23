package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.ServiceCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ServiceDataRequest;
import com.swifttech.sr.ps.service.ServiceService;
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
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping("service/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid ServiceCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceService.createService(request));
    }

    @PutMapping("service/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid ServiceCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceService.updateService(id, request));
    }

    @GetMapping("service/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(serviceService.findServiceById(id));
    }

    @PostMapping("service/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody ServiceDataRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceService.findServicePaginatedData(request));
    }

}
