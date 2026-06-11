package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ValueComponentTypeCreateUpdateRequest;

public interface ValueComponentTypeService {

    GlobalResponse createValueComponentType(ValueComponentTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueComponentType(Long id, ValueComponentTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findValueComponentTypeById(Long id) throws GlobalException;

    GlobalResponse findValueComponentTypePaginatedData(PaginationRequest request) throws GlobalException;

}
