package cource.project.dao;

import cource.project.exeption.NoneExistingEntityException;

import java.util.Collection;

public interface Repository<K, V> {

    V create(V entity);

    V findById(K id);

    Collection<V> findAll();

    void update(V entity) throws NoneExistingEntityException;

    void deleteById(K id) throws NoneExistingEntityException;

}
