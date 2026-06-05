package com.swifttech.sr.ps.entity;

import com.swifttech.edx.dm.entity.BaseEntity;
import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.sr.ps.entity.master.ValueApplicationEntity;
import com.swifttech.sr.ps.entity.master.ValueComponentTypeEntity;
import com.swifttech.sr.ps.entity.master.ValueMovementEntity;
import com.swifttech.sr.ps.enums.ComputationEventEnum;
import com.swifttech.sr.ps.enums.ComputationModelEnum;
import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(
        name = "sr_value_component",
        check = {
                @CheckConstraint(
                        name = "chk_value_component_owner",
                        constraint = """
                                (
                                    (CASE WHEN service_classification_id IS NOT NULL THEN 1 ELSE 0 END) +
                                    (CASE WHEN product_classification_id IS NOT NULL THEN 1 ELSE 0 END) +
                                    (CASE WHEN product_type_id IS NOT NULL THEN 1 ELSE 0 END) +
                                    (CASE WHEN product_id IS NOT NULL THEN 1 ELSE 0 END) +
                                    (CASE WHEN service_id IS NOT NULL THEN 1 ELSE 0 END)
                                ) = 1
                                """
                )
        }
)
public class ValueComponentEntity extends BaseEntity {
    private String name;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_classification_id")
    private ServiceClassificationEntity serviceClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_classification_id")
    private ProductClassificationEntity productClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id")
    private ProductTypeEntity productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    private LocalDate dateFrom;
    private LocalDate dateTo;
}
