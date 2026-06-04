package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.service.ProductClassificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductClassificationController {

    private final ProductClassificationService productClassificationService;

    @PostMapping("product-classification/create")
    public ResponseEntity<GlobalResponse> createProductClassification(@RequestBody @Valid ProductClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(productClassificationService.createProductClassification(request));
    }

}


