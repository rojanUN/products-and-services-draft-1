package com.swifttech.edx.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.edx.sr.ps.model.request.CountryCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.CountryDataRequest;
import com.swifttech.edx.sr.ps.service.CountryService;
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
public class CountryController {

    private final CountryService countryService;

    @PostMapping("country/create")
    public ResponseEntity<GlobalResponse> create(@RequestBody @Valid CountryCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(countryService.createCountry(request));
    }

    @PutMapping("country/{id}/update")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody @Valid CountryCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(countryService.updateCountry(id, request));
    }

    @GetMapping("country/{id}")
    public ResponseEntity<GlobalResponse> findById(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(countryService.findCountryById(id));
    }

    @PostMapping("country/list")
    public ResponseEntity<GlobalResponse> findAllPaginated(@RequestBody CountryDataRequest request) throws GlobalException {
        return ResponseEntity.ok(countryService.findCountryPaginatedData(request));
    }

}
