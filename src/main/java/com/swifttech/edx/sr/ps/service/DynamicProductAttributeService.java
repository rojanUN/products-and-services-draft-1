package com.swifttech.edx.sr.ps.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.model.request.DynamicProductAttributeCreateUpdateRequest;

public interface DynamicProductAttributeService {

    GlobalResponse addDynamicAttribute(Long productId, DynamicProductAttributeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateDynamicAttribute(Long id, DynamicProductAttributeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse deleteDynamicAttribute(Long id) throws GlobalException;

    GlobalResponse findDynamicAttributesByProductId(Long productId) throws GlobalException;

}
