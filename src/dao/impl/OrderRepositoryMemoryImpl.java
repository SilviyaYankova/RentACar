package dao.impl;

import dao.IdGenerator;
import dao.OrderRepository;
import model.Order;

public class OrderRepositoryMemoryImpl extends AbstractPersistableRepository<Long, Order> implements OrderRepository {

    public OrderRepositoryMemoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
