package com.swifttech.sr.ps.mapper;

import com.swifttech.sr.ps.entity.CurrencyClassificationEntity;
import com.swifttech.sr.ps.model.request.CurrencyClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.CurrencyClassificationResponse;

public final class CurrencyClassificationMapper {

    private CurrencyClassificationMapper() {
    }

    public static CurrencyClassificationResponse toResponse(CurrencyClassificationEntity entity) {
        if (entity == null) {
            return null;
        }
        CurrencyClassificationResponse response = new CurrencyClassificationResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public static CurrencyClassificationEntity toEntity(CurrencyClassificationCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        CurrencyClassificationEntity currencyClassificationEntity = new CurrencyClassificationEntity();
        currencyClassificationEntity.setName(request.getName());
        return currencyClassificationEntity;
    }

    public static void toUpdate(CurrencyClassificationCreateUpdateRequest request, CurrencyClassificationEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
    }

}
