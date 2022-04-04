package Controller;

import dao.UserRepository;
import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.enums.CarStatus;
import model.enums.Role;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;
import view.BookingDialog;
import view.EditProfileDialog;
import view.Menu;
import view.Option;

import java.util.Collection;
import java.util.List;

public class UserController {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public UserController(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }


    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        userService.loadData();
        carService.loadData();
        orderService.loadData();

        Menu menu = new Menu();
        if (LOGGED_IN_USER.getRole().equals(Role.USER)) {
            menu = new Menu("User Menu", List.of(
                    new Option("See all cars", () -> {
                        Collection<Car> allCars = carService.getAllCarsWithStatus(CarStatus.AVAILABLE);

                        if (allCars.size() > 0) {
                            int cont = 0;
                            for (Car car : allCars) {
                                cont++;
                                System.out.println(cont + ". " + car);
                            }
                        } else {
                            System.out.println("Sorry there is no available cars for booking.");
                            System.out.println();
                        }

                        return "";
                    }),
                    new Option("Book Car", () -> {
                        Collection<Car> allCars = carService.getAllCarsWithStatus(CarStatus.AVAILABLE);
                        if (allCars.size() > 0) {
                            BookingDialog BookingDialog = new BookingDialog(userService, carService, orderService, userRepository);
                            BookingDialog.input(LOGGED_IN_USER);
                        } else {
                            System.out.println("Sorry there is no available cars for booking.");
                            System.out.println();
                        }


                        return "";
                    }),
                    new Option("Orders", () -> {
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository);
                        orderController.init(LOGGED_IN_USER);
                        return "orders\n";
                    }),
                    new Option("Comments", () -> {
                        // todo see all comments
                        // todo edit comments
                        // todo delete comments

                        return "Comments\n";
                    }),
                    new Option("See profile", () -> {
                        System.out.println(LOGGED_IN_USER);
                        System.out.println();
                        return "";
                    }),
                    new Option("Edit profile", () -> {
                        EditProfileDialog editProfileDialog = new EditProfileDialog(userService);
                        editProfileDialog.init(LOGGED_IN_USER);
                        return "";
                    })
            ));
        }
        if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR)) {
            System.out.println("Admin Panel");
            menu = new Menu("Admin Menu", List.of(
                    new Option("Users", () -> {
                        userService.loadData();
                        userService.getAllUsers().forEach(System.out::println);
                        System.out.println();
                        return "";
                    }),
                    new Option("Orders", () -> {
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository);
                        orderController.init(LOGGED_IN_USER);
                        return "";
                    }),
                    new Option("Cars", () -> {
                        // todo ask about cars status or do a car dialog
                        Collection<Car> allCars = carService.getAllCars();
                        int cont = 0;
                        for (Car car : allCars) {
                            cont++;
                            System.out.println(cont + ". " + car);
                        }
                        return "All available car are successfully shown.\n";
                    }),
                    new Option("Comments", () -> {
                        // todo see all comments
                        // todo edit comments
                        // todo delete comments

                        return "Comments\n";
                    }),
                    new Option("See profile", () -> {
                        System.out.println(LOGGED_IN_USER);
                        System.out.println();
                        return "";
                    }),
                    new Option("Edit profile", () -> {
                        EditProfileDialog editProfileDialog = new EditProfileDialog(userService);
                        editProfileDialog.init(LOGGED_IN_USER);
                        return "";
                    })
            ));
        }
        if (LOGGED_IN_USER.getRole().equals(Role.SELLER)) {

            menu = new Menu("Seller Menu", List.of(
                    new Option("Orders", () -> {
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository);
                        orderController.init(LOGGED_IN_USER);
                        return "";
                    }),
                    new Option("Cars", () -> {
                        // todo ask about cars status or do a car dialog
                        Collection<Car> allCars = carService.getAllCars();
                        int cont = 0;
                        for (Car car : allCars) {
                            cont++;
                            System.out.println(cont + ". " + car);
                        }
                        return "All available car are successfully shown.\n";
                    }),
                    new Option("Comments", () -> {
                        // todo see all comments
                        // todo edit comments
                        // todo delete comments

                        return "Comments\n";
                    }),
                    new Option("See profile", () -> {
                        System.out.println(LOGGED_IN_USER);
                        System.out.println();
                        return "";
                    }),
                    new Option("Edit profile", () -> {
                        EditProfileDialog editProfileDialog = new EditProfileDialog(userService);
                        editProfileDialog.init(LOGGED_IN_USER);
                        return "";
                    })
            ));
        }
        if (LOGGED_IN_USER.getRole().equals(Role.DRIVER)) {
            menu = new Menu("Driver Menu", List.of(
                    new Option("Orders", () -> {
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository);
                        orderController.init(LOGGED_IN_USER);
                        return "";
                    }),
                    new Option("Cars", () -> {
                        // todo ask about cars status or do a car dialog
                        Collection<Car> allCars = carService.getAllCars();
                        int cont = 0;
                        for (Car car : allCars) {
                            cont++;
                            System.out.println(cont + ". " + car);
                        }
                        return "All available car are successfully shown.\n";
                    }),
                    new Option("Comments", () -> {
                        // todo see all comments
                        // todo edit comments
                        // todo delete comments

                        return "Comments\n";
                    }),
                    new Option("See profile", () -> {
                        System.out.println(LOGGED_IN_USER);
                        System.out.println();
                        return "";
                    }),
                    new Option("Edit profile", () -> {
                        EditProfileDialog editProfileDialog = new EditProfileDialog(userService);
                        editProfileDialog.init(LOGGED_IN_USER);
                        return "";
                    })
            ));
        }
        if (LOGGED_IN_USER.getRole().equals(Role.SITE_MANAGER)) {
            menu = new Menu("Site Manager Menu", List.of(

                    new Option("See profile", () -> {
                        System.out.println(LOGGED_IN_USER);
                        System.out.println();
                        return "";
                    }),
                    new Option("Edit profile", () -> {
                        EditProfileDialog editProfileDialog = new EditProfileDialog(userService);
                        editProfileDialog.init(LOGGED_IN_USER);
                        return "";
                    })
            ));
        }

        menu.show();
    }
}
