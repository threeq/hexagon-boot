package com.hexagon.boot.adapter.repository;

import com.hexagon.boot.domain.BaseEntity;
import com.hexagon.boot.domain.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class JpaBaseRepositoryImpl<E extends BaseEntity, D extends E> implements BaseRepository<E> {
    protected JpaBaseRepositoryFacade<D, Long> jpaCrudRepository;

    protected abstract D translateDao(E entity);
    protected abstract E translateEntity(D dao);

    @Override
    public E save(E entity) {
        return jpaCrudRepository.save(translateDao(entity));
    }

    @Override
    public List<E> saveAll(List<E> var1) {
        List<E> target = new ArrayList<>();
        jpaCrudRepository.saveAll(var1.stream()
                .map(this::translateDao)
                .collect(Collectors.toList()))
                .forEach(target::add);
        return target;
    }

    @Override
    public void deleteAll(List<E> deleteList) {
        jpaCrudRepository.deleteAll(deleteList.stream()
                .map(this::translateDao)
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(E entity) {
        jpaCrudRepository.delete(translateDao(entity));
    }

    @Override
    public E findById(Long id) {
        return jpaCrudRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaCrudRepository.existsById(id);
    }

    @Override
    public List<E> findAll() {
        List<E> target = new ArrayList<>();
        jpaCrudRepository.findAll().forEach(target::add);
        return target;
    }

    @Override
    public List<E> findAllById(Iterable<Long> ids) {
        List<E> target = new ArrayList<>();
        jpaCrudRepository.findAllById(ids).forEach(target::add);
        return target;
    }

    @Override
    public long count() {
        return jpaCrudRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        jpaCrudRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        jpaCrudRepository.deleteAll();
    }
}
