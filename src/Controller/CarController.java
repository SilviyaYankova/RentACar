package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.enums.CarStatus;
import model.user.User;
import service.CarService;
import service.UserService;
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

        Menu menu = new Menu("User Menu", List.of(
                new Option("See all cars", () -> {
                    Collection<Car> allCars = carService.getAllCars();
                    int cont = 0;
                    for (Car car : allCars) {
                        cont++;
                        System.out.println(cont + ". " + car);
                    }

                    return "All available car are successfully shown.\n";
                }),
                new Option("Add car", () -> {

                    return "";
                }),
                new Option("Edit car", () -> {

                    return "";
                }),
                new Option("Delete car", () -> {

                    return "";
                })
        ));
        menu.show();
    }
}
