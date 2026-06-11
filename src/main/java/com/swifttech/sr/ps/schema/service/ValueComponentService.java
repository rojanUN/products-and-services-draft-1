package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ValueComponentCreateUpdateRequest;

public interface ValueComponentService {

    GlobalResponse createValueComponent(ValueComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueComponent(Long id, ValueComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueComponentStatus(Long id, StatusUpdateRequest request) throws GlobalException;

    GlobalResponse findValueComponentById(Long id) throws GlobalException;

    GlobalResponse findValueComponentPaginatedData(PaginationRequest request) throws GlobalException;

}
