package com.swifttech.sr.ps.schema.mapper;

import com.swifttech.sr.ps.schema.entity.ValueApplicationEntity;
import com.swifttech.sr.ps.schema.model.request.ValueApplicationCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ValueApplicationResponse;

public final class ValueApplicationMapper {

    private ValueApplicationMapper() {
    }

    public static ValueApplicationResponse toResponse(ValueApplicationEntity entity) {
        if (entity == null) {
            return null;
        }
        ValueApplicationResponse response = new ValueApplicationResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public static ValueApplicationEntity toEntity(ValueApplicationCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        ValueApplicationEntity valueApplicationEntity = new ValueApplicationEntity();
        valueApplicationEntity.setName(request.getName());
        return valueApplicationEntity;
    }

    public static void toUpdate(ValueApplicationCreateUpdateRequest request, ValueApplicationEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
    }

}
