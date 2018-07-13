package com.hexagon.boot.adapter.repository;

import com.hexagon.boot.domain.BaseEntity;
import com.hexagon.boot.domain.BaseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class MybatisBaseRepositoryImpl<E extends BaseEntity, D> implements BaseRepository<E> {

    protected IBaseMapper<D> mapper;

    protected abstract D translateDao(E entity);
    protected abstract E translateEntity(D dao);

    @Override
    public E findById(Long id) {
        return translateEntity(mapper.selectByPrimaryKey(id));
    }

    @Override
    public E findByParam(Map<String, Object> params) {
        return translateEntity(mapper.selectByParams(params));
    }

    @Override
    public List<E> findAll() {
        return mapper.selectListByParams(null, 0, 100, null)
                .stream()
                .map(this::translateEntity)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return mapper.selectCountByParams(null);
    }

    @Override
    public List<E> findPage(Map<String, Object> params, Integer pageNum, Integer pageSize, String order) {
        return mapper.selectListByParams(params, pageNum, pageSize, order)
                .stream()
                .map(this::translateEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> findAllByIds(List<Long> ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        return mapper.selectListByParams(params, 0, ids.size(), null)
                .stream()
                .map(this::translateEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        D obj = mapper.selectByPrimaryKey(id);
        return obj!=null;
    }

    @Override
    public List<E> insertAll(List<E> items) {
        List<D> l = items.stream().map(this::translateDao).collect(Collectors.toList());
        mapper.insertBatch(l);
        return l.stream()
                .map(this::translateEntity)
                .collect(Collectors.toList());
    }

    @Override
    public E insert(E entity) {
        D d = translateDao(entity);
        mapper.insertSelective(d);
        return translateEntity(d);
    }

    @Override
    public E update(E entity) {
        mapper.updateByPrimaryKeySelective(translateDao(entity));
        return entity;
    }

    @Override
    public E write(E entity) {
        mapper.updateByPrimaryKey(translateDao(entity));
        return entity;
    }

    @Override
    public void delete(E entity) {
        mapper.deleteByPrimaryKey(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteAll(List<E> deleteList) {
        for(E e: deleteList) {
            mapper.deleteByPrimaryKey(e.getId());
        }
    }

    @Override
    public void deleteAll() {
        // 不支持
    }
}
