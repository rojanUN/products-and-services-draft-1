package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ProductAndServiceClassificationCreateUpdateRequest;

public interface ProductAndServiceClassificationService {

    GlobalResponse createProductAndServiceClassification(ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductAndServiceClassification(Long id, ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductAndServiceClassificationById(Long id) throws GlobalException;

    GlobalResponse findProductAndServiceClassificationPaginatedData(PaginationRequest request) throws GlobalException;

}
