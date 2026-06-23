package com.swifttech.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.model.request.ValueComponentTypeCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ValueComponentTypeDataRequest;

public interface ValueComponentTypeService {

    GlobalResponse createValueComponentType(ValueComponentTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueComponentType(Long id, ValueComponentTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findValueComponentTypeById(Long id) throws GlobalException;

    GlobalResponse findValueComponentTypePaginatedData(ValueComponentTypeDataRequest request) throws GlobalException;

}
