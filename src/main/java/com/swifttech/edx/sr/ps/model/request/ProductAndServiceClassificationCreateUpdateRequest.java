package com.swifttech.edx.sr.ps.model.request;

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
public class ProductAndServiceClassificationCreateUpdateRequest {

    private String name;
    private String description;

    @NotNull
    private Long productClassificationId;

    @NotNull
    private Long serviceClassificationId;

    private Set<Long> valueComponentIds;

}
