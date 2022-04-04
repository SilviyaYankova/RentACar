package dao.impl;


import dao.*;
import dao.file.CarsRepositoryFileImpl;
import dao.file.OrderRepositoryFileImpl;
import dao.file.UserRepositoryFileImpl;
import dao.file.WorkerRepositoryFileImpl;

public class DaoFactoryMemoryImp implements DaoFactory {


    @Override
    public UserRepository createUserRepository() {
        return new UserRepositoryMemoryImpl(new LongIdGenerator());
    }

    @Override
    public UserRepository createUserRepositoryFile(String dbFileName) {
        return new UserRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }


    @Override
    public CarRepository createCarRepository() {
        return new CarRepositoryMemoryImpl(new LongIdGenerator());
    }

    @Override
    public CarRepository createCarRepositoryFile(String dbFileName) {
        return new CarsRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }


    @Override
    public OrderRepository createOrderRepository() {
        return new OrderRepositoryMemoryImpl(new LongIdGenerator());
    }

    @Override
    public OrderRepository createOrderRepositoryFile(String dbFileName) {
        return new OrderRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }


    @Override
    public WorkerRepository createWorkerRepository() {
        return new WorkerRepositoryMemoryImpl(new LongIdGenerator());
    }

    @Override
    public WorkerRepository createWorkerRepository(String dbFileName) {
        return new WorkerRepositoryFileImpl(new LongIdGenerator(), dbFileName);
    }


    @Override
    public CommentRepository createCommentRepository() {
        return new CommentRepositoryMemoryImpl(new LongIdGenerator());
    }
}
