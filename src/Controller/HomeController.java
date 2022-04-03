package Controller;

import dao.UserRepository;
import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.enums.CarStatus;
import model.enums.DriverStatus;
import model.enums.Role;
import model.user.Driver;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;
import view.*;

import java.util.Collection;
import java.util.List;

public class HomeController {
    private static User LOGGED_IN_USER = null;

    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public HomeController(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    public void init() throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException, NoPermissionException {
        userService.loadData();
        carService.loadData();
//        orderService.loadData();

        Menu menu = new Menu("Home Menu", List.of(
                new Option("See all cars", () -> {
                    Collection<Car> allCars = carService.getAllCars();
                    allCars.forEach(System.out::println);
                    System.out.println();
                    return "All Cars shown successfully.\n";
                }),
                new Option("Login", () -> {
//                    User user = new LoginDialog(userService).input();

//                    while (user.getUsername() == null && user.getPassword() == null) {
//                        System.out.println("Bad credentials. Try again.");
//                        System.out.println();
//                        user = new LoginDialog(userService).input();
//                    }
//                        LOGGED_IN_USER = user;
                    LOGGED_IN_USER = userService.getUserById(6L);
                    System.out.printf("'%s' logged in successfully%n%n", LOGGED_IN_USER.getUsername());
                    if (LOGGED_IN_USER.getRole().equals(Role.USER)) {
                        UserController userController = new UserController(userService, carService, orderService, userRepository);
                        userController.init(LOGGED_IN_USER);
                    } else if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR)) {

                    } else if (LOGGED_IN_USER.getRole().equals(Role.SELLER)) {

                    } else if (LOGGED_IN_USER.getRole().equals(Role.DRIVER)) {

                    } else if (LOGGED_IN_USER.getRole().equals(Role.SITE_MANAGER)) {

                    }
                    return "";
                }),
                new Option("Register", () -> {
                    // todo if is register and is logged in to send me to another view; after register to be logged in
                    User user = new RegisterDialog().input();
                    user.setRole(Role.USER);
                    User created = userService.registerUser(user);
                    return String.format("User ID:%s: '%s' added successfully.%n",
                            created.getId(), created.getUsername());
                }),
                new Option("get all users", () -> {
                    // todo if is register and is logged in to send me to another view; after register to be logged in
                    userService.loadData();
                    userService.getAllUsers().forEach(System.out::println);
                    System.out.println();
                    return "users printed";
                }),
                new Option("get all cars", () -> {
                    // todo if is register and is logged in to send me to another view; after register to be logged in
                    carService.loadData();
                    carService.getAllCars().forEach(System.out::println);
                    return "cars printed";
                }),
                new Option("get all orders", () -> {
                    orderService.loadData();
                    Collection<Order> allOrders = orderService.getAllOrders();

                    if (allOrders.size() == 0) {
                        System.out.println("no orders");
                    } else {
                        orderService.loadData();
                        orderService.getAllOrders().forEach(System.out::println);

                    }
                    return "orders printed";
                })
        ));
        menu.show();
    }
}
