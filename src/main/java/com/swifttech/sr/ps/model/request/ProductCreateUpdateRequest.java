package com.swifttech.sr.ps.model.request;

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
public class ProductCreateUpdateRequest {

    @NotBlank
    private String name;
    private String description;

    @NotNull
    private Long productClassificationId;

    @NotNull
    private Long productTypeId;

    private Long productComponentId;

    @NotNull
    private Long productAndServiceClassificationId;

    private Set<Long> serviceIds;
    private boolean hasDynamicAttributes;
    private Set<Long> valueComponentIds;
    private Set<DynamicProductAttributeCreateUpdateRequest> dynamicAttributes;

}
