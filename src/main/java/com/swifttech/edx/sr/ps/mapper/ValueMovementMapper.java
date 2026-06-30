package com.swifttech.edx.sr.ps.mapper;

import com.swifttech.edx.sr.ps.entity.ValueMovementEntity;
import com.swifttech.edx.sr.ps.model.request.ValueMovementCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.response.ValueMovementResponse;

public final class ValueMovementMapper {

    private ValueMovementMapper() {
    }

    public static ValueMovementResponse toResponse(ValueMovementEntity entity) {
        if (entity == null) {
            return null;
        }
        ValueMovementResponse response = new ValueMovementResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public static ValueMovementEntity toEntity(ValueMovementCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        ValueMovementEntity valueMovementEntity = new ValueMovementEntity();
        valueMovementEntity.setName(request.getName());
        return valueMovementEntity;
    }

    public static void toUpdate(ValueMovementCreateUpdateRequest request, ValueMovementEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
    }

}
