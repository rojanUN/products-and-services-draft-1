package com.swifttech.edx.sr.ps.model.response;

import com.swifttech.edx.dm.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationHierarchyResponse {

    private Long id;
    private String name;
    private String description;
    private StatusEnum status;
    private List<ClassificationHierarchyResponse> children;

}
