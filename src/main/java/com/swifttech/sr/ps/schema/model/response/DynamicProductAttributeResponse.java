package com.swifttech.sr.ps.schema.model.response;

import com.swifttech.sr.ps.enums.DynamicProductAttributeTypeEnum;
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
public class DynamicProductAttributeResponse {

    private Long id;
    private String attributeName;
    private String attributeValue;
    private DynamicProductAttributeTypeEnum attributeType;

}
