package view;

import exeption.NoneExistingEntityException;
import model.Car;
import model.enums.CarStatus;
import model.user.User;
import service.CarService;
import service.WorkerService;

import java.util.List;
import java.util.Scanner;

public class FinishCarCleaning {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;
    private final WorkerService workerService;

    public FinishCarCleaning(CarService carService, WorkerService workerService) {
        this.carService = carService;
        this.workerService = workerService;
    }

    public void init(User LOGGED_IN_USER) throws NoneExistingEntityException {
        carService.loadData();
        workerService.loadData();

        List<Car> cleaningCars = carService.getAllCarsWithStatus(CarStatus.CLEANING);


        boolean continueCleaning = true;
        while (continueCleaning) {
            if (cleaningCars.size() > 0) {
                int count = 0;
                for (Car car : cleaningCars) {
                    count++;
                    System.out.println(count + ". \t" + car);
                }
                System.out.println();
                System.out.println("Choose finished car cleaning from the list above.");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(cleaningCars, choice, input);
                Car car = cleaningCars.get(choice - 1);

                workerService.finishCarCleaning(car);

                cleaningCars.remove(car);

                System.out.println("Car with ID= '" + car.getId() + "' is cleaned.");

                continueCleaning = confirmEditing(true, cleaningCars);


            }else {
                System.out.println("There is no cars being cleaned at the moment.");
                continueCleaning = false;
            }

        }
    }

    private boolean confirmEditing(boolean continueCleaning, List<Car> cleaningCars) {
        System.out.println();
        System.out.println("For continue finishing car cleaning press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue finishing car cleaning.");
                System.out.println("Remaining cars for finishing cleaning: " + cleaningCars.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled finishing cleaning.");
                continueCleaning = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'C' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueCleaning;
    }

    public int checkValidInput(List<Car> cleaningCars, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > cleaningCars.size()) {
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
