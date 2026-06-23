package com.swifttech.sr.ps.entity;

import com.swifttech.edx.dm.entity.BaseEntity;
import com.swifttech.edx.dm.enums.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Entity
@Table(
        name = "sr_currency",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}),
                @UniqueConstraint(columnNames = {"alphaThree"})
        }
)
public class CurrencyEntity extends BaseEntity {

    private String name;
    private String alphaThree;
    private String numeric;
    private String symbol;
    private String defaultRateFormat;
    private String defaultRateMasking;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "country_id")
//    private CountryEntity country;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "currency_classification_id")
//    private CurrencyClassificationEntity classification;

}
