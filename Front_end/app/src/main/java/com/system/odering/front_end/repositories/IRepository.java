package com.system.odering.front_end.repositories;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/13.
 */
public interface IRepository<E, ID> {
    E findById(ID id);

    E save(E entity);

    E update(E entity);

    E delete(E entity);

    Set<E> findAll();

    int deleteAll();
}