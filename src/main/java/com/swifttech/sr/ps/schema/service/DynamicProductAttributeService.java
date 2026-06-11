package com.swifttech.sr.ps.schema.service;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.model.request.DynamicProductAttributeCreateUpdateRequest;

import java.util.List;

public interface DynamicProductAttributeService {

    GlobalResponse addDynamicAttribute(Long productId, DynamicProductAttributeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse updateDynamicAttribute(Long id, DynamicProductAttributeCreateUpdateRequest request) throws GlobalException;

    GlobalResponse deleteDynamicAttribute(Long id) throws GlobalException;

    GlobalResponse findDynamicAttributesByProductId(Long productId) throws GlobalException;

}
