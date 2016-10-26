package de.x4fyr.timeline.model.persistence;

import java.io.Serializable;

/**
 * Basic CRUD repository inspired by Spring Framework.
 *
 * @param <T> element to be handled
 * @param <I> identifier of the elements
 */
interface CRUDRepository<T, I extends Serializable> {

    void delete(I id);

    boolean exists(I id);

    T findOne(I id);

    <S extends T> S save(S entity);
}
