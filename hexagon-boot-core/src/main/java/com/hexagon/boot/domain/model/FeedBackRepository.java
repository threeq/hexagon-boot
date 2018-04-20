package com.hexagon.boot.domain.model;

import java.util.List;

/**
 * TODO 目前还在优化 存储的定义和使用规范
 *
 * 领域模型 自身是需要存储的，所以存储所需要的服务接口定义在 领域模型内部
 * 但是实现由于和具体技术相关，放入 端口适配层
 * @param <S>
 */
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
