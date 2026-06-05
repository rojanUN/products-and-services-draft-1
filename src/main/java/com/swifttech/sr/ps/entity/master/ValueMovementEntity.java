package com.swifttech.sr.ps.entity.master;

import com.swifttech.edx.dm.entity.MasterEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity(name = "sr_value_movement")
public class ValueMovementEntity extends MasterEntity {  //this might not exist as a different table (can use common table in master setup for such kind of configurations)
}
