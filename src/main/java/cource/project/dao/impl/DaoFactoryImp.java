package cource.project.dao.impl;
import cource.project.dao.*;

import java.sql.Connection;

public class DaoFactoryImp implements DaoFactory {

    @Override
    public UserRepository createUserRepository(Connection connection) {
        return new UserRepositoryImpl(connection);
    }

    @Override
    public CarRepository createCarRepository(Connection connection) {
        return new CarRepositoryImpl(connection);
    }

    @Override
    public OrderRepository createOrderRepository(Connection connection) {
        return new OrderRepositoryImpl(connection);
    }

    @Override
    public WorkerRepository createWorkerRepository(Connection connection) {
        return new WorkerRepositoryImpl(connection);
    }

    @Override
    public CommentRepository createCommentRepository(Connection connection) {
        return new CommentRepositoryImpl(connection);
    }
}
