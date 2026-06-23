package com.swifttech.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CountryRepository extends BaseRepository<CountryEntity>, JpaSpecificationExecutor<CountryEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);
    boolean existsByAlpha3Code(String alpha3Code);

    Optional<CountryEntity> findByAlpha3Code(String alpha3Code);

}
