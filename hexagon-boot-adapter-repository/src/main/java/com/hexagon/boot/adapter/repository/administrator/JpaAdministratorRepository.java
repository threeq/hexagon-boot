package com.hexagon.boot.adapter.repository.administrator;

import com.hexagon.boot.adapter.repository.JpaBaseRepositoryImpl;
import com.hexagon.boot.domain.administrator.model.Administrator;
import com.hexagon.boot.domain.administrator.model.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaAdministratorRepository
        extends JpaBaseRepositoryImpl<Administrator, JpaAdministrator>
        implements AdministratorRepository {

    @Autowired
    private void setJpaRepositoryFacade(JpaAdministratorRepositoryFacade jpaAdministratorRepositoryFacade) {
        this.jpaCrudRepository = jpaAdministratorRepositoryFacade;
    }

    @Override
    protected JpaAdministrator translateDao(Administrator entity) {
        return new JpaAdministrator(entity);
    }

    @Override
    protected Administrator translateEntity(JpaAdministrator dao) {
        return new Administrator(dao);
    }

    @Override
    public Optional<Administrator> findByMobilePhone(String mobilePhone) {
        return this.jpaCrudRepository.findOne((Specification<JpaAdministrator>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.<String>get("mobilePhone"), mobilePhone));

            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }).map(Administrator::new);
    }

    @Override
    public Optional<Administrator> findByEmail(String email) {
        return this.jpaCrudRepository.findOne((Specification<JpaAdministrator>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.<String>get("email"), email));

            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }).map(Administrator::new);
    }
}
