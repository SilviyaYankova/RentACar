package view;

import exeption.NoPermissionException;
import exeption.NoneExistingEntityException;
import model.Order;
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

    public void init(User LOGGED_IN_USER) throws NoneExistingEntityException, NoPermissionException {
        orderService.loadData();

        List<Order> pendingOrders = orderService.getAllPendingOrders();
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
                System.out.println("Approving all orders....");
                orderService.approveOrder(pendingOrders, LOGGED_IN_USER);
                System.out.println("Orders approved.");

                choice = confirmEditing(LOGGED_IN_USER, choice);
            }
            if (choice == 2) {
                System.out.println();
                System.out.println("Choose Order number to edit from the list above. (from 1 to " + pendingOrders.size() + ")");
                choice = 0;
                input = scanner.nextLine();
                choice = checkValidInput(pendingOrders, choice, input);
                Order order = pendingOrders.get(choice - 1);

                orderService.approveOrder(order, LOGGED_IN_USER);



                choice = confirmEditing(LOGGED_IN_USER, choice);
            }
        }



//        orderService.getAllOrders().forEach(System.out::println);
    }


    public int checkValidInput(int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 6) {
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

    private int confirmEditing(User LOGGED_IN_USER, int choice) throws NoneExistingEntityException, NoPermissionException {
        System.out.println();
        System.out.println("Save approved order or continue editing?");
        System.out.println("For saving approved order press 'YES' for continue editing press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("You finished editing your profile.");
                userService.editUser(LOGGED_IN_USER);
            } else if (input.equals("C")) {
                System.out.println("You choose to continue editing your profile.");
                System.out.println("Choose fields to edit: ");
                System.out.println("1. First name");
                System.out.println("2. Last name");
                System.out.println("3. Phone Number");
                System.out.println("4. Password");
                input = scanner.nextLine();
                choice = 0;
                choice = checkValidInput(choice, input);
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled editing your profile.");
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'C' or 'E'");
                input = scanner.nextLine();
            }
        }
        if (input.equals("YES")) {
            choice = 0;
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
