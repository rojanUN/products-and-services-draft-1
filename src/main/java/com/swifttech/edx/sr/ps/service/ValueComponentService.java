package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ValueComponentDataRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.ValueComponentCreateUpdateRequest;

public interface ValueComponentService {

    GlobalResponse createValueComponent(ValueComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueComponent(Long id, ValueComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueComponentStatus(Long id, StatusUpdateRequest request) throws GlobalException;

    GlobalResponse findValueComponentById(Long id) throws GlobalException;

    GlobalResponse findValueComponentPaginatedData(ValueComponentDataRequest request) throws GlobalException;

}
