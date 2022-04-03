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
import view.BookingDialog;
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

        Menu menu = new Menu("Users Menu", List.of(
                new Option("See all cars", () -> {
                    System.out.println("Loading cars ...");
                    Collection<Car> allCars = carService.getAllCars();
                    int cont = 0;
                    for (Car car : allCars) {
                        cont++;
                        System.out.println(cont + ". " + car);
                    }
                    return "All available car are successfully shown.\n";
                }),
                new Option("Book Car", () -> {
                    BookingDialog BookingDialog = new BookingDialog(userService, carService, orderService, userRepository);
                    BookingDialog.input(LOGGED_IN_USER);

                    return "";
                }),
                new Option("Orders", () -> {
                    // todo see all orders
                    OrderController orderController = new OrderController(userService, carService, orderService, userRepository);
                    orderController.init(LOGGED_IN_USER);
                    // todo edit order
                    // todo delete order

                    return "orders\n";
                }),
                new Option("Comments", () -> {
                    // todo see all comments
                    // todo edit comments
                    // todo delete comments

                    return "Comments\n";
                }),
                new Option("Statistic", () -> {
                    // todo made another dialog with numbers of cars

                    return "Statistic\n";
                })
        ));
        menu.show();
    }
}
