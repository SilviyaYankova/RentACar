package cource.project.view;

import cource.project.dao.UserRepository;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.enums.OrderStatus;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.*;

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
    private final CommentService commentService;

    public DeleteOrderDialog(UserService userService, CarService carService, OrderService orderService,
                             UserRepository userRepository, WorkerService workerService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.workerService = workerService;
        this.commentService = commentService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        if (LOGGED_IN_USER.getRole().equals(Role.USER)) {
            List<Order> orders = userService.getAllUserOrders(LOGGED_IN_USER);
            deleteOrder(LOGGED_IN_USER, orders);
        }
        if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR) || LOGGED_IN_USER.getRole().equals(Role.SELLER)) {
            Collection<Order> allOrders = orderService.getAllOrders();
            deleteOrder(LOGGED_IN_USER, allOrders);
        }
    }

    private void deleteOrder(User LOGGED_IN_USER, Collection<Order> userOrders) throws NoneExistingEntityException {
        List<Order> orders = new ArrayList<>();
        for (Order order : userOrders) {
            if (!order.getOrderStatus().equals(OrderStatus.FINISH)) {
                int year = order.getPickUpDate().getYear();
                int month = order.getPickUpDate().getMonth().getValue();
                int day = order.getPickUpDate().getDayOfMonth() - DAYS_BEFORE_CHANGE_ORDER;

                int nowYear = LocalDateTime.now().getYear();
                int nowMonth = LocalDateTime.now().getMonth().getValue();
                int nowDay = LocalDateTime.now().getDayOfMonth();

                int diff = day - nowDay;
                if (year == nowYear && month == nowMonth && diff > 2) {
                    orders.add(order);
                }
                if (month > nowMonth) {
                    orders.add(order);
                }
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
            choice = checkValidInput(orders, choice, input);

            Order order = orders.get(choice - 1);
            order.setOrderStatus(OrderStatus.DELETED);

            User user = order.getUser();
            user.getOrders().remove(order);
            orderService.editOrder(order);
            user.getOrders().add(order);

            Car car = order.getCar();
            car.getPickUpDates().remove(order.getPickUpDate());
            car.getDropOffDates().remove(order.getDropOffDate());
            carService.editCar(car);

            userService.editUser(user);
            orderService.deleteOrder(order.getId());
        } else {
            System.out.println("Sorry there is no orders you can delete.");
        }
    }

    public int checkValidInput(List<Order> orders, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > orders.size()) {
                    System.out.println("Error: Please choose a valid number.");
                    choice = 0;
                    input = scanner.nextLine();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: Numbers only.");
                input = scanner.nextLine();
            }
        }
        return choice;
    }
}
