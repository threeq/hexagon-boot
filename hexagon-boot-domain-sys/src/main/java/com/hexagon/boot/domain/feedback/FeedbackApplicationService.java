package com.hexagon.boot.domain.feedback;


import com.hexagon.boot.domain.feedback.model.FeedBackEntity;
import com.hexagon.boot.domain.feedback.model.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用服务器层
 */
@Service
public class FeedbackApplicationService {

    @Autowired
    FeedBackRepository repository;

    public FeedBackEntity save(FeedBackEntity feedBackEntity) {
        return repository.write(feedBackEntity);
    }

    public Iterable<FeedBackEntity> findAll() {
        return repository.findAll();
    }

}
