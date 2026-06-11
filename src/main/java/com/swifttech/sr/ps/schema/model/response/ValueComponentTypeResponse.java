package com.swifttech.sr.ps.schema.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValueComponentTypeResponse {

    private Long id;
    private String name;
    private String description;

}
