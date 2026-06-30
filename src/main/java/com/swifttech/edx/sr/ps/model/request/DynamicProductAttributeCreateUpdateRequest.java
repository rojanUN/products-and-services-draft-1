package com.swifttech.edx.sr.ps.model.request;

import com.swifttech.edx.sr.ps.enums.DynamicProductAttributeTypeEnum;
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
public class DynamicProductAttributeCreateUpdateRequest {

    @NotBlank
    private String attributeName;
    private String attributeValue;

    @NotNull
    private DynamicProductAttributeTypeEnum attributeType;

}
