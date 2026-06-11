package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.ProductCreateUpdateRequest;

public interface ProductService {

    GlobalResponse createProduct(ProductCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProduct(Long id, ProductCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductById(Long id) throws GlobalException;

    GlobalResponse findProductPaginatedData(PaginationRequest request) throws GlobalException;

}
