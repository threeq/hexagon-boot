package com.hexagon.boot.adapter.repository.feedback;

import com.hexagon.boot.adapter.repository.JpaBaseRepositoryFacade;

/**
 * 资源库操作具体实现方式
 * 不会暴露到外边
 */
interface JpaFeedBackRepositoryFacade extends JpaBaseRepositoryFacade<JpaFeedBackEntity, Long> {
}
