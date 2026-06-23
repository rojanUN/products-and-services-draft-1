package com.swifttech.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.model.request.ValueMovementCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ValueMovementDataRequest;

public interface ValueMovementService {

    GlobalResponse createValueMovement(ValueMovementCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueMovement(Long id, ValueMovementCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findValueMovementById(Long id) throws GlobalException;

    GlobalResponse findValueMovementPaginatedData(ValueMovementDataRequest request) throws GlobalException;

}
