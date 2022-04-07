package cource.project.dao;

import java.sql.Connection;

public interface DaoFactory {

    UserRepository createUserRepository(Connection connection);

    CarRepository createCarRepository (Connection connection);

    OrderRepository createOrderRepository(Connection connection);

    WorkerRepository createWorkerRepository(Connection connection);

    CommentRepository createCommentRepository(Connection connection);
}
