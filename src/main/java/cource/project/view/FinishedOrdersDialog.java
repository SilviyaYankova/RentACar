package cource.project.view;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Order;
import cource.project.model.enums.OrderStatus;
import cource.project.model.user.User;
import cource.project.service.CarService;
import cource.project.service.OrderService;
import cource.project.service.UserService;

import java.util.List;
import java.util.Scanner;

public class FinishedOrdersDialog {
    Scanner scanner = new Scanner(System.in);
    private final OrderService orderService;
    private final CarService carService;
    private final UserService userService;

    public FinishedOrdersDialog(OrderService orderService, CarService carService, UserService userService) {
        this.orderService = orderService;
        this.carService = carService;
        this.userService = userService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        orderService.finishOrders();
        List<Order> finishedOrders = orderService.getAllOrdersWithStatus(OrderStatus.FINISH);

        System.out.println("Finished orders:");
        int count = 0;
        for (Order order : finishedOrders) {
            count++;
            System.out.println("\t" + count + ". " + order);
        }

//        System.out.println();
//        System.out.println("Send all cars for cleaning? Type 'YES' or 'NO'");
//        String input = scanner.nextLine();
//
//        boolean incorrectInput = true;
//
//        while (incorrectInput){
//            if (input.equals("YES")) {
//                System.out.println("Sending all cars for cleaning...");
//                for (Order order : finishedOrders) {
//                    carService.returnCar(order);
//                }
//                System.out.println("All cars are send for cleaning.");
//                incorrectInput = false;
//            } else if (input.equals("NO")) {
//                System.out.println("No cars are send for cleaning.");
//                incorrectInput = false;
//            } else {
//                System.out.println("Please make a valid choice.");
//                input = scanner.nextLine();
//            }
//        }
    }
}
