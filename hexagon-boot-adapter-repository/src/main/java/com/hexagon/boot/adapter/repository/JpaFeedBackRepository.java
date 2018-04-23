package com.hexagon.boot.adapter.repository;

import com.hexagon.boot.domain.model.FeedBackEntity;
import com.hexagon.boot.domain.model.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源库依赖实现
 */
@Repository
public class JpaFeedBackRepository implements FeedBackRepository<JpaFeedBackEntity> {
    @Autowired
    JpaFeedBackRepositoryFacade jpaFeedBackRepository;


    @Override
    public FeedBackEntity save(FeedBackEntity var1) {
        return jpaFeedBackRepository.save(new JpaFeedBackEntity(var1));
    }

    @Override
    public Iterable<JpaFeedBackEntity> saveAll(List<FeedBackEntity> var1) {
        return jpaFeedBackRepository.saveAll(var1.stream()
                .map(JpaFeedBackEntity::new)
                .collect(Collectors.toList()));
    }

    @Override
    public FeedBackEntity findById(Long var1) {
        return jpaFeedBackRepository.findById(var1).orElse(null);
    }

    @Override
    public boolean existsById(Long var1) {
        return jpaFeedBackRepository.existsById(var1);
    }

    @Override
    public Iterable<JpaFeedBackEntity> findAll() {
        return jpaFeedBackRepository.findAll();
    }

    @Override
    public Iterable<JpaFeedBackEntity> findAllById(Iterable<Long> var1) {
        return jpaFeedBackRepository.findAllById(var1);
    }

    @Override
    public long count() {
        return jpaFeedBackRepository.count();
    }

    @Override
    public void deleteById(Long var1) {
        jpaFeedBackRepository.deleteById(var1);
    }

    @Override
    public void delete(FeedBackEntity var1) {
        jpaFeedBackRepository.delete(new JpaFeedBackEntity(var1));
    }

    @Override
    public void deleteAll(List<FeedBackEntity> var1) {
        jpaFeedBackRepository.deleteAll(var1.stream()
                .map(JpaFeedBackEntity::new)
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteAll() {
        jpaFeedBackRepository.deleteAll();
    }
}
