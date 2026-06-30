package com.swifttech.edx.sr.ps.service.specification;

import com.swifttech.edx.sr.ps.entity.ProductAndServiceClassificationEntity;
import com.swifttech.edx.sr.ps.model.request.ProductAndServiceClassificationDataRequest;
import com.swifttech.edx.sr.ps.utils.SpecificationHelper;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductAndServiceClassificationSpecification {

    private ProductAndServiceClassificationSpecification() {}

    public static Specification<ProductAndServiceClassificationEntity> filterBy(ProductAndServiceClassificationDataRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getSearchText())) {
                String text = request.getSearchText();
                predicates.add(cb.or(
                        SpecificationHelper.likeIgnoreCase(cb, root.get("name"), text),
                        SpecificationHelper.likeIgnoreCase(cb, cb.coalesce(root.get("description"), ""), text)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
