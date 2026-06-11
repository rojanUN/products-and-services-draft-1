package com.swifttech.sr.ps.schema.model.response;

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
    private Long serviceId;
    private String serviceName;
    private boolean hasDynamicAttributes;
    private Set<DynamicProductAttributeResponse> dynamicAttributes;

}
