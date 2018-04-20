package com.hexagon.boot.domain.model;

import java.util.List;

public interface FeedBackRepository<S extends FeedBackEntity> {
    FeedBackEntity save(FeedBackEntity var1);

    Iterable<S> saveAll(List<FeedBackEntity> var1);

    FeedBackEntity findById(Long var1);

    boolean existsById(Long var1);

    Iterable<S> findAll();

    Iterable<S> findAllById(Iterable<Long> var1);

    long count();

    void deleteById(Long var1);

    void delete(FeedBackEntity var1);

    void deleteAll(List<FeedBackEntity> var1);

    void deleteAll();
}
