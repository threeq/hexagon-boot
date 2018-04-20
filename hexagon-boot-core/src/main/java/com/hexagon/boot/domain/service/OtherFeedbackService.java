package com.hexagon.boot.domain.service;

import com.hexagon.boot.domain.model.FeedBackEntity;

/**
 * 领域模型内部所需要依赖的外部服务
 * 需要依赖服务和模型的定义在领域模型内部，但是实现由于和具体技术相关，放入 端口适配层
 */
public interface OtherFeedbackService {
    FeedBackEntity getById(Long id);
}
