package com.swifttech.edx.sr.ps.service.specification;

import com.swifttech.edx.sr.ps.entity.ServiceEntity;
import com.swifttech.edx.sr.ps.model.request.ServiceDataRequest;
import com.swifttech.edx.sr.ps.utils.SpecificationHelper;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ServiceSpecification {

    private ServiceSpecification() {}

    public static Specification<ServiceEntity> filterBy(ServiceDataRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getSearchText())) {
                String text = request.getSearchText();
                predicates.add(SpecificationHelper.likeIgnoreCase(cb, root.get("name"), text));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
