package com.hexagon.boot.domain.feedback.model;

import com.hexagon.boot.domain.BaseRepository;

/**
 *
 * 领域模型 自身是需要存储的，所以存储所需要的服务接口定义在 领域模型内部
 * 但是实现由于和具体技术相关，放入 端口适配层
 */
public interface FeedBackRepository  extends BaseRepository<FeedBackEntity> {

}
