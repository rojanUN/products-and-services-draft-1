package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.ValueApplicationCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ValueApplicationDataRequest;

public interface ValueApplicationService {

    GlobalResponse createValueApplication(ValueApplicationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateValueApplication(Long id, ValueApplicationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findValueApplicationById(Long id) throws GlobalException;

    GlobalResponse findValueApplicationPaginatedData(ValueApplicationDataRequest request) throws GlobalException;

}
