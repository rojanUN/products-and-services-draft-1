package com.swifttech.sr.ps.schema.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProductTypeCreateUpdateRequest {

    @NotBlank
    private String name;
    private String description;

    @NotNull
    private Long productClassificationId;

}
