package com.swifttech.sr.ps.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Long productClassificationId;
    private String productClassificationName;
    private Long productTypeId;
    private String productTypeName;
    private Long productComponentId;
    private String productComponentName;
    private Long productAndServiceClassificationId;
    private String productAndServiceClassificationName;
    private List<ServiceResponse> services;
    private boolean hasDynamicAttributes;
    private Set<DynamicProductAttributeResponse> dynamicAttributes;

}
