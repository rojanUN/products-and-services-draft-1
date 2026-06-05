package com.swifttech.sr.ps.utils;

import com.swifttech.edx.dm.entity.BaseEntity;
import com.swifttech.edx.dm.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Utility {

    //persistence method
    public static <T extends BaseEntity> T handlePersist(T entity, BaseRepository<T> repository) {
        log.info("persisting({}, {})", entity.getClass().getSimpleName(), repository);
        return repository.save(entity);
    }
}
