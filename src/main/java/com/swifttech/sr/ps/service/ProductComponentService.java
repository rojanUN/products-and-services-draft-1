package com.swifttech.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.model.request.ProductComponentCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductComponentDataRequest;

public interface ProductComponentService {

    GlobalResponse createProductComponent(ProductComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateProductComponent(Long id, ProductComponentCreateUpdateRequest request) throws GlobalException;

    GlobalResponse findProductComponentById(Long id) throws GlobalException;

    GlobalResponse findProductComponentPaginatedData(ProductComponentDataRequest request) throws GlobalException;

}
