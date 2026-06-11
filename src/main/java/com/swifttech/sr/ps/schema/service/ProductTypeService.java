package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ProductTypeCreateUpdateRequest;

public interface ProductTypeService {

    GlobalResponse createProductType(ProductTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductType(Long id, ProductTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductTypeById(Long id) throws GlobalException;

    GlobalResponse findProductTypePaginatedData(PaginationRequest request) throws GlobalException;

}
