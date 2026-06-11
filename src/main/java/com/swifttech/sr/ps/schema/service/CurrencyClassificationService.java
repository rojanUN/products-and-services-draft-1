package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.CurrencyClassificationCreateUpdateRequest;

public interface CurrencyClassificationService {

    GlobalResponse createCurrencyClassification(CurrencyClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateCurrencyClassification(Long id, CurrencyClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findCurrencyClassificationById(Long id) throws GlobalException;

    GlobalResponse findCurrencyClassificationPaginatedData(PaginationRequest request) throws GlobalException;

}
