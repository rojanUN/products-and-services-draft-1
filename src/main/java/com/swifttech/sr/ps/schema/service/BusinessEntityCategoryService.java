package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.BusinessEntityCategoryCreateUpdateRequest;

public interface BusinessEntityCategoryService {

    GlobalResponse createBusinessEntityCategory(BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateBusinessEntityCategory(Long id, BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findBusinessEntityCategoryById(Long id) throws GlobalException;

    GlobalResponse findBusinessEntityCategoryPaginatedData(PaginationRequest request) throws GlobalException;

}
