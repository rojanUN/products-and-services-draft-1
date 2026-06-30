package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.CountryCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.CountryDataRequest;

public interface CountryService {

    GlobalResponse createCountry(CountryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateCountry(Long id, CountryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findCountryById(Long id) throws GlobalException;

    GlobalResponse findCountryPaginatedData(CountryDataRequest request) throws GlobalException;

}
