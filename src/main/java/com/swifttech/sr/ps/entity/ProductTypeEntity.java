package com.swifttech.sr.ps.entity;

import com.swifttech.edx.dm.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity(name = "sr_product_type")
public class ProductTypeEntity extends BaseEntity {

    private String name;
    private String description;

/*    @OneToMany(mappedBy = "productType", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST},  fetch = FetchType.LAZY)
    private Set<ProductComponentEntity> productComponents;*/

    @OneToMany(mappedBy = "productType", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST},  fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_classification_id", nullable = false)
    private ProductClassificationEntity productClassification;

    @OneToMany(mappedBy = "productType",orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<ValueComponentEntity> valueComponents;

}
