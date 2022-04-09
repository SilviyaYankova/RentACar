package cource.project.Controller;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.enums.CarStatus;
import cource.project.model.enums.OrderStatus;
import cource.project.model.user.User;
import cource.project.service.CarService;
import cource.project.service.OrderService;
import cource.project.service.UserService;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class CarController {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;

    public CarController(UserService userService, CarService carService, OrderService orderService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
    }

    public void init(User LOGGED_IN_USER) throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException {


        Menu menu = new Menu("Car Menu", List.of(
                new Option("See all cars", () -> {
                    Collection<Car> allCars = carService.getAllCars();
                    int cont = 0;
                    for (Car car : allCars) {
                        cont++;
                        System.out.println(cont + ". \t" + car);
                    }

                    return "";
                }),
                new Option("Send cars for cleaning", () -> {

                    List<Car> finishedCars = carService.getAllCarsWithStatus(CarStatus.WAITING);

                    System.out.println("Cars for cleaning:");
                    int count = 0;
                    for (Car car : finishedCars) {
                        count++;
                        System.out.println("\t" + count + ". " + car);
                    }
                    System.out.println();
                    System.out.println("Send all cars for cleaning? Type 'YES' or 'NO'");
                    String input = scanner.nextLine();

                    boolean incorrectInput = true;

                    while (incorrectInput) {
                        if (input.equals("YES")) {
                            System.out.println("Sending all cars for cleaning...");
                            for (Car car : finishedCars) {
                                carService.returnCar(car);
                            }
                            System.out.println("All cars are send for cleaning.");
                            incorrectInput = false;
                        } else if (input.equals("NO")) {
                            System.out.println("No cars are send for cleaning.");
                            incorrectInput = false;
                        } else {
                            System.out.println("Please make a valid choice.");
                            input = scanner.nextLine();
                        }
                    }



                    return "";
                }),


                new Option("Add car", () -> {
                    AddCarDialog addCarDialog = new AddCarDialog(carService);
                    addCarDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Edit car", () -> {
                    EditCarDialog editCarDialog = new EditCarDialog(carService);
                    editCarDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete car", () -> {
                    DeleteCarDialog deleteCarDialog = new DeleteCarDialog(carService);
                    deleteCarDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));
        menu.show();
    }
}
