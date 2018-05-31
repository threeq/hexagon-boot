package com.hexagon.boot.domain.administrator.model;

import com.hexagon.boot.domain.BaseRepository;

import java.util.Optional;

public interface AdministratorRepository extends BaseRepository<Administrator> {
    Optional<Administrator> findByMobilePhone(String mobilePhone);
    Optional<Administrator> findByEmail(String Email);
}
