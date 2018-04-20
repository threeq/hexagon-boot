package com.hexagon.boot.adapter.other.domain;

import com.hexagon.boot.domain.model.FeedBackEntity;
import com.hexagon.boot.domain.service.OtherFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 外部服务依赖实现
 */
@Service
public class Other1FeedbackService implements OtherFeedbackService {

    @Autowired
    OtherDomainAdapter otherDomainAdapter;

    @Override
    public FeedBackEntity getById(Long id) {
        return otherDomainAdapter.getById(id);
    }
}
