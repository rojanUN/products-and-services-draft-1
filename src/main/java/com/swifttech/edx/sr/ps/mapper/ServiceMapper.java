package com.swifttech.edx.sr.ps.mapper;

import com.swifttech.edx.dm.entity.BaseEntity;
import com.swifttech.edx.sr.ps.entity.ServiceEntity;
import com.swifttech.edx.sr.ps.model.request.ServiceCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.response.ServiceResponse;

import java.util.stream.Collectors;

public final class ServiceMapper {

    private ServiceMapper() {
    }

    public static ServiceResponse toResponse(ServiceEntity entity) {
        if (entity == null) {
            return null;
        }
        ServiceResponse response = new ServiceResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        if (entity.getServiceCategory() != null) {
            response.setServiceCategoryId(entity.getServiceCategory().getUid());
            response.setServiceCategoryName(entity.getServiceCategory().getName());
        }
        if (entity.getServiceClassification() != null) {
            response.setServiceClassificationId(entity.getServiceClassification().getUid());
            response.setServiceClassificationName(entity.getServiceClassification().getName());
        }
        if (entity.getProducts() != null) {
            response.setProductIds(
                    entity.getProducts().stream()
                            .map(BaseEntity::getUid)
                            .collect(Collectors.toSet())
            );
        }
        return response;
    }

    public static ServiceEntity toEntity(ServiceCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ServiceEntity.builder()
                .name(request.getName())
                .build();
    }

    public static void toUpdate(ServiceCreateUpdateRequest request, ServiceEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
    }

}
