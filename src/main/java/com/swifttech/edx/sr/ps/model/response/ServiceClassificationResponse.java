package com.swifttech.edx.sr.ps.model.response;

import com.swifttech.edx.dm.enums.StatusEnum;
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
public class ServiceClassificationResponse {

    private Long id;
    private String name;
    private String description;
    private StatusEnum status;
    private Long parentId;
    private String parentName;

}
