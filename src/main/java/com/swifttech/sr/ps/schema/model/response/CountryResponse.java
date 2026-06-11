package com.swifttech.sr.ps.schema.model.response;

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
public class CountryResponse {

    private Long id;
    private String name;
    private String description;
    private String alpha2Code;
    private String alpha3Code;
    private String numericCode;
    private String dialCode;
    private String timezone;

}
