package com.swifttech.sr.ps.mapper;

import com.swifttech.sr.ps.entity.DynamicProductAttributeEntity;
import com.swifttech.sr.ps.model.request.DynamicProductAttributeCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.DynamicProductAttributeResponse;

public final class DynamicProductAttributeMapper {

    private DynamicProductAttributeMapper() {
    }

    public static DynamicProductAttributeResponse toResponse(DynamicProductAttributeEntity entity) {
        if (entity == null) {
            return null;
        }
        DynamicProductAttributeResponse response = new DynamicProductAttributeResponse();
        response.setId(entity.getUid());
        response.setAttributeName(entity.getAttributeName());
        response.setAttributeValue(entity.getAttributeValue());
        response.setAttributeType(entity.getAttributeType());
        return response;
    }

    public static DynamicProductAttributeEntity toEntity(DynamicProductAttributeCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return DynamicProductAttributeEntity.builder()
                .attributeName(request.getAttributeName())
                .attributeValue(request.getAttributeValue())
                .attributeType(request.getAttributeType())
                .build();
    }

    public static void toUpdate(DynamicProductAttributeCreateUpdateRequest request, DynamicProductAttributeEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setAttributeName(request.getAttributeName());
        entity.setAttributeValue(request.getAttributeValue());
        entity.setAttributeType(request.getAttributeType());
    }

}
