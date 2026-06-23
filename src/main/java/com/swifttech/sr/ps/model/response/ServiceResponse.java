package com.swifttech.sr.ps.model.response;

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
public class ServiceResponse {

    private Long id;
    private String name;
    private Long serviceCategoryId;
    private String serviceCategoryName;
    private Long serviceClassificationId;
    private String serviceClassificationName;
    private Set<Long> productIds;

}
