package cource.project;

import cource.project.Controller.HomeController;
import cource.project.dao.*;
import cource.project.dao.DaoFactory;
import cource.project.dao.impl.DaoFactoryImp;
import cource.project.exeption.*;
import cource.project.model.user.User;
import cource.project.service.*;
import cource.project.service.impl.*;
import cource.project.util.JdbcUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static cource.project.util.JdbcUtils.closeConnection;
import static cource.project.util.JdbcUtils.createDbConnection;

@Slf4j
public class Main {
    public static void main(String[] args) throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException, SQLException, IOException, ClassNotFoundException {
        Properties props = new Properties();
        String dbConfigPath = Main.class.getClassLoader().getResource("jdbc.properties").getPath();
        props.load(new FileInputStream(dbConfigPath));
        log.info("jdbc.properties: {}", props);

        Connection connection = createDbConnection(props);

        DaoFactory daoFactory = new DaoFactoryImp();
        UserRepository userRepository = daoFactory.createUserRepository(connection);


        CarRepository carRepository = daoFactory.createCarRepository(connection);
        OrderRepository orderRepository = daoFactory.createOrderRepository(connection);
        WorkerRepository workerRepository = daoFactory.createWorkerRepository(connection);
        CommentRepository commentRepository = daoFactory.createCommentRepository(connection);

        WorkerService workerService = new WorkerServiceImpl(workerRepository, carRepository);
        CarService carService = new CarServiceImpl(carRepository, workerService, userRepository, orderRepository);
        OrderService orderService = new OrderServiceImpl(orderRepository, userRepository, carService);

        CommentService commentService = new CommentServiceImpl(commentRepository, carService, userRepository);
        UserService userService = new UserServiceImpl(userRepository, workerService, orderService, carService, commentService);


        HomeController homeController = new HomeController(userService, carService, orderService, userRepository, workerService, commentService);
        homeController.init();


        closeConnection(connection);
    }

}
