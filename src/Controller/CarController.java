package Controller;

import exeption.InvalidEntityDataException;
import service.CarService;
import service.UserService;

public class CarController {
    private final UserService userService;
    private final CarService carService;

    public CarController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    public void init() throws InvalidEntityDataException {

        //todo validirai dannitre i tigava save


    }
}
