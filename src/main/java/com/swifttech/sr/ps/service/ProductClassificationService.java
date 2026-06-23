package com.swifttech.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductClassificationDataRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;

public interface ProductClassificationService {

    GlobalResponse createProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductClassification(Long id, ProductClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException;

    GlobalResponse findProductClassificationById(Long id) throws GlobalException;

    GlobalResponse findProductClassificationPaginatedData(ProductClassificationDataRequest request) throws GlobalException;

    GlobalResponse findProductClassificationHierarchy() throws GlobalException;

}
