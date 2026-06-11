package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ServiceCreateUpdateRequest;

public interface ServiceService {

    GlobalResponse createService(ServiceCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateService(Long id, ServiceCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findServiceById(Long id) throws GlobalException;

    GlobalResponse findServicePaginatedData(PaginationRequest request) throws GlobalException;

}
