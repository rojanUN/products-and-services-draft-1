package com.swifttech.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.model.request.ProductAndServiceClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductAndServiceClassificationDataRequest;

public interface ProductAndServiceClassificationService {

    GlobalResponse createProductAndServiceClassification(ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductAndServiceClassification(Long id, ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductAndServiceClassificationById(Long id) throws GlobalException;

    GlobalResponse findProductAndServiceClassificationPaginatedData(ProductAndServiceClassificationDataRequest request) throws GlobalException;

}
