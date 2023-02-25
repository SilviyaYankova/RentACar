package cource.project.dao.impl;

import cource.project.dao.*;

import java.sql.Connection;

public class DaoFactoryImp implements DaoFactory {
    @Override
    public UserRepository createUserRepository(Connection connection) {
        return new UserRepositoryJDBC(connection);
    }

    @Override
    public CarRepository createCarRepository(Connection connection, WorkerRepository workerRepository) {
        return new CarRepositoryJDBC(connection, workerRepository);
    }

    @Override
    public OrderRepository createOrderRepository(Connection connection, UserRepository userRepository, CarRepository carRepository) {
        return new OrderRepositoryJBDC(connection, userRepository, carRepository);
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
