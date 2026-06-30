package com.swifttech.edx.sr.ps.model.response;

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
public class ProductAndServiceClassificationResponse {

    private Long id;
    private String name;
    private String description;
    private Long productClassificationId;
    private String productClassificationName;
    private Long serviceClassificationId;
    private String serviceClassificationName;

}
