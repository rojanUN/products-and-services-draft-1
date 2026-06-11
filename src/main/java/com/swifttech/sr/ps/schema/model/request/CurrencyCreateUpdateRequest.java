package com.swifttech.sr.ps.schema.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyCreateUpdateRequest {

    @NotBlank
    private String name;
    private String alphaThree;
    private String numeric;
    private String symbol;
    private String defaultRateFormat;
    private String defaultRateMasking;
    private Long countryId;
    private Long classificationId;

}
