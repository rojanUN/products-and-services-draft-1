package com.swifttech.sr.ps.model.response;

import com.swifttech.edx.dm.enums.StatusEnum;
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
public class CurrencyResponse {

    private Long id;
    private String name;
    private String alphaThree;
    private String numeric;
    private String symbol;
    private String defaultRateFormat;
    private String defaultRateMasking;
    private StatusEnum status;
    private Long countryId;
    private String countryName;
    private Long classificationId;
    private String classificationName;

}
