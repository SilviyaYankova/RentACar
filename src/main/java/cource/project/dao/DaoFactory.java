package cource.project.dao;

import cource.project.service.WorkerService;

import java.sql.Connection;

public interface DaoFactory {

    UserRepository createUserRepository(Connection connection);

    CarRepository createCarRepository (Connection connection, WorkerRepository workerRepository);

    OrderRepository createOrderRepository(Connection connection, UserRepository userRepository, CarRepository carRepository);

    WorkerRepository createWorkerRepository(Connection connection);

    CommentRepository createCommentRepository(Connection connection);
}
