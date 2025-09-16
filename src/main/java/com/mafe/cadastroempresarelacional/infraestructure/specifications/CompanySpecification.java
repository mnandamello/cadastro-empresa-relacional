package com.mafe.cadastroempresarelacional.infraestructure.specifications;

import com.mafe.cadastroempresarelacional.domain.Adress;
import com.mafe.cadastroempresarelacional.domain.Company;
import com.mafe.cadastroempresarelacional.domain.enums.CompanySituation;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.CompanyFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecification {

    public static Specification<Company> withCompanySituation(String companySituation) {
        return (root, query, cb) -> {
            if (companySituation == null || companySituation.trim().isEmpty()) return cb.conjunction();
            try {
                CompanySituation situation = CompanySituation.valueOf(companySituation.toUpperCase());
                return cb.equal(root.get("companySituation"), situation);
            } catch (IllegalArgumentException e) {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Company> withCity(String city) {
        return (root, query, cb) -> {
            if (city == null || city.trim().isEmpty()) return cb.conjunction();
            Join<Company, Adress> adressesJoin = root.join("adresses", JoinType.INNER);
            return cb.like(cb.lower(adressesJoin.get("city")), "%" + city.toLowerCase() + "%");
        };
    }

    public static Specification<Company> withFilters(CompanyFilter filter) {
        return Specification.allOf(
                withCompanySituation(filter.getCompanySituation()),
                withCity(filter.getCity())
        );
    }
}
