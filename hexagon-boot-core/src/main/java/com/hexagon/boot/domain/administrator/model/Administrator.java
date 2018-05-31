package com.hexagon.boot.domain.administrator.model;

import com.hexagon.boot.domain.BaseEntity;
import com.hexagon.boot.domain.administrator.model.exception.EmailExistException;
import com.hexagon.boot.domain.administrator.model.exception.MobilePhoneExistException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Administrator extends BaseEntity {

    private String name;
    private String pwd;
    private String mobilePhone;
    private String email;

    public Administrator(Administrator administrator) {
        super(administrator);
    }

    public Administrator save(AdministratorRepository administratorRepository) throws EmailExistException, MobilePhoneExistException {
        if(this.getId()==null) {
            // 新增管理员
            checkMobilePhone(administratorRepository);
            checkEmail(administratorRepository);
        } else {
            // 修改管理员
            Administrator old = administratorRepository.findById(this.getId());
            if(!old.getMobilePhone().equals((this.mobilePhone))) {
                checkMobilePhone(administratorRepository);
            }
            if(!old.getEmail().equals(this.email)) {
                checkEmail(administratorRepository);
            }
        }

        return administratorRepository.save(this);
    }

    private boolean checkEmail(AdministratorRepository administratorRepository) throws MobilePhoneExistException {
        Administrator admini = administratorRepository.findById(this.getId());
        if(admini!=null) {
            throw new MobilePhoneExistException();
        }
        return true;
    }

    private boolean checkMobilePhone(AdministratorRepository administratorRepository) throws EmailExistException {
        Administrator admini = administratorRepository.findById(this.getId());
        if(admini!=null) {
            throw new EmailExistException();
        }
        return true;
    }
}
