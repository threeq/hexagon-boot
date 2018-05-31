package com.hexagon.boot.application.administrator;

import com.hexagon.boot.domain.administrator.model.Administrator;
import com.hexagon.boot.domain.administrator.model.AdministratorRepository;
import com.hexagon.boot.domain.administrator.model.exception.EmailExistException;
import com.hexagon.boot.domain.administrator.model.exception.MobilePhoneExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorAppService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator register(Administrator administrator) throws MobilePhoneExistException, EmailExistException {
        return administrator.save(administratorRepository);
    }
}
