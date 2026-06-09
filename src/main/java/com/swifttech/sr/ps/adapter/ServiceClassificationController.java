package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.ServiceClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.service.ServiceClassificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ProductAndServicesRestController
@RequiredArgsConstructor
public class ServiceClassificationController {

    private final ServiceClassificationService serviceClassificationService;

    @PostMapping("service-classification/create")
    public ResponseEntity<GlobalResponse> createServiceClassification(@RequestBody @Valid ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(serviceClassificationService.createServiceClassification(request));
    }

}
