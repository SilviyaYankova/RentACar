package view;

import exeption.NoneExistingEntityException;
import model.Car;
import model.enums.CarStatus;
import model.user.User;
import service.CarService;

import java.util.List;
import java.util.Scanner;

public class ReturnCarToTheShopDialog {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;

    public ReturnCarToTheShopDialog(CarService carService) {
        this.carService = carService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        List<Car> carsForReturningToTheShop = carService.getAllCarsWithStatus(CarStatus.FINISH_CLEANING);
        System.out.println("Cars waiting to be return to the shop:");

        boolean continueCleaning = true;
        while (continueCleaning) {
            if (carsForReturningToTheShop.size() > 0) {
                int count = 0;
                for (Car car : carsForReturningToTheShop) {
                    count++;
                    System.out.println(count + ". \t" + car);
                }

                System.out.println("Choose car to return to the shop from the list above.");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(carsForReturningToTheShop, choice, input);
                Car car = carsForReturningToTheShop.get(choice - 1);

                carService.returnCarToShop(car);

                carsForReturningToTheShop.remove(car);

                System.out.println("Car with ID= '" + car.getId() + "' is cleaned and available for booking.");

                continueCleaning = confirmEditing(true, carsForReturningToTheShop);
            } else {
                System.out.println("All cleaned cars are returned to the shop.");
                continueCleaning = false;
            }

        }





    }
    private boolean confirmEditing(boolean continueCleaning, List<Car> carsForReturningToTheShop) {
        System.out.println();
        System.out.println("For continue returning cleaned cars to shop press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue returning cleaned cars to shop.");
                System.out.println("Remaining cleaned cars for returning to the shop: " + carsForReturningToTheShop.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled returning cleaned cars to shop.");
                continueCleaning = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'C' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueCleaning;
    }

    public int checkValidInput(List<Car> carsForReturningToTheShop, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > carsForReturningToTheShop.size()) {
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