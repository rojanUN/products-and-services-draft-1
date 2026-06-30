package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.CurrencyDataRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.CurrencyCreateUpdateRequest;

public interface CurrencyService {

    GlobalResponse createCurrency(CurrencyCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateCurrency(Long id, CurrencyCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateCurrencyStatus(Long id, StatusUpdateRequest request) throws GlobalException;

    GlobalResponse findCurrencyById(Long id) throws GlobalException;

    GlobalResponse findCurrencyPaginatedData(CurrencyDataRequest request) throws GlobalException;

}
