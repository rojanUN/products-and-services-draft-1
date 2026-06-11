package com.swifttech.sr.ps.schema.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.schema.model.request.CurrencyClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.schema.service.CurrencyClassificationService;
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
public class CurrencyClassificationController {

    private final CurrencyClassificationService currencyClassificationService;

    @PostMapping("currency-classification/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid CurrencyClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(currencyClassificationService.createCurrencyClassification(request));
    }

    @PutMapping("currency-classification/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid CurrencyClassificationCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(currencyClassificationService.updateCurrencyClassification(id, request));
    }

    @GetMapping("currency-classification/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(currencyClassificationService.findCurrencyClassificationById(id));
    }

    @PostMapping("currency-classification/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody PaginationRequest request) throws GlobalException {
        return ResponseEntity.ok(currencyClassificationService.findCurrencyClassificationPaginatedData(request));
    }

}
