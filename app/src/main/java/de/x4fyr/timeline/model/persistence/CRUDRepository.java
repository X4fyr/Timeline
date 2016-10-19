package de.x4fyr.timeline.model.persistence;

import java.io.Serializable;

/**
 * Basic CRUD repository inspired by Spring Framework
 */
public interface CRUDRepository<T,ID extends Serializable> {

    void delete(ID id);

    boolean exists(ID id);

    T findOne(ID id);

    <S extends T> S save(S entity);
}
