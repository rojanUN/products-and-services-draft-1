package com.swifttech.edx.sr.ps.entity;

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

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sr_service")
public class ServiceEntity extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_category_id")
    private BusinessEntityCategoryEntity serviceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_classification_id")
    private ServiceClassificationEntity serviceClassification;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "sr_service_value_component",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "value_component_id")
    )
    private Set<ValueComponentEntity> valueComponents = new HashSet<>();

    @ManyToMany(mappedBy = "services")
    private Set<ProductEntity> products = new HashSet<>();

}
