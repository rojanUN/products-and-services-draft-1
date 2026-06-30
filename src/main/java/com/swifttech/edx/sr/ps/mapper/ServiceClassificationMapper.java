package com.swifttech.edx.sr.ps.mapper;

import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.edx.sr.ps.entity.ServiceClassificationEntity;
import com.swifttech.edx.sr.ps.model.request.ServiceClassificationCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.response.ServiceClassificationResponse;

public final class ServiceClassificationMapper {

    private ServiceClassificationMapper() {
    }

    public static ServiceClassificationResponse toResponse(ServiceClassificationEntity entity) {
        if (entity == null) {
            return null;
        }
        ServiceClassificationResponse response = new ServiceClassificationResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setStatus(entity.getStatus());
        if (entity.getParentClassification() != null) {
            response.setParentId(entity.getParentClassification().getUid());
            response.setParentName(entity.getParentClassification().getName());
        }
        return response;
    }

    public static ServiceClassificationEntity toEntity(ServiceClassificationCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ServiceClassificationEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(StatusEnum.ACTIVE)
                .build();
    }

    public static void toUpdate(ServiceClassificationCreateUpdateRequest request, ServiceClassificationEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

}
