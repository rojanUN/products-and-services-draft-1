package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ValueMovementCreateUpdateRequest;

public interface ValueMovementService {

    GlobalResponse createValueMovement(ValueMovementCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueMovement(Long id, ValueMovementCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findValueMovementById(Long id) throws GlobalException;

    GlobalResponse findValueMovementPaginatedData(PaginationRequest request) throws GlobalException;

}
