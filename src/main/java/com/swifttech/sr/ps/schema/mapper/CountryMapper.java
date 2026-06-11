package com.swifttech.sr.ps.schema.mapper;

import com.swifttech.sr.ps.schema.entity.CountryEntity;
import com.swifttech.sr.ps.schema.model.request.CountryCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.CountryResponse;

public final class CountryMapper {

    private CountryMapper() {
    }

    public static CountryResponse toResponse(CountryEntity entity) {
        if (entity == null) {
            return null;
        }
        CountryResponse response = new CountryResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setAlpha2Code(entity.getAlpha2Code());
        response.setAlpha3Code(entity.getAlpha3Code());
        response.setNumericCode(entity.getNumericCode());
        response.setDialCode(entity.getDialCode());
        response.setTimezone(entity.getTimezone());
        return response;
    }

    public static CountryEntity toEntity(CountryCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return CountryEntity.builder()
//                .name(request.getName())
                .alpha2Code(request.getAlpha2Code())
                .alpha3Code(request.getAlpha3Code())
                .numericCode(request.getNumericCode())
                .dialCode(request.getDialCode())
                .timezone(request.getTimezone())
                .build();
    }

    public static void toUpdate(CountryCreateUpdateRequest request, CountryEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setAlpha2Code(request.getAlpha2Code());
        entity.setAlpha3Code(request.getAlpha3Code());
        entity.setNumericCode(request.getNumericCode());
        entity.setDialCode(request.getDialCode());
        entity.setTimezone(request.getTimezone());
    }

}
