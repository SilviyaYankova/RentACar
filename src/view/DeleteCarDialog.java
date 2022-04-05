package view;

import model.user.User;
import service.CarService;

import java.util.Scanner;

public class DeleteCarDialog {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;

    public DeleteCarDialog(CarService carService) {
        this.carService = carService;
    }

    public void input(User LOGGED_IN_USER) {
            carService.loadData();


    }
}
