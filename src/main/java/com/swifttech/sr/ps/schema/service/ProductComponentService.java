package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ProductComponentCreateUpdateRequest;

public interface ProductComponentService {

    GlobalResponse createProductComponent(ProductComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductComponent(Long id, ProductComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductComponentById(Long id) throws GlobalException;

    GlobalResponse findProductComponentPaginatedData(PaginationRequest request) throws GlobalException;

}
