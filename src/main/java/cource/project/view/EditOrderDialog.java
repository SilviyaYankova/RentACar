package cource.project.view;

import cource.project.dao.UserRepository;
import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.enums.Location;
import cource.project.model.enums.OrderStatus;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class EditOrderDialog {
    private static final int DAYS_BEFORE_CHANGE_ORDER = 2;
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final WorkerService workerService;
    private final CommentService commentService;

    public EditOrderDialog(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository, WorkerService workerService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.workerService = workerService;
        this.commentService = commentService;
    }


    public void input(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {


        if (LOGGED_IN_USER.getRole().equals(Role.USER)) {
            List<Order> orders = userService.getAllUserOrders(LOGGED_IN_USER);
            editOrder(LOGGED_IN_USER, orders);
        }

        if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR) || LOGGED_IN_USER.getRole().equals(Role.SELLER)) {
            Collection<Order> allOrders = orderService.getAllOrders();
            List<Order> orders = new ArrayList<>(allOrders);
            editOrder(LOGGED_IN_USER, orders);
        }


    }

    private void editOrder(User LOGGED_IN_USER, Collection<Order> userOrders) throws NoneExistingEntityException, NoneAvailableEntityException, InvalidEntityDataException {

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
            System.out.println("You can edit orders only two days before the pick up date.");
            System.out.println("Orders you can edit:");
            int count = 0;
            for (Order order : orders) {
                count++;
                System.out.println("\t" + count + ". " + order);
            }
            System.out.println();
            System.out.println("Choose Order number to edit from the list above. (from 1 to " + orders.size() + ")");
            int choice = 0;
            String input = scanner.nextLine();
            choice = checkValidInput(orders, choice, input);

            Order order = orders.get(choice - 1);
            Car oldCar = carService.getCarById(order.getCar().getId());
            LocalDateTime oldPickUpDate = order.getPickUpDate();
            LocalDateTime oldDropOffDate = order.getDropOffDate();

            List<LocalDateTime> oldCarPickUpDates = oldCar.getPickUpDates();
            List<LocalDateTime> oldCarDropOffDates = oldCar.getDropOffDates();
            userOrders.remove(order);

            System.out.println("Order you choose to edit:");
            System.out.println();
            System.out.println(order);
            System.out.println("Choose fields to edit: ");
            System.out.println("1. Locations");
            System.out.println("2. Dates");
            System.out.println("3. Driver");
            System.out.println("4. Car");

            input = scanner.nextLine();
            choice = 0;
            choice = checkValidInput(choice, input);
            boolean incorrectInput = true;
            AddOrderDialog addOrderDialog = new AddOrderDialog(userService, carService, orderService, userRepository, workerService, commentService);

            Car car = null;
            while (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                if (choice == 1) {
                    order.setPickUpLocation(null);
                    while (order.getPickUpLocation() == null) {
                        Location[] locations = addOrderDialog.getLocations();
                        addOrderDialog.choosePickUpLocation(order, choice, locations);
                    }

                    order.setDropOfLocation(null);
                    while (order.getDropOffLocation() == null) {
                        Location[] locations = addOrderDialog.getLocations();
                        addOrderDialog.chooseDropOffLocation(order, choice, locations);
                    }
                    choice = confirm(order, choice);
                }

                if (choice == 2) {
                    order.setPickUpDate(null);
                    while (order.getPickUpDate() == null) {
                        addOrderDialog.choosePickUpdate(order);
                    }

                    order.setDropOffDate(null);
                    while (order.getDropOffDate() == null) {
                        addOrderDialog.chooseDropOffDate(order);
                    }
                    List<LocalDateTime> pickUpDates = oldCar.getPickUpDates();
                    if (pickUpDates.contains(order.getPickUpDate())) {
                        System.out.println("Sorry your current car is not available for this dates. Change dates or pick another car.");
                        order.setPickUpDate(null);
                        order.setDropOffDate(null);
                        choice = continueOrNot(order, choice);
                    } else {
                        oldCar.getPickUpDates().remove(oldPickUpDate);
                        oldCar.getDropOffDates().remove(oldDropOffDate);
                        oldCar.getPickUpDates().add(order.getPickUpDate());
                        oldCar.getDropOffDates().add(order.getDropOffDate());
                        choice = confirm(order, choice);
                    }
                }

                if (choice == 3) {
                    addOrderDialog.chooseToHireADriverOrNot(LOGGED_IN_USER, order, incorrectInput);
                    if (order.isHireDriver() == false) {
                        order.setDriver(null);
                    }
                    choice = confirm(order, choice);
                }

                if (choice == 4) {
                    oldCar.getPickUpDates().remove(order.getPickUpDate());
                    oldCar.getDropOffDates().remove(order.getDropOffDate());
                    oldCar.getOrders().remove(order);

                    List<Car> availableCarsForDates = carService.getAvailableCars(order);

                    if (availableCarsForDates.size() == 0) {

                        System.out.println("Sorry there is no available cars for this dates.");
                        System.out.println("Choose 'YES' to change dates or 'NO' to cancel order.");
                        input = scanner.nextLine();

                        if (input.equals("YES")) {
                            incorrectInput = false;
                            while (availableCarsForDates.size() == 0) {
                                order.setPickUpDate(null);
                                order.setDropOffDate(null);
                                System.out.println("Pick Up new Dates:");
                                addOrderDialog.choosePickUpdate(order);
                                addOrderDialog.chooseDropOffDate(order);
                                availableCarsForDates = carService.getAvailableCars(order);
                            }

                        } else if (input.equals("NO")) {
                            System.out.println("You canceled your order.");

                        } else {
                            System.out.println("Please make a valid choice.");
                            input = scanner.nextLine();
                        }
                    }

                    count = 0;
                    if (availableCarsForDates.size() > 0) {
                        System.out.println("Available cars for chosen dates:");
                        for (Car c : availableCarsForDates) {
                            count++;
                            System.out.println(count + ".\t" + c);
                        }

                        System.out.println();
                        System.out.println("Choose Car number from the list above. (from 1 to " + availableCarsForDates.size() + ")");
                        input = scanner.nextLine();
                        choice = 0;
                        choice = addOrderDialog.validInputNumber(choice, input, availableCarsForDates);

                        car = availableCarsForDates.get(choice - 1);
                        car.getPickUpDates().add(order.getPickUpDate());
                        car.getDropOffDates().add(order.getDropOffDate());
                        order.setCar(car);
                    }

                    choice = confirm(order, choice);
                }
            }

            if (!input.equals("E")) {
                double driverPricePerDays = calculatePrice(order, order.getCar());

                if (order.getCar().equals(oldCar)) {
                    carService.editCar(oldCar);
                } else {
                    carService.editCar(oldCar);
                    carService.editCar(car);
                }

                userService.editUser(LOGGED_IN_USER);

                printOrder(order, order.getCar(), driverPricePerDays);

                order.setModifiedOn(LocalDateTime.now());
                confirmOrCancelOrder(order);
            }

        } else {
            System.out.println("Sorry there is no orders you can edit.");
        }
    }

    private int continueOrNot(Order order, int choice) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("Continue editing order or cancel?");
        System.out.println("For continue editing press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue editing your order.");
                System.out.println("Choose fields to edit: ");
                System.out.println("1. Locations");
                System.out.println("2. Dates");
                System.out.println("3. Driver");
                System.out.println("4. Car");
                input = scanner.nextLine();
                choice = 0;
                choice = checkValidInput(choice, input);
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled editing your order.");
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

    private int confirm(Order order, int choice) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("Save order or continue editing?");
        System.out.println("For saving order press 'YES' for continue editing press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("You finished your order.");
                order.setModifiedOn(LocalDateTime.now());
                order.getUser().getOrders().remove(order);
                orderService.editOrder(order);
                order.getUser().getOrders().add(order);
                userService.editUser(order.getUser());
            } else if (input.equals("C")) {
                System.out.println("You choose to continue editing your order.");
                System.out.println("Choose fields to edit: ");
                System.out.println("1. Locations");
                System.out.println("2. Dates");
                System.out.println("3. Driver");
                System.out.println("4. Car");
                input = scanner.nextLine();
                choice = 0;
                choice = checkValidInput(choice, input);
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled editing your order.");
                break;
            } else {
                System.out.println("Error: Please make a choice between 'S' or 'C' or 'E'");
                input = scanner.nextLine();
            }
        }
        if (input.equals("YES")) {
            choice = 0;
        }
        return choice;
    }

    private void confirmOrCancelOrder(Order order) throws NoneExistingEntityException {
        boolean incorrectInput = true;
        String input = scanner.nextLine();
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("You finished your order.");
                order.setModifiedOn(LocalDateTime.now());
                orderService.editOrder(order);
            } else if (input.equals("NO")) {
                System.out.println("You declined your order.");
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'NO");
                input = scanner.nextLine();
            }
        }
    }

    private void printOrder(Order order, Car car, double driverPricePerDays) {
        System.out.println("Please confirm your order: (Type 'YES' or 'NO')");
        System.out.printf("brand: %s%nmodel: %s%npick up location: %s%ndrop off location: %s%npick up date: %s%ndrop off date %s%ndays: %d%ncar price per days: %.2f%ncar deposit: %.2f%n",
                car.getBrand(),
                car.getModel(),
                order.getPickUpLocation(),
                order.getDropOffLocation(),
                order.getPickUpDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                order.getDropOffDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                order.getDays(),
                order.getCarPricePerDays(),
                car.getDeposit());


        if (order.getDriver() != null) {
            System.out.printf("driver price per day: %.2f%ndriver total price: %.2f%n",
                    order.getDriver().getPricePerDay(), driverPricePerDays);
        }

        System.out.printf("total price: %.2f", order.getFinalPrice());
        System.out.println();
    }

    private double calculatePrice(Order order, Car car) {
        long days = DAYS.between(order.getPickUpDate(), order.getDropOffDate());
        order.setDays(days);

        double pricePerDays = orderService.calculateCarPrice(days, car.getPricePerDay());
        order.setCarPricePerDays(pricePerDays);

        double driverPricePerDays = 0;
        if (order.getDriver() != null) {
            driverPricePerDays = days * order.getDriver().getPricePerDay();
        }
        double perDay = 0;
        perDay += orderService.calculateCarPrice(days, car.getPricePerDay());
        double totalPrice = perDay + car.getDeposit() + driverPricePerDays;

        order.setFinalPrice(totalPrice);
        return driverPricePerDays;
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

    public int checkValidInput(int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 4) {
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
