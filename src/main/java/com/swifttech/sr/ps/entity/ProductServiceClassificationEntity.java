package com.swifttech.sr.ps.entity;

import com.swifttech.edx.dm.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sr_product_service_classification")
public class ProductServiceClassificationEntity extends BaseEntity {

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_classification_id")
    private ProductClassificationEntity productClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_classification_id")
    private ServiceClassificationEntity serviceClassification;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "sr_classification_value_component"
            , joinColumns = @JoinColumn(name = "classification_id")
            , inverseJoinColumns = @JoinColumn(name = "value_component_id")
    )
    private Set<ValueComponentEntity> valueComponents;  //This could also easily be a @ManyToOne relation, this is ambiguous in the miro board that is currently built (date:2026/05/22 11:54AM )
}
