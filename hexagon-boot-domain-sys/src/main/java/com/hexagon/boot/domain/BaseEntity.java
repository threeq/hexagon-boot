package com.hexagon.boot.domain;

import com.hexagon.boot.libs.util.Asserts;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity<E extends BaseEntity> {
    Long id;
    Long created;
    Long updated;
    Boolean active;    // true:可用； false:不可用

    public BaseEntity() {
        this.setActive(true);
        this.created = System.currentTimeMillis();
    }

    protected BaseEntity(BaseEntity entity) {
        this.id = entity.id;
        this.created = entity.created;
        this.updated = entity.updated;
        this.active = entity.active;
    }

    public <R extends BaseRepository<E>> E load(R repository) {
        Asserts.notNull(this.getId(), "id 不能为空");
        return repository.findById(this.getId());
    }

    public <R extends BaseRepository<E>> E save(R repository) {
        this.setUpdated(System.currentTimeMillis());
        if(this.getId()!=null) {
            return repository.update((E) this);
        } else {
            return repository.insert((E) this);
        }
    }

    public <R extends BaseRepository<E>> E delete(R repository) {
        Asserts.notNull(this.getId(), "id 不能为空");
        E old = repository.findById(this.getId());
        this.setActive(false);
        this.setUpdated(System.currentTimeMillis());
        repository.delete((E) this);
        return old;
    }

    public <R extends BaseRepository<E>> E write(R repository) {
        Asserts.notNull(this.getId(), "id 不能为空");
        this.setUpdated(System.currentTimeMillis());
        return repository.write((E) this);
    }

    public <R extends BaseRepository<E>> void disabled(R repository) {
        this.setActive(false);
        this.setUpdated(System.currentTimeMillis());
        repository.update((E) this);
    }

}
