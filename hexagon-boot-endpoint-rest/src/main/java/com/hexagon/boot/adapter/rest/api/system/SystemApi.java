package com.hexagon.boot.adapter.rest.api.system;

import com.hexagon.boot.adapter.rest.api.ResponseEntity;
import com.hexagon.boot.application.administrator.AdministratorAppService;
import com.hexagon.boot.domain.administrator.model.Administrator;
import com.hexagon.boot.domain.administrator.model.exception.NameOrPwdErrorException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class SystemApi {
    @Autowired
    private AdministratorAppService administratorAppService;

    /**
     * 系统管理登录操作
     * @param loginVO
     * @return
     */
    @PostMapping("/v1/login")
    public ResponseEntity<Administrator> login(@RequestBody LoginVO loginVO) {
        try {
            Administrator admin = administratorAppService.login(
                    loginVO.loginId,
                    loginVO.name,
                    loginVO.pwd,
                    loginVO.validCode);

            return new ResponseEntity<>(admin);
        } catch (NameOrPwdErrorException e) {
            return new ResponseEntity<>(e.code());
        }
    }

    @Setter
    @Getter
    static class LoginVO {
        private String name;
        private String pwd;
        private String validCode;
        private String loginId;
    }
}
