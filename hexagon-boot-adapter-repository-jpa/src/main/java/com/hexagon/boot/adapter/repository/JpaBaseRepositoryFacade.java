package com.hexagon.boot.adapter.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface JpaBaseRepositoryFacade<D, K>
        extends PagingAndSortingRepository<D, K>, JpaSpecificationExecutor<D> {
}
