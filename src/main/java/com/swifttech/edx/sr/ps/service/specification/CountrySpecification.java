package com.swifttech.edx.sr.ps.service.specification;

import com.swifttech.edx.sr.ps.entity.CountryEntity;
import com.swifttech.edx.sr.ps.model.request.CountryDataRequest;
import com.swifttech.edx.sr.ps.utils.SpecificationHelper;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CountrySpecification {

    private CountrySpecification() {}

    public static Specification<CountryEntity> filterBy(CountryDataRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getSearchText())) {
                String text = request.getSearchText();
                predicates.add(cb.or(
                        SpecificationHelper.likeIgnoreCase(cb, root.get("name"), text),
                        SpecificationHelper.likeIgnoreCase(cb, cb.coalesce(root.get("alpha2Code"), ""), text),
                        SpecificationHelper.likeIgnoreCase(cb, cb.coalesce(root.get("alpha3Code"), ""), text),
                        SpecificationHelper.likeIgnoreCase(cb, cb.coalesce(root.get("dialCode"), ""), text),
                        SpecificationHelper.likeIgnoreCase(cb, cb.coalesce(root.get("numericCode"), ""), text)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
