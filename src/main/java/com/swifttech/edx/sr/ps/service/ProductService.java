package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.ProductCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ProductDataRequest;

public interface ProductService {

    GlobalResponse createProduct(ProductCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProduct(Long id, ProductCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductById(Long id) throws GlobalException;

    GlobalResponse findProductPaginatedData(ProductDataRequest request) throws GlobalException;

}
