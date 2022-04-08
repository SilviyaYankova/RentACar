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
import java.util.List;
import java.util.Properties;

@Slf4j
public class Main {
    public static final String SELECT_All_USERS = "SELECT * FROM `rent-a-car`.users;";

    public static void main(String[] args) throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException, SQLException, IOException, ClassNotFoundException {
        Properties props = new Properties();
        String dbConfigPath = Main.class.getClassLoader().getResource("jdbc.properties").getPath();
        props.load(new FileInputStream(dbConfigPath));
        log.info("jdbc.properties: {}", props);

// 1. Load DB Driver
        try {
            Class.forName(props.getProperty("driver"));
        } catch (ClassNotFoundException ex) {
            log.error("DB driver class not found", ex);
            throw ex;
        }
        log.info("DB driver loaded successfully: {}", props.getProperty("driver"));

        // 2. Create DB Connection and 3.Create Statement
        try (var con = DriverManager.getConnection(props.getProperty("url"), props);
             var stmt = con.prepareStatement(SELECT_All_USERS)) {
            log.info("DB Connection established successfully to schema: {}", con.getCatalog());
            // 4. Set params and execute SQL query
//            stmt.setDouble(1, 40.0);
            var rs = stmt.executeQuery();
            // 5. Transform ResultSet to Book
            List<User> results = new ArrayList<>();


            while (rs.next()) {
                String registered_on = rs.getString("registered_on");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(registered_on, formatter);
                results.add(new User(
                        rs.getLong(1),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("repeat_password"),
                        localDateTime
                ));
            }

            for (var user : results) {
                System.out.println(user);
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw ex;
        }


        DaoFactory daoFactory = new DaoFactoryImp();
        Connection connection = JdbcUtils.createDbConnection(props);
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


//        HomeController homeController = new HomeController(userService, carService, orderService, userRepository, workerService, commentService);
//        homeController.init();
    }

}
