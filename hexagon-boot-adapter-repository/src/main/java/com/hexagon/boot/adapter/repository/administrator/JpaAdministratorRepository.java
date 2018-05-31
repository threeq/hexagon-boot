package com.hexagon.boot.adapter.repository.administrator;

import com.hexagon.boot.adapter.repository.JpaBaseRepositoryImpl;
import com.hexagon.boot.domain.administrator.model.Administrator;
import com.hexagon.boot.domain.administrator.model.AdministratorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAdministratorRepository
        extends JpaBaseRepositoryImpl<Administrator, JpaAdministrator>
        implements AdministratorRepository {
    @Override
    protected JpaAdministrator translateDao(Administrator entity) {
        return new JpaAdministrator(entity);
    }

    @Override
    protected Administrator translateEntity(JpaAdministrator dao) {
        return new Administrator(dao);
    }
}
