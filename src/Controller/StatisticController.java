package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Comment;
import model.Order;
import model.user.User;
import service.*;
import view.Menu;
import view.Option;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class StatisticController {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final WorkerService workerService;
    private final CommentService commentService;

    public StatisticController(UserService userService, CarService carService, OrderService orderService, WorkerService workerService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.workerService = workerService;
        this.commentService = commentService;
    }


    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        userService.loadData();
        carService.loadData();
        orderService.loadData();
        workerService.loadData();
        commentService.loadData();

        Menu menu = new Menu("Statistics Menu", List.of(
                new Option("Users", () -> {
                    userService.loadData();
                    Collection<User> all = userService.getAllUsers();
                    List<User> allUsers = new ArrayList<>(all);
                    if (allUsers.size() > 0) {
                        int count = 0;
                        for (User user : allUsers) {
                            count++;
                            System.out.println(count + ".\t " + user);
                        }
                    } else {
                        System.out.println("There is no users in the system.");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Comments", () -> {
                    Collection<Comment> allComments = commentService.getAllComments();
                    if (allComments.size() > 0) {
                        System.out.println("All comments:");
                        int count = 0;
                        for (Comment comment : allComments) {
                            count++;
                            System.out.println(count + ". \t" + comment);
                        }
                    } else {
                        System.out.println("You have no comments.");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Cars", () -> {
                    Collection<Car> allCars = carService.getAllCars();
                    int cont = 0;
                    for (Car car : allCars) {
                        cont++;
                        System.out.println(cont + ". \t" + car);
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Orders", () -> {
                    Collection<Order> orders = orderService.getAllOrders();
                    if (orders.size() > 0) {
                        orders.forEach(System.out::println);
                    } else {
                        System.out.println("Sorry there is no orders");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Personal Sells Statistic", () -> {
                    Collection<Order> allOrders = orderService.getAllOrders();
                    int count = 0;
                    for (Order order : allOrders) {
                        if (order.getSeller().equals(LOGGED_IN_USER)) {
                            System.out.println(count + ".\t" + order);
                        }
                    }

                    return "";
                }),
                new Option("Total profit", () -> {
                    System.out.println("Total profit includes only the profit from finished orders.");
                    String profit = userService.getProfit();
                    System.out.println(profit);
                    return "";
                }),
                new Option("Total profit for period", () -> {
                    System.out.println("Total profit for period includes only the profit from finished orders.");

                    System.out.println("Enter start period: (ex. 31.03.2022 10:00)");
                    String input = scanner.nextLine();
                    LocalDateTime from = from(input);
                    System.out.println("Enter end period: (ex. 31.03.2022 10:00)");
                    input = scanner.nextLine();
                    LocalDateTime to = from(input);

                    String profitForPeriod = userService.getProfitForPeriod(from, to);
                    System.out.println();
                    System.out.println(profitForPeriod);
                    return "";
                })
        ));

        menu.show();
    }

    public LocalDateTime from(String input) {
        LocalDateTime localDateTime = null;
        while (localDateTime == null) {

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                if (dateTime.isAfter(LocalDateTime.now())) {
                    System.out.println("Date can not be from the future. Please try again.");
                    input = scanner.nextLine();
                }

                localDateTime = LocalDateTime.parse(input, formatter);

            } catch (Exception e) {
                System.out.println("Error: Incorrect date. Please try again.");
                input = scanner.nextLine();

            }
        }
        return localDateTime;
    }
}
