package com.swifttech.sr.ps.entity;

import com.swifttech.edx.dm.entity.BaseEntity;
import com.swifttech.sr.ps.enums.DynamicProductAttributeTypeEnum;
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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sr_dynamic_product_attribute")
public class DynamicProductAttributeEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String attributeName;
    private String attributeValue;

    @Enumerated(EnumType.STRING)
    private DynamicProductAttributeTypeEnum attributeType;
}
