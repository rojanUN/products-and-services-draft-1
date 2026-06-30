package com.swifttech.edx.sr.ps.model.request;

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
public class ValueComponentTypeCreateUpdateRequest {

    @NotBlank
    private String name;
    private String description;

}
