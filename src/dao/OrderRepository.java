package dao;

import dao.file.PersistableRepository;
import model.Order;

public interface OrderRepository extends PersistableRepository<Long, Order> {
}
