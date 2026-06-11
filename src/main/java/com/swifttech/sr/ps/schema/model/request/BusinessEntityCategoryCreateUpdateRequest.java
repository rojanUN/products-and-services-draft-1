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
public class BusinessEntityCategoryCreateUpdateRequest {

    @NotBlank
    private String name;
    private String description;

}
