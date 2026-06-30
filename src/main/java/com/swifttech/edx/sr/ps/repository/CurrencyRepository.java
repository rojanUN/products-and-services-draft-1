package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.CurrencyEntity;

import java.util.Optional;

public interface CurrencyRepository extends BaseRepository<CurrencyEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);
    boolean existsByAlphaThree(String alphaThree);

    Optional<CurrencyEntity> findByAlphaThree(String alphaThree);

}
