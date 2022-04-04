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
import model.user.Driver;
import model.user.User;
import service.CarService;
import service.OrderService;
import service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class BookingDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public BookingDialog(UserService userService, CarService carService, OrderService orderService, UserRepository userRepository) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    public void input(User LOGGED_IN_USER) throws InvalidEntityDataException, NoneExistingEntityException, NoPermissionException, NoneAvailableEntityException {
        orderService.loadData();
        Order order = new Order();
        order.setUser(LOGGED_IN_USER);

        int count = 0;
        int choice = 0;
        boolean incorrectInput = true;

        Location[] locations = getLocations();

        choice = choosePickUpLocation(order, choice, locations);

        choice = chooseDropOffLocation(order, choice, locations);

        choosePickUpdate(order);

        chooseDropOffDate(order);

        chooseToHireADriverOrNot(LOGGED_IN_USER, order, incorrectInput);

        chooseCar(LOGGED_IN_USER, order);

    }

    public void chooseCar(User LOGGED_IN_USER, Order order) throws NoneExistingEntityException, InvalidEntityDataException, NoPermissionException {
        boolean incorrectInput;
        int choice;
        int count;
        String input = "";
        count = 0;
        List<Car> availableCarsForDates = carService.getAvailableCars(order);

        Collection<Order> allOrders = orderService.getAllOrders();
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
                    choosePickUpdate(order);
                    chooseDropOffDate(order);
                    availableCarsForDates = carService.getAvailableCars(order);
                }

            } else if (input.equals("NO")) {
                System.out.println("You canceled your order.");

            } else {
                System.out.println("Please make a valid choice.");
                input = scanner.nextLine();
            }

        }

        Car car = null;
        if (availableCarsForDates.size() > 0) {
            System.out.println("Available cars for chosen dates:");
            for (Car c : availableCarsForDates) {
                count++;
                System.out.println(count + ".\t"+ c);
            }

            System.out.println();
            System.out.println("Choose Car number to book from the list above. (from 1 to " + availableCarsForDates.size() + ")");
            input = scanner.nextLine();
            choice = 0;
            choice = validInputNumber(choice, input, availableCarsForDates);

            car = availableCarsForDates.get(choice - 1);

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

            // todo confirm orders
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

            incorrectInput = true;
            input = scanner.nextLine();
            while (incorrectInput) {
                if (input.equals("YES")) {
                    incorrectInput = false;
                    System.out.println("You finished your order.");
                    try {
                        orderService.addOrder(order, car);
                    } catch (NoneAvailableEntityException e) {
                        System.out.println("Sorry, Car is not available. Please change your dates.");
                    }
                } else if (input.equals("NO")) {
                    System.out.println("You declined your order.");
                    break;
                } else {
                    System.out.println("Error: Please make a choice between 'YES' or 'NO");
                    input = scanner.nextLine();
                }
            }
        }
    }

    public int validInputNumber(int choice, String input, List<Car> availableCarsForDates) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > availableCarsForDates.size()) {
                    System.out.println("Error: Please choose a valid car number.");
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

    public int choosePickUpLocation(Order order, int choice, Location[] locations) {
        System.out.println("Choose Pick Up Location number from the list above. (from 1 to " + locations.length + ")");
        String input = scanner.nextLine();
        choice = 0;
        while (order.getPickUpLocation() == null) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > locations.length) {
                    System.out.println("Error: Please choose a valid location number.");
                    input = scanner.nextLine();
                } else {
                    Location location = locations[choice - 1];
                    order.setPickUpLocation(location);
                    System.out.println("You choose Pick Up Location: " + location.name());
                }

            } catch (NumberFormatException ex) {
                System.out.println("Error: Numbers only.");
                input = scanner.nextLine();
            }
        }

        return choice;
    }

    public int chooseDropOffLocation(Order order, int choice, Location[] locations) {
        System.out.println("Choose Drop Off Location number from the list above. (from 1 to " + locations.length + ")");
        String input = scanner.nextLine();
        while (order.getDropOffLocation() == null) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > locations.length) {
                    System.out.println("Error: Please choose a valid location number.");
                    choice = 0;
                    input = scanner.nextLine();
                } else {
                    Location location = locations[choice - 1];
                    order.setDropOfLocation(location);
                    System.out.println("You choose Drop Off Location: " + location.name());
                }

            } catch (NumberFormatException ex) {
                System.out.println("Error: Numbers only.");
                input = scanner.nextLine();
            }
        }
        return choice;
    }

    public void chooseToHireADriverOrNot(User LOGGED_IN_USER, Order order, boolean incorrectInput) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        String input;
        System.out.println("Do you want to hire a driver? (Type 'YES' for 'yes' or 'NO' for 'no')");
        input = scanner.nextLine();
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("You choose to hire a driver.");
                order.setHireDriver(true);
                Driver availableDriver = null;
                try {
                    availableDriver = userRepository.getAvailableDriver(order.getPickUpDate(), order.getDropOffDate());
                    order.setDriver(availableDriver);
                } catch (NoneAvailableEntityException e) {
                    System.out.println("Sorry there is no available Driver. You can change or cancel your order.");
                    System.out.println("Choose 'C' to continue without a Driver or 'NO' to decline order.");
                    input = scanner.nextLine();

                    if (input.equals("C")) {
                        input = "NO";
                        break;
                    } else if (input.equals("NO")) {
                        UserController userController = new UserController(userService, carService, orderService, userRepository);
                        userController.init(LOGGED_IN_USER);
                    } else {
                        System.out.println("Please make a valid choice.");
                        input = scanner.nextLine();
                    }
                }
            } else if (input.equals("NO")) {
                System.out.println("You choose not to hire a driver.");
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'NO");
                input = scanner.nextLine();
            }
        }
    }

    public void chooseDropOffDate(Order order) {
        String input;
        System.out.println("Choose Drop Off Date and Time.(ex. 31.03.2022 10:00)");
        input = scanner.nextLine();
        while (order.getDropOffDate() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                if (dateTime.isBefore(LocalDateTime.now()) || dateTime.equals(order.getPickUpDate())) {
                    System.out.println("Drop Off Date can not be from the past. Please try again.");
                    input = scanner.nextLine();
                }
                order.setDropOffDate(dateTime);
            } catch (Exception e) {
                System.out.println("Error: Incorrect Drop Off Date input. Please try again.");
                input = scanner.nextLine();

            }
        }
    }

    public void choosePickUpdate(Order order) {
        String input;
        System.out.println("Choose Pick Up Date and Time.(ex. 31.03.2022 10:00)");
        input = scanner.nextLine();
        while (order.getPickUpDate() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                if (dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Pick Up Date can not be from the past. Please try again.");
                    input = scanner.nextLine();
                }
                order.setPickUpDate(dateTime);

            } catch (Exception e) {
                System.out.println("Error: Incorrect Pick Up Date input. Please try again.");
                input = scanner.nextLine();

            }
        }
    }

    public Location[] getLocations() {
        int count;
        Location[] locations = Location.values();
        System.out.println();
        System.out.println("Locations:");
        count = 0;
        for (Location location : locations) {
            count++;
            System.out.println("\t" + count + ". " + location.name());
        }
        return locations;
    }
}
