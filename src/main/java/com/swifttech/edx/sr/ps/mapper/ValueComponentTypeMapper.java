package com.swifttech.edx.sr.ps.mapper;

import com.swifttech.edx.sr.ps.entity.ValueComponentTypeEntity;
import com.swifttech.edx.sr.ps.model.request.ValueComponentTypeCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.response.ValueComponentTypeResponse;

public final class ValueComponentTypeMapper {

    private ValueComponentTypeMapper() {
    }

    public static ValueComponentTypeResponse toResponse(ValueComponentTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        ValueComponentTypeResponse response = new ValueComponentTypeResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public static ValueComponentTypeEntity toEntity(ValueComponentTypeCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        ValueComponentTypeEntity valueComponentTypeEntity = new ValueComponentTypeEntity();
        valueComponentTypeEntity.setName(request.getName());
        return valueComponentTypeEntity;
    }

    public static void toUpdate(ValueComponentTypeCreateUpdateRequest request, ValueComponentTypeEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
    }

}
