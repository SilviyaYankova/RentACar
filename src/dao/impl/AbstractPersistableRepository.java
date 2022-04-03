package dao.impl;

import dao.IdGenerator;
import dao.Identifiable;
import dao.file.PersistableRepository;
import exeption.NoneExistingEntityException;
import model.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPersistableRepository<K, V extends Identifiable<K>> implements PersistableRepository<K, V> {



    private Map<K, V> map = new HashMap<>();
    private IdGenerator<K> idGenerator;

    protected AbstractPersistableRepository(IdGenerator<K> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public V create(V entity) {
        K nextId = idGenerator.getNextId();
        entity.setId(nextId);
        map.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public V findById(K id) {
        return map.get(id);
    }

    @Override
    public Collection<V> findAll() {
        return map.values();
    }

    @Override
    public void update(V entity) throws NoneExistingEntityException {
        V old = findById(entity.getId());
        if (old == null) {
            throw new NoneExistingEntityException(entity.getClass().getSimpleName() + " with ID='" + entity.getId() + "' does not exist.");
        }

        map.put(entity.getId(), entity);

    }

    @Override
    public void deleteById(K id) throws NoneExistingEntityException {
        V old = map.remove(id);
        if (old == null) {
            throw new NoneExistingEntityException("Entity with ID='" + id + "' does not exist.");
        }

    }

    @Override
    public long count() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    public IdGenerator<K> getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(IdGenerator<K> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public void addAll(Collection<V> entities) {
        for(var entity: entities) {
            map.put(entity.getId(), entity);
        }
    }
}
