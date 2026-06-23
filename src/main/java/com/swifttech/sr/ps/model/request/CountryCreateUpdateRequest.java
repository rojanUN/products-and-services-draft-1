package com.swifttech.sr.ps.model.request;

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
public class CountryCreateUpdateRequest {

    @NotBlank
    private String name;
    private String description;
    private String alpha2Code;
    private String alpha3Code;
    private String numericCode;
    private String dialCode;
    private String timezone;

}
