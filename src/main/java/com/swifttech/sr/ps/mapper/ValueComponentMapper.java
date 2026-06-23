package com.swifttech.sr.ps.mapper;

import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.sr.ps.entity.ValueComponentEntity;
import com.swifttech.sr.ps.model.request.ValueComponentCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ValueComponentResponse;

public final class ValueComponentMapper {

    private ValueComponentMapper() {
    }

    public static ValueComponentResponse toResponse(ValueComponentEntity entity) {
        if (entity == null) {
            return null;
        }
        ValueComponentResponse response = new ValueComponentResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setStatus(entity.getStatus());
        response.setComputationModel(entity.getComputationModel());
        response.setComputationEvent(entity.getComputationEvent());
        response.setDateFrom(entity.getDateFrom());
        response.setDateTo(entity.getDateTo());
        if (entity.getBasedOn() != null) {
            response.setBasedOnId(entity.getBasedOn().getUid());
        }
        if (entity.getValueMovement() != null) {
            response.setValueMovementId(entity.getValueMovement().getUid());
            response.setValueMovementName(entity.getValueMovement().getName());
        }
        if (entity.getAppliesTo() != null) {
            response.setAppliesToId(entity.getAppliesTo().getUid());
            response.setAppliesToName(entity.getAppliesTo().getName());
        }
        if (entity.getComponentType() != null) {
            response.setComponentTypeId(entity.getComponentType().getUid());
            response.setComponentTypeName(entity.getComponentType().getName());
        }
        return response;
    }

    public static ValueComponentEntity toEntity(ValueComponentCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ValueComponentEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(StatusEnum.ACTIVE)
                .computationModel(request.getComputationModel())
                .computationEvent(request.getComputationEvent())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .build();
    }

    public static void toUpdate(ValueComponentCreateUpdateRequest request, ValueComponentEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setComputationModel(request.getComputationModel());
        entity.setComputationEvent(request.getComputationEvent());
        entity.setDateFrom(request.getDateFrom());
        entity.setDateTo(request.getDateTo());
    }

}
