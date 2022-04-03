import Controller.HomeController;
import dao.*;
import dao.impl.DaoFactoryMemoryImp;
import exeption.*;
import model.Car;
import model.Worker;
import model.user.User;
import service.*;
import service.impl.*;

import java.util.stream.Collectors;

import static model.mock.MockCar.MOCK_CARS;
import static model.mock.MockUser.MOCK_USERS;
import static model.mock.MockWorker.MOCK_WORKERS;

public class Main {
    public static final String USERS_DB_FILENAME = "users.db";
    public static final String CARS_DB_FILENAME = "cars.db";
    public static final String ORDERS_DB_FILENAME = "orders.db";

    public static void main(String[] args) throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException, NoPermissionException {
        DaoFactory daoFactory = new DaoFactoryMemoryImp();

        CarRepository carRepository = daoFactory.createCarRepositoryFile(CARS_DB_FILENAME);
        UserRepository userRepository = daoFactory.createUserRepositoryFile(USERS_DB_FILENAME);
        OrderRepository orderRepository = daoFactory.createOrderRepositoryFile(ORDERS_DB_FILENAME);



        WorkerRepository workerRepository = daoFactory.createWorkerRepository();
        CommentRepository commentRepository = daoFactory.createCommentRepository();

        WorkerService workerService = new WorkerServiceImpl(workerRepository, carRepository);
        CarService carService = new CarServiceImpl(carRepository, workerService);

        OrderService orderService = new OrderServiceImpl(orderRepository, userRepository, carService);
        CommentService commentService = new CommentServiceImpl(commentRepository, carService, userRepository);
        UserService userService = new UserServiceImpl(userRepository, workerService, orderService, carService, commentService);

// create initial users
//        createInitialUsers(userService);
//        createInitialCars(carService);
//        createInitialWorkers(workerService);

        HomeController homeController = new HomeController(userService, carService, orderService, userRepository);
        homeController.init();


    }

    private static void createInitialWorkers(WorkerService workerService) throws NoneExistingEntityException {

        for (Worker MOCK_WORKER : MOCK_WORKERS) {
                workerService.addWorker(MOCK_WORKER);
        }
    }

    private static void createInitialCars(CarService carService) {
        for (Car MOCK_CAR : MOCK_CARS) {
            try {
                carService.addCar(MOCK_CAR);
            } catch (InvalidEntityDataException e) {
                printErrors(e);
            }
        }
    }

    private static void printErrors(InvalidEntityDataException ex) {
        StringBuilder sb = new StringBuilder(ex.getMessage());

        if (ex.getCause() instanceof ConstraintViolationException) {
            sb.append(", invalid fields:\n");
            var violations = ((ConstraintViolationException) ex.getCause()).getFieldViolations();
            sb.append(violations.stream()
                    .map(v -> String.format(" - %s.%s [%s] - %s",
                            v.getType().substring(v.getType().lastIndexOf(".") + 1),
                            v.getField(),
                            v.getInvalidValue(),
                            v.getErrorMessage())
                    ).collect(Collectors.joining("\n"))
            );
        }
        System.out.println(sb);
    }

    private static void createInitialUsers(UserService userService) {
        for (User MOCK_USER : MOCK_USERS) {
            try {
                userService.registerUser(MOCK_USER);
            } catch (InvalidEntityDataException e) {
                printErrors(e);
            }
        }
    }
}
