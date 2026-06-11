package com.swifttech.sr.ps.schema.entity;

import com.swifttech.edx.dm.entity.MasterEntity;
import jakarta.persistence.Entity;
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
        name = "sr_country",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}),
                @UniqueConstraint(columnNames = {"alpha2Code"}),
                @UniqueConstraint(columnNames = {"alpha3Code"})
        }
)
public class CountryEntity extends MasterEntity {

    private String alpha2Code;
    private String alpha3Code;
    private String numericCode;
    private String dialCode;
    private String timezone;

}
