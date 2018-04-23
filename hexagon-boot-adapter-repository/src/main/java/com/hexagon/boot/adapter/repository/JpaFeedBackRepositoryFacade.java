package com.hexagon.boot.adapter.repository;

import org.springframework.data.repository.CrudRepository;

/**
 * 资源库操作具体实现方式
 * 不会暴露到外边
 */
interface JpaFeedBackRepositoryFacade extends CrudRepository<JpaFeedBackEntity, Long> {
}
