package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ServiceClassificationDataRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.ServiceClassificationCreateUpdateRequest;

public interface ServiceClassificationService {

    GlobalResponse createServiceClassification(ServiceClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateServiceClassification(Long id, ServiceClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateServiceClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException;

    GlobalResponse findServiceClassificationById(Long id) throws GlobalException;

    GlobalResponse findServiceClassificationPaginatedData(ServiceClassificationDataRequest request) throws GlobalException;

    GlobalResponse findServiceClassificationHierarchy() throws GlobalException;

}
