package view;

import dao.UserRepository;
import exeption.NoPermissionException;
import exeption.NoneExistingEntityException;
import model.Order;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class DeleteOrderDialog {

    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public DeleteOrderDialog(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException, NoPermissionException {
        orderService.loadData();
        EditOrderDialog editOrderDialog = new EditOrderDialog(userService, carService, orderService, userRepository);
        Collection<Order> userOrders = LOGGED_IN_USER.getOrders();
        List<Order> orders = new ArrayList<>();
        for (Order order : userOrders) {
            int dayOfMonth = order.getPickUpDate().getDayOfMonth();
            int dayOfMonth1 = LocalDateTime.now().getDayOfMonth();
            if (dayOfMonth + 2 > dayOfMonth1) {
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
            userService.editUser(LOGGED_IN_USER, LOGGED_IN_USER.getRole());
            orderService.deleteOrder(order.getId());

        } else {
            System.out.println("Sorry there is no orders you can delete.");
        }
    }
}
