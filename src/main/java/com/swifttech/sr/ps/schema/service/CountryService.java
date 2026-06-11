package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.CountryCreateUpdateRequest;

public interface CountryService {

    GlobalResponse createCountry(CountryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateCountry(Long id, CountryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findCountryById(Long id) throws GlobalException;

    GlobalResponse findCountryPaginatedData(PaginationRequest request) throws GlobalException;

}
