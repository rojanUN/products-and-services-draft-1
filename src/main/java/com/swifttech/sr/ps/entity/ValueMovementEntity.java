package com.swifttech.sr.ps.entity;

import com.swifttech.edx.dm.entity.MasterEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity(name = "sr_value_movement")
public class ValueMovementEntity extends MasterEntity {

}
