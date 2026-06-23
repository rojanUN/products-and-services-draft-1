package com.swifttech.sr.ps.model.request;

import com.swifttech.edx.dm.payload.request.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDataRequest extends PaginationRequest {
    private String searchText;
}
