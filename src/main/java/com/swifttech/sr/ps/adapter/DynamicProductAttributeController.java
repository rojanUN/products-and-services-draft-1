package com.swifttech.sr.ps.adapter;

import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.annotation.ProductAndServicesRestController;
import com.swifttech.sr.ps.model.request.DynamicProductAttributeCreateUpdateRequest;
import com.swifttech.sr.ps.service.DynamicProductAttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ProductAndServicesRestController
@RequiredArgsConstructor
public class DynamicProductAttributeController {

    private final DynamicProductAttributeService dynamicProductAttributeService;

    @PostMapping("product/{productId}/dynamic-attribute")
    public ResponseEntity<GlobalResponse> addAttribute(@PathVariable Long productId, @RequestBody @Valid DynamicProductAttributeCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(dynamicProductAttributeService.addDynamicAttribute(productId, request));
    }

    @PutMapping("dynamic-attribute/{id}/update")
    public ResponseEntity<GlobalResponse> updateAttribute(@PathVariable Long id, @RequestBody @Valid DynamicProductAttributeCreateUpdateRequest request) throws GlobalException {
        return ResponseEntity.ok(dynamicProductAttributeService.updateDynamicAttribute(id, request));
    }

    @DeleteMapping("dynamic-attribute/{id}")
    public ResponseEntity<GlobalResponse> deleteAttribute(@PathVariable Long id) throws GlobalException {
        return ResponseEntity.ok(dynamicProductAttributeService.deleteDynamicAttribute(id));
    }

    @GetMapping("product/{productId}/dynamic-attributes")
    public ResponseEntity<GlobalResponse> findByProductId(@PathVariable Long productId) throws GlobalException {
        return ResponseEntity.ok(dynamicProductAttributeService.findDynamicAttributesByProductId(productId));
    }

}
