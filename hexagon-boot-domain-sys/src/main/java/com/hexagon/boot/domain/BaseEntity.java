package com.hexagon.boot.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity {
    Long id;
    Long created;
    Long updated;
    int state;    // 0:删除；1:创建；2:修改

    public BaseEntity() {
        this.state = 1;
        this.created = System.currentTimeMillis();
    }

    protected BaseEntity(BaseEntity entity) {
        this.id = entity.id;
        this.created = entity.created;
        this.updated = entity.updated;
        this.state = entity.state;
    }

}
