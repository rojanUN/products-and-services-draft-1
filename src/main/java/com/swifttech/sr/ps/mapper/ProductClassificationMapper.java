package com.swifttech.sr.ps.mapper;

import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.sr.ps.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ProductClassificationResponse;


public final class ProductClassificationMapper {

    private ProductClassificationMapper() {

    }

    public static ProductClassificationResponse toResponse(ProductClassificationEntity entity) {
        if (entity == null) {
            return null;
        }

        ProductClassificationResponse response = new ProductClassificationResponse();
        response.setName(entity.getName());

        return response;
    }

    public static ProductClassificationEntity toEntity(ProductClassificationCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        ProductClassificationEntity entity = ProductClassificationEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(StatusEnum.ACTIVE)
                .build();



        return entity;
    }

    public static void toUpdate(ProductClassificationCreateUpdateRequest request, ProductClassificationEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }
}
