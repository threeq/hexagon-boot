package com.hexagon.boot.adapter.repository.feedback;

import com.hexagon.boot.adapter.repository.JpaBaseRepositoryImpl;
import com.hexagon.boot.domain.feedback.model.FeedBackEntity;
import com.hexagon.boot.domain.feedback.model.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源库依赖实现
 */
@Repository
public class JpaFeedBackRepository extends JpaBaseRepositoryImpl<FeedBackEntity, JpaFeedBackEntity>
        implements FeedBackRepository {

    @Autowired
    private void setJpaUserRepositoryFacade(JpaFeedBackRepositoryFacade jpaFeedBackRepositoryFacade) {
        this.jpaCrudRepository = jpaFeedBackRepositoryFacade;
    }

    @Override
    protected JpaFeedBackEntity translateDao(FeedBackEntity entity) {
        return new JpaFeedBackEntity(entity);
    }

    @Override
    protected FeedBackEntity translateEntity(JpaFeedBackEntity dao) {
        return new FeedBackEntity(dao);
    }
}
