package com.swifttech.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.model.request.ServiceCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ServiceDataRequest;

public interface ServiceService {

    GlobalResponse createService(ServiceCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateService(Long id, ServiceCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findServiceById(Long id) throws GlobalException;

    GlobalResponse findServicePaginatedData(ServiceDataRequest request) throws GlobalException;

}
