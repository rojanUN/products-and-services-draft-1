package com.swifttech.sr.ps.model.request;

import com.swifttech.sr.ps.enums.ComputationEventEnum;
import com.swifttech.sr.ps.enums.ComputationModelEnum;
import jakarta.validation.constraints.NotBlank;
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
public class ValueComponentCreateUpdateRequest {

    @NotBlank
    private String name;
    private String description;
    private ComputationModelEnum computationModel;
    private ComputationEventEnum computationEvent;
    private Long basedOnId;
    private Long valueMovementId;
    private Long appliesToId;
    private Long componentTypeId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

}
