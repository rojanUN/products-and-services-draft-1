package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.ProductTypeCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ProductTypeDataRequest;

public interface ProductTypeService {

    GlobalResponse createProductType(ProductTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductType(Long id, ProductTypeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductTypeById(Long id) throws GlobalException;

    GlobalResponse findProductTypePaginatedData(ProductTypeDataRequest request) throws GlobalException;

}
