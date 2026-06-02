package com.swifttech.sr.ps.mapper;

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
                .build();



        return entity;
    }
}
