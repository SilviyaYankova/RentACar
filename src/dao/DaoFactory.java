package dao;

public interface DaoFactory {

    UserRepository createUserRepositoryFile(String dbFileNAme);

    UserRepository createUserRepository();


    CarRepository createCarRepository ();

    CarRepository createCarRepositoryFile(String dbFileName);


    OrderRepository createOrderRepository();

    OrderRepository createOrderRepositoryFile(String dbFileName);


    WorkerRepository createWorkerRepository();

    WorkerRepository createWorkerRepository(String dbFileName);


    CommentRepository createCommentRepository();
    CommentRepository createCommentRepository(String dbFileName);
}
