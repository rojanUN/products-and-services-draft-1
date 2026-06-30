package com.swifttech.edx.sr.ps.model.response;

import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.edx.sr.ps.enums.ComputationEventEnum;
import com.swifttech.edx.sr.ps.enums.ComputationModelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValueComponentResponse {

    private Long id;
    private String name;
    private String description;
    private StatusEnum status;
    private ComputationModelEnum computationModel;
    private ComputationEventEnum computationEvent;
    private Long basedOnId;
    private Long valueMovementId;
    private String valueMovementName;
    private Long appliesToId;
    private String appliesToName;
    private Long componentTypeId;
    private String componentTypeName;
    private LocalDate dateFrom;
    private LocalDate dateTo;

}
