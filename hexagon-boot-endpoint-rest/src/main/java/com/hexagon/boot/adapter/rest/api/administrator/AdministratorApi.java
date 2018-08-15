package com.hexagon.boot.adapter.rest.api.administrator;

import com.hexagon.boot.domain.administrator.AdministratorApplicationService;
import com.hexagon.boot.domain.administrator.model.Administrator;
import com.hexagon.boot.domain.administrator.model.exception.EmailExistException;
import com.hexagon.boot.domain.administrator.model.exception.MobilePhoneExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrator")
public class AdministratorApi {
    @Autowired
    private AdministratorApplicationService administratorApplicationService;

    /**
     * TODO 需要处理所有的 业务异常情况，或统一处理
     * @param administrator
     * @return
     * @throws MobilePhoneExistException
     * @throws EmailExistException
     */
    @PostMapping("/v1")
    public Administrator save(Administrator administrator) throws MobilePhoneExistException, EmailExistException {
        return administratorApplicationService.register(administrator);
    }

}
