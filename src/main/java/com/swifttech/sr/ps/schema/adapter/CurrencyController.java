package com.swifttech.sr.ps.schema.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.schema.model.request.CurrencyCreateUpdateRequest;
import com.swifttech.sr.ps.schema.service.CurrencyService;
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
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("currency/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid CurrencyCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(currencyService.createCurrency(request));
    }

    @PutMapping("currency/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid CurrencyCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(currencyService.updateCurrency(id, request));
    }

    @PatchMapping("currency/{id}/status")
    public ResponseEntity<GlobalResponse> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(currencyService.updateCurrencyStatus(id, request));
    }

    @GetMapping("currency/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(currencyService.findCurrencyById(id));
    }

    @PostMapping("currency/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody PaginationRequest request) throws GlobalException {
        return ResponseEntity.ok(currencyService.findCurrencyPaginatedData(request));
    }

}
