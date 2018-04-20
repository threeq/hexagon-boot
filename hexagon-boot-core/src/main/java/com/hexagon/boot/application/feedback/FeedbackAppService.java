package com.hexagon.boot.application.feedback;


import com.hexagon.boot.domain.model.FeedBackEntity;
import com.hexagon.boot.domain.model.FeedBackRepository;
import com.hexagon.boot.domain.service.OtherFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用服务器层
 */
@Service
public class FeedbackAppService {

    @Autowired
    FeedBackRepository repository;

    @Autowired(required = false)
    OtherFeedbackService outerFeedbackService;

    public FeedBackEntity save(FeedBackEntity feedBackEntity) {
        return repository.save(feedBackEntity);
    }

    public Iterable<FeedBackEntity> findAll() {
        return repository.findAll();
    }

    public FeedBackEntity getById(Long id){
        return outerFeedbackService.getById(id);
    }
}
