package Controller;

import dao.UserRepository;
import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Worker;
import model.enums.CarStatus;
import model.enums.Role;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;
import service.WorkerService;
import view.*;

import java.util.Collection;
import java.util.List;

public class UserController {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final WorkerService workerService;

    public UserController(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository, WorkerService workerService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.workerService = workerService;
    }


    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        userService.loadData();
        carService.loadData();
        orderService.loadData();
        workerService.loadData();

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
                            BookingDialog BookingDialog = new BookingDialog(userService, carService, orderService, userRepository, workerService);
                            BookingDialog.input(LOGGED_IN_USER);
                        } else {
                            System.out.println("Sorry there is no available cars for booking.");
                            System.out.println();
                        }


                        return "";
                    }),
                    new Option("Orders", () -> {
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository, workerService);
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
            menu = new Menu("Admin Menu", List.of(
                    new Option("Users", () -> {
                        userService.loadData();
                        userService.getAllUsers().forEach(System.out::println);
                        System.out.println();
                        return "";
                    }),
                    new Option("Orders", () -> {
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository, workerService);
                        orderController.init(LOGGED_IN_USER);
                        return "";
                    }),
                    new Option("Cars", () -> {
                        // todo ask about cars status or do a car dialog
                        CarController carController = new CarController(userService, carService);
                        carController.init(LOGGED_IN_USER);

                        return "";
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
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository, workerService);
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
                        OrderController orderController = new OrderController(userService, carService, orderService, userRepository, workerService);
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
                    new Option("See all cars for cleaning", () -> {
                        List<Car> allCarsWithStatus = carService.getAllCarsWithStatus(CarStatus.WAITING_FOR_CLEANING);

                        if (allCarsWithStatus.size() > 0) {
                            int count = 0;
                            for (Car car : allCarsWithStatus) {
                                count++;
                                System.out.println(count + ". \t" + car);
                            }
                        } else {
                            System.out.println("There is no cars waiting for cleaning.");
                        }

                        return "";
                    }),
                    new Option("See all workers", () -> {
                        Collection<Worker> allWorkers = workerService.getAllWorkers();
                        allWorkers.forEach(System.out::println);

                        return "";
                    }),
                    new Option("Assign Workers to clean cars", () -> {
                        List<Car> allCarsWithStatus = carService.getAllCarsWithStatus(CarStatus.WAITING_FOR_CLEANING);

                        if (allCarsWithStatus.size() > 0) {
                            AssignWorkersDialog cleaningDialog = new AssignWorkersDialog(carService, workerService, userService);
                            cleaningDialog.init(LOGGED_IN_USER);
                        } else {
                            System.out.println("There is no cars waiting for cleaning.");
                            System.out.println();
                        }

                        return "";
                    }),
                    new Option("See all cars currently cleaning", () -> {
                        List<Car> allCarsWithStatus = carService.getAllCarsWithStatus(CarStatus.CLEANING);

                        if (allCarsWithStatus.size() > 0) {
                            int count = 0;
                            for (Car car : allCarsWithStatus) {
                                count++;
                                System.out.println(count + ". \t" + car);
                            }
                        } else {
                            System.out.println("There is no cars waiting for cleaning.");
                        }

                        return "";
                    }),
                    new Option("Finish car cleaning", () -> {
                        List<Car> allCarsWithStatus = carService.getAllCarsWithStatus(CarStatus.CLEANING);

                        if (allCarsWithStatus.size() > 0) {
                            FinishCarCleaning finishCarCleaning = new FinishCarCleaning(carService, workerService);
                            finishCarCleaning.init(LOGGED_IN_USER);
                        } else {
                            System.out.println("There is no cars being cleaned at the moment.");
                        }

                        return "";
                    }),
                    new Option("Return car to shop", () -> {
                        List<Car> allCarsWithStatus = carService.getAllCarsWithStatus(CarStatus.FINISH_CLEANING);
                        if (allCarsWithStatus.size() > 0) {
                            ReturnCarToTheShopDialog returnCarToTheShopDialog = new ReturnCarToTheShopDialog(carService);
                            returnCarToTheShopDialog.init(LOGGED_IN_USER);
                        } else {
                            System.out.println("There is no cars to return to the shop.");
                        }
                        return "";
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

        menu.show();
    }
}
