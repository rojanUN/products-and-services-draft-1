package com.swifttech.edx.sr.ps.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCreateUpdateRequest {

    @NotBlank
    private String name;

    private Long serviceCategoryId;

    @NotNull
    private Long serviceClassificationId;

    private Set<Long> valueComponentIds;
    private Set<Long> productIds;

}
