package view;

import dao.UserRepository;
import exeption.NoPermissionException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;
import service.WorkerService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class DeleteOrderDialog {
    private static final int DAYS_BEFORE_CHANGE_ORDER = 2;
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final WorkerService workerService;

    public DeleteOrderDialog(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository, WorkerService workerService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.workerService = workerService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException, NoPermissionException {
        orderService.loadData();
        EditOrderDialog editOrderDialog = new EditOrderDialog(userService, carService, orderService, userRepository, workerService);
        Collection<Order> userOrders = LOGGED_IN_USER.getOrders();
        List<Order> orders = new ArrayList<>();
        for (Order order : userOrders) {
            int dayOfMonth = order.getPickUpDate().getDayOfMonth() - DAYS_BEFORE_CHANGE_ORDER;
            int now = LocalDateTime.now().getDayOfMonth();
            if (dayOfMonth > now) {
                orders.add(order);
            }
        }

        if (orders.size() > 0) {
            System.out.println("You can delete orders only two days before the pick up date.");
            System.out.println("Orders you can delete:");
            int count = 0;
            for (Order order : orders) {
                count++;
                System.out.println("\t" + count + ". " + order);
            }
            System.out.println();
            System.out.println("Choose Order number to delete from the list above. (from 1 to " + orders.size() + ")");
            int choice = 0;
            String input = scanner.nextLine();
            choice = editOrderDialog.checkValidInput(orders, choice, input);

            Order order = orders.get(choice - 1);
            LOGGED_IN_USER.getOrders().remove(order);
            Car car = order.getCar();
            car.getPickUpDates().remove(order.getPickUpDate());
            car.getDropOffDates().remove(order.getDropOffDate());
            carService.editCar(car);
            userService.editUser(LOGGED_IN_USER);
            orderService.deleteOrder(order.getId());

        } else {
            System.out.println("Sorry there is no orders you can delete.");
        }
    }
}
