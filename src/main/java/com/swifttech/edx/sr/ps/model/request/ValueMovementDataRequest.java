package com.swifttech.edx.sr.ps.model.request;

import com.swifttech.edx.dm.payload.request.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueMovementDataRequest extends PaginationRequest {
    private String searchText;
}
