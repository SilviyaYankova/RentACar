package view;

import exeption.NoneExistingEntityException;
import model.Order;
import model.enums.OrderStatus;
import model.user.User;
import service.OrderService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class ApproveOrdersDialog {
    Scanner scanner = new Scanner(System.in);
    private final OrderService orderService;
    private final UserService userService;

    public ApproveOrdersDialog(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        orderService.loadData();

        List<Order> pendingOrders = orderService.getAllOrdersWithStatus(OrderStatus.PENDING);
        System.out.println("Orders waiting for approving.");
        int count = 0;
        for (Order order : pendingOrders) {
            count++;
            System.out.println("\t" + count + ". " + order);
        }

        System.out.println();
        System.out.println("Choose action.");
        System.out.println("1. Approve all orders");
        System.out.println("2. Approve individual order");

        String input = scanner.nextLine();
        int choice = 0;
        choice = checkValidInput(choice, input);
        boolean incorrectInput = true;

        while (choice == 1 || choice == 2) {
            if (choice == 1) {
                System.out.println("Approving all orders...");
                orderService.approveOrder(pendingOrders, LOGGED_IN_USER);
                System.out.println("Orders approved.");
                choice = 0;
            }
            if (choice == 2) {
                System.out.println();
                System.out.println("Choose Order number to edit from the list above. (from 1 to " + pendingOrders.size() + ")");
                choice = 0;
                input = scanner.nextLine();
                choice = checkValidInput(pendingOrders, choice, input);
                Order order = pendingOrders.get(choice - 1);
                order.getUser().getOrders().remove(order);
                orderService.approveOrder(order, LOGGED_IN_USER);
                order.getUser().getOrders().add(order);
                userService.editUser(order.getUser());
                choice = confirmEditing(LOGGED_IN_USER, choice);
            }
        }
    }


    public int checkValidInput(int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 2) {
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

    private int confirmEditing(User LOGGED_IN_USER, int choice) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("For continue approving orders press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
             if (input.equals("C")) {
                 System.out.println("Choose action.");
                 System.out.println("1. Approve all orders");
                 System.out.println("2. Approve individual order");
                input = scanner.nextLine();
                choice = 0;
                choice = checkValidInput(choice, input);
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled approving orders.");
                 choice = 0;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'C' or 'E'");
                input = scanner.nextLine();
            }
        }

        return choice;
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
