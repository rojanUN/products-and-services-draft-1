package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.BusinessEntityCategoryCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.BusinessEntityCategoryDataRequest;

public interface BusinessEntityCategoryService {

    GlobalResponse createBusinessEntityCategory(BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateBusinessEntityCategory(Long id, BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findBusinessEntityCategoryById(Long id) throws GlobalException;

    GlobalResponse findBusinessEntityCategoryPaginatedData(BusinessEntityCategoryDataRequest request) throws GlobalException;

}
