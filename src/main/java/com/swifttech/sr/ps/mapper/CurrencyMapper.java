package com.swifttech.sr.ps.mapper;

import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.sr.ps.entity.CurrencyEntity;
import com.swifttech.sr.ps.model.request.CurrencyCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.CurrencyResponse;

public final class CurrencyMapper {

    private CurrencyMapper() {
    }

    public static CurrencyResponse toResponse(CurrencyEntity entity) {
        if (entity == null) {
            return null;
        }
        CurrencyResponse response = new CurrencyResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setAlphaThree(entity.getAlphaThree());
        response.setNumeric(entity.getNumeric());
        response.setSymbol(entity.getSymbol());
        response.setDefaultRateFormat(entity.getDefaultRateFormat());
        response.setDefaultRateMasking(entity.getDefaultRateMasking());
        response.setStatus(entity.getStatus());
        /*if (entity.getCountry() != null) {
            response.setCountryId(entity.getCountry().getUid());
            response.setCountryName(entity.getCountry().getName());
        }
        if (entity.getClassification() != null) {
            response.setClassificationId(entity.getClassification().getUid());
            response.setClassificationName(entity.getClassification().getName());
        }*/
        return response;
    }

    public static CurrencyEntity toEntity(CurrencyCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return CurrencyEntity.builder()
                .name(request.getName())
                .alphaThree(request.getAlphaThree())
                .numeric(request.getNumeric())
                .symbol(request.getSymbol())
                .defaultRateFormat(request.getDefaultRateFormat())
                .defaultRateMasking(request.getDefaultRateMasking())
                .status(StatusEnum.ACTIVE)
                .build();
    }

    public static void toUpdate(CurrencyCreateUpdateRequest request, CurrencyEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setAlphaThree(request.getAlphaThree());
        entity.setNumeric(request.getNumeric());
        entity.setSymbol(request.getSymbol());
        entity.setDefaultRateFormat(request.getDefaultRateFormat());
        entity.setDefaultRateMasking(request.getDefaultRateMasking());
    }

}
