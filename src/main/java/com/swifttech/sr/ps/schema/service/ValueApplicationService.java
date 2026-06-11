package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ValueApplicationCreateUpdateRequest;

public interface ValueApplicationService {

    GlobalResponse createValueApplication(ValueApplicationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueApplication(Long id, ValueApplicationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findValueApplicationById(Long id) throws GlobalException;

    GlobalResponse findValueApplicationPaginatedData(PaginationRequest request) throws GlobalException;

}
