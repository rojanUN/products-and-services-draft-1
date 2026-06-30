package com.swifttech.edx.sr.ps.entity;

import com.swifttech.edx.dm.entity.BaseEntity;
import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.edx.sr.ps.enums.ComputationEventEnum;
import com.swifttech.edx.sr.ps.enums.ComputationModelEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sr_value_component")
public class ValueComponentEntity extends BaseEntity {

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private ComputationModelEnum computationModel;

    @Enumerated(EnumType.STRING)
    private ComputationEventEnum computationEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_value_component_id")
    private ValueComponentEntity basedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_movement_id")
    private ValueMovementEntity valueMovement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_application_id")
    private ValueApplicationEntity appliesTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_component_type_id")
    private ValueComponentTypeEntity componentType;

    private LocalDate dateFrom;
    private LocalDate dateTo;

}
