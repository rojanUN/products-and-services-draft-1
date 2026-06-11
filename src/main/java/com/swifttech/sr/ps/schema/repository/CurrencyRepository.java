package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.CurrencyEntity;

import java.util.Optional;

public interface CurrencyRepository extends BaseRepository<CurrencyEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);
    boolean existsByAlphaThree(String alphaThree);

    Optional<CurrencyEntity> findByAlphaThree(String alphaThree);

}
