package cource.project.dao.impl;


import cource.project.dao.Repository;
import cource.project.exeption.NoneExistingEntityException;

import java.util.Collection;

public abstract class AbstractRepository<K, V> implements Repository<K, V> {

    @Override
    public V create(V entity) {
        return null;
    }

    @Override
    public V findById(K id) {
        return null;
    }

    @Override
    public Collection<V> findAll() {
        return null;
    }

    @Override
    public void update(V entity) throws NoneExistingEntityException {

    }

    @Override
    public void deleteById(K id) throws NoneExistingEntityException {

    }

    @Override
    public long count() {
        return 0;
    }
}
