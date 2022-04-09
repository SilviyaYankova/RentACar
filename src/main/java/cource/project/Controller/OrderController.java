package cource.project.Controller;

import cource.project.dao.UserRepository;
import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Order;
import cource.project.model.enums.OrderStatus;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.*;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

import java.util.Collection;
import java.util.List;

public class OrderController {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final WorkerService workerService;
    private final CommentService commentService;

    public OrderController(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository, WorkerService workerService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.workerService = workerService;
        this.commentService = commentService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {
        // check for finished orders
        orderService.finishOrders();

        Menu menu = new Menu();

        if (LOGGED_IN_USER.getRole().equals(Role.USER)) {
            menu = new Menu("Orders Menu", List.of(
                    new Option("Order History", () -> {
                        List<Order> orders = userService.getAllUserOrders(LOGGED_IN_USER);
                        if (orders.size() == 0) {
                            System.out.println("You have no past orders.");
                        } else {
                            orders.forEach(System.out::println);
                        }
                        System.out.println();
                        return "";
                    }),
                    new Option("Edit order", () -> {
                        EditOrderDialog editOrderDialog = new EditOrderDialog(userService, carService, orderService, userRepository, workerService, commentService);
                        editOrderDialog.input(LOGGED_IN_USER);

                        System.out.println();
                        return "";
                    }),
                    new Option("Delete order", () -> {

                        DeleteOrderDialog deleteOrderDialog = new DeleteOrderDialog(userService, carService, orderService, userRepository, workerService, commentService);
                        deleteOrderDialog.input(LOGGED_IN_USER);

                        System.out.println();
                        return "";
                    })
            ));
        }

        if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR) || LOGGED_IN_USER.getRole().equals(Role.SELLER)) {

            menu = new Menu("Orders", List.of(
                    new Option("All Orders", () -> {
                        Collection<Order> orders = orderService.getAllOrders();
                        if (orders.size() > 0) {
                            orders.forEach(System.out::println);
                        } else {
                            System.out.println("Sorry there is no orders");
                        }
                        System.out.println();
                        return "";
                    }),
                    new Option("Approve Pending orders", () -> {
                        Collection<Order> orders = orderService.getAllOrdersWithStatus(OrderStatus.PENDING);
                        if (orders.size() > 0) {
                            ApproveOrdersDialog approveOrdersDialog = new ApproveOrdersDialog(orderService, userService);
                            approveOrdersDialog.input(LOGGED_IN_USER);
                        } else {
                            System.out.println("Sorry there is no pending orders.");
                        }
                        System.out.println();
                        return "";
                    }),
                    new Option("Finished orders", () -> {
                        orderService.finishOrders();
                        Collection<Order> orders = orderService.getAllOrdersWithStatus(OrderStatus.FINISH);
                        if (orders.size() > 0) {
                            FinishedOrdersDialog finishedOrdersDialog = new FinishedOrdersDialog(orderService, carService, userService);
                            finishedOrdersDialog.input(LOGGED_IN_USER);
                        } else {
                            System.out.println("Sorry there is no finished orders.");
                        }
                        System.out.println();
                        return "";
                    }),
                    new Option("Edit order", () -> {
                        EditOrderDialog editOrderDialog = new EditOrderDialog(userService, carService, orderService, userRepository, workerService, commentService);
                        editOrderDialog.input(LOGGED_IN_USER);

                        System.out.println();
                        return "";
                    }),
                    new Option("Delete order", () -> {

                        DeleteOrderDialog deleteOrderDialog = new DeleteOrderDialog(userService, carService, orderService, userRepository, workerService, commentService);
                        deleteOrderDialog.input(LOGGED_IN_USER);

                        System.out.println();
                        return "";
                    })
            ));
        }

        menu.show();
    }
}
