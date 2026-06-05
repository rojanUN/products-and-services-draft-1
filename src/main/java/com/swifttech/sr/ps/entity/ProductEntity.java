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
@Entity(name = "sr_product")
public class ProductEntity extends BaseEntity {

    private String name;
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductTypeEntity productType;

//    private String productComponent; //what is a product component?? "Actual data from the Product Type. Ex: NPR. Direct reference to the data"  ???? need to clarify

    //EAGER because the set will likely be very small 5-10. I thinks not sure.
//    @JoinTable(
//            name = "sr_product_service"
//            , joinColumns = @JoinColumn(name = "product_id")
//            , inverseJoinColumns = @JoinColumn(name = "service_id")
//    )
//    private Set<ServiceEntity> services; //this is a subset of services

/*    @ManyToOne(optional = false, fetch = FetchType.LAZY) //optional false, not sure what is correct.
    private ProductServiceClassificationEntity productServiceClassification;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<ValueComponentEntity> valueComponents;

    private boolean hasDynamicProductAttributes;


}
