package com.hexagon.boot.domain;

import java.util.List;

public interface BaseRepository<E extends BaseEntity> {
    E save(E obj);

    List<E> saveAll(List<E> items);

    E findById(Long id);

    boolean existsById(Long id);

    List<E> findAll();

//    TODO 对于条件组合，还没有想好有什么公共方法
//    List<E> findAllByParams(Map<String, Object> params);

    List<E> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(E obj);

    void deleteAll(List<E> deleteList);

    void deleteAll();
}
