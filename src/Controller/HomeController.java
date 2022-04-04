package Controller;

import dao.UserRepository;
import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;
import service.WorkerService;
import view.*;

import java.util.Collection;
import java.util.List;

public class HomeController {
    private static User LOGGED_IN_USER = null;

    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final WorkerService workerService;

    public HomeController(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository, WorkerService workerService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.workerService = workerService;
    }

    public void init() throws NoneAvailableEntityException, NoneExistingEntityException, NoPermissionException, InvalidEntityDataException {
        userService.loadData();
        carService.loadData();
        orderService.loadData();
        orderService.checkFinishedOrders();

        Menu menu = new Menu("Home Menu", List.of(
                new Option("See all cars", () -> {
                    Collection<Car> allCars = carService.getAllCars();
                    allCars.forEach(System.out::println);
                    System.out.println();
                    return "All Cars shown successfully.\n";
                }),
                new Option("Login", () -> {
//                    User user = new LoginDialog(userService).input();
//
//                    while (user.getUsername() == null && user.getPassword() == null) {
//                        System.out.println("Bad credentials. Try again.");
//                        System.out.println();
//                        user = new LoginDialog(userService).input();
//                    }
//                    LOGGED_IN_USER = user;
                    User userById = userService.getUserById(3L);
//                    Order orderById = orderService.getOrderById(1L);
//                    Driver driver = (Driver) userService.getUserById(4L);
//                    driver.getPickUpDates().add(orderById.getPickUpDate());
//                    driver.getDropOffDate().add(orderById.getDropOffDate());
//                    userService.editUser(driver);
//                    orderById.setDriver(driver);
//
//                    orderService.editOrder(orderById);

                    LOGGED_IN_USER = userById;
                    System.out.println(LOGGED_IN_USER.getUsername() + " logged in successfully.");
                    System.out.println();

                    UserController userController = new UserController(userService, carService, orderService, userRepository, workerService);
                    userController.init(LOGGED_IN_USER);

                    return "";
                }),
                new Option("Register", () -> {
                    User user = new RegisterDialog(userService).input();
                    User created = userService.registerUser(user);
                    if (created == null) {
                        return "Username already exist.";
                    }
                    return String.format("User ID:%s: '%s' added successfully.%n",
                            created.getId(), created.getUsername());
                })
        ));
        menu.show();
    }
}
