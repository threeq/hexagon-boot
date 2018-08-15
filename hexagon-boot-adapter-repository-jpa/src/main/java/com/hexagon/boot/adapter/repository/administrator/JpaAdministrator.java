package com.hexagon.boot.adapter.repository.administrator;

import com.hexagon.boot.domain.administrator.model.Administrator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JpaAdministrator extends Administrator {
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getPwd() {
        return super.getPwd();
    }

    @Override
    public String getMobilePhone() {
        return super.getMobilePhone();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    public JpaAdministrator() {
        super();
    }

    public JpaAdministrator(Administrator administrator) {
        super(administrator);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public Long getCreated() {
        return super.getCreated();
    }

    @Override
    public Long getUpdated() {
        return super.getUpdated();
    }

    @Override
    public Boolean getActive() {
        return super.getActive();
    }
}
