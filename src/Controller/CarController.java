package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.user.User;
import service.CarService;
import service.UserService;
import view.AddCarDialog;
import view.DeleteCarDialog;
import view.Menu;
import view.Option;

import java.util.Collection;
import java.util.List;

public class CarController {
    private final UserService userService;
    private final CarService carService;

    public CarController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    public void init(User LOGGED_IN_USER) throws InvalidEntityDataException, NoneAvailableEntityException, NoPermissionException, NoneExistingEntityException {
        userService.loadData();
        carService.loadData();

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
                new Option("Add car", () -> {
                    AddCarDialog addCarDialog = new AddCarDialog(carService);
                    addCarDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Edit car", () -> {
                    System.out.println("Working on it...");
                    System.out.println();
                    return "";
                }),
                new Option("Delete car", () -> {
                    DeleteCarDialog deleteCarDialog = new DeleteCarDialog();
                    deleteCarDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));
        menu.show();
    }
}
