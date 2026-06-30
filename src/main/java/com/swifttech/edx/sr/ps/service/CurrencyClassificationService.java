package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.CurrencyClassificationCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.CurrencyClassificationDataRequest;

public interface CurrencyClassificationService {

    GlobalResponse createCurrencyClassification(CurrencyClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateCurrencyClassification(Long id, CurrencyClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findCurrencyClassificationById(Long id) throws GlobalException;

    GlobalResponse findCurrencyClassificationPaginatedData(CurrencyClassificationDataRequest request) throws GlobalException;

}
