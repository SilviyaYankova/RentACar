package cource.project.dao.impl;

import cource.project.dao.OrderRepository;
import cource.project.model.Order;

import java.sql.Connection;

public class OrderRepositoryImpl extends AbstractRepository<Long, Order> implements OrderRepository{

    protected OrderRepositoryImpl(Connection connection) {
        super(connection);
    }
}
