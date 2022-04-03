package dao;

import exeption.NoneExistingEntityException;
import model.user.User;

import java.util.Collection;

public interface Repository<K, V extends Identifiable<K>> {

    V create(V entity);

    V findById(K id);

    Collection<V> findAll();

    void update(V entity) throws NoneExistingEntityException;

    void deleteById(K id) throws NoneExistingEntityException;

    long count();

    void clear();

    void addAll(Collection<V> entities);
}
