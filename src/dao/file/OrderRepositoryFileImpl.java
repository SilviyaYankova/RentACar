package dao.file;

import dao.IdGenerator;
import dao.OrderRepository;
import dao.impl.PersistableRepositoryFileImpl;
import model.Order;

public class OrderRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Order> implements OrderRepository {


    public OrderRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
