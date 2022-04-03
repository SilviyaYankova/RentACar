package view;

import Controller.UserController;
import dao.UserRepository;
import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.enums.Location;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class EditOrderDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public EditOrderDialog(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }


    public void input(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        orderService.loadData();
        Collection<Order> userOrders = LOGGED_IN_USER.getOrders();
        List<Order> orders = new ArrayList<>();
        for (Order order : userOrders) {
            int dayOfMonth = order.getPickUpDate().getDayOfMonth();
            int dayOfMonth1 = LocalDateTime.now().getDayOfMonth();
            int diff = Math.abs(dayOfMonth1 - dayOfMonth);
            if (diff >= 2) {
                orders.add(order);
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

            System.out.println("Order you choose to edit:");
            System.out.println();
            System.out.println(order);
            System.out.println("Choose field to edit: ");
            System.out.println("1. Pick up Location");
            System.out.println("2. Drop off Location");
            System.out.println("3. Pick up Date");
            System.out.println("4. Drop off Date");
            System.out.println("5. Driver");
            System.out.println("6. Car");
            System.out.println("7. Exit");

            input = scanner.nextLine();
            choice = 0;
            choice = checkValidInput(choice, input);
            BookingDialog bookingDialog = new BookingDialog(userService, carService, orderService, userRepository);

            if (choice == 1) {
                order.setPickUpLocation(null);
                while (order.getPickUpLocation() == null) {
                    Location[] locations = bookingDialog.getLocations();
                }
            }

            if (choice == 2) {
                order.setDropOfLocation(null);
                while (order.getDropOffLocation() == null) {
                    Location[] locations = bookingDialog.getLocations();
                }
            }

            if (choice == 3) {
                order.setPickUpDate(null);
                while (order.getPickUpDate() == null) {
                    bookingDialog.choosePickUpdate(order);
                }
            }

            if (choice == 4) {
                order.setDropOffDate(null);
                while (order.getDropOffDate() == null) {
                    bookingDialog.chooseDropOffDate(order);
                }
            }

            boolean incorrectInput = true;
            if (choice == 5) {
                bookingDialog.chooseToHireADriverOrNot(LOGGED_IN_USER, order, incorrectInput);
            }

            Car car = null;
            if (choice == 6) {

                List<Car> availableCarsForDates = carService.getAvailableCars(order);

                //  if (availableCarsForDates.size() == 0) or  (input.equals("NO"))?
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
                            bookingDialog.choosePickUpdate(order);
                            bookingDialog.chooseDropOffDate(order);
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
                    System.out.println("Choose Car number to book from the list above. (from 1 to " + availableCarsForDates.size() + ")");
                    input = scanner.nextLine();
                    choice = 0;
                    choice = bookingDialog.validInputNumber(choice, input, availableCarsForDates);

                    car = availableCarsForDates.get(choice - 1);
                    order.setCar(car);
                }
            }


                double driverPricePerDays = calculatePrice(order, order.getCar());



                printORder(order, order.getCar(), driverPricePerDays);

                confirmOrCancelOrder(order);


        } else {
            System.out.println("Sorry, you are not able to edit your orders because of the pick up date.");
        }

    }

    private void confirmOrCancelOrder(Order order) throws NoneExistingEntityException {
        String input;
        boolean incorrectInput;
        incorrectInput = true;
        input = scanner.nextLine();
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

    private void printORder(Order order, Car car, double driverPricePerDays) {
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
}
