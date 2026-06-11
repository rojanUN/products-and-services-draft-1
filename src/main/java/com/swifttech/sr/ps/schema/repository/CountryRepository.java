package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.CountryEntity;

import java.util.Optional;

public interface CountryRepository extends BaseRepository<CountryEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);
    boolean existsByAlpha3Code(String alpha3Code);

    Optional<CountryEntity> findByAlpha3Code(String alpha3Code);

}
