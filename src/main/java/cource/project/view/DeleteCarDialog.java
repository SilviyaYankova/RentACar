package cource.project.view;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.user.User;
import cource.project.service.CarService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class DeleteCarDialog {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;

    public DeleteCarDialog(CarService carService) {
        this.carService = carService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        Collection<Car> all = carService.getAllCars();
        List<Car> allCars = new ArrayList<>(all);
        boolean continueEditing = true;
        while (continueEditing) {
            if (allCars.size() > 0) {
                System.out.println("Cars you can delete:");
                int count = 0;
                for (Car car : allCars) {
                    count++;
                    System.out.println(count + ". \t" + car);
                }
                System.out.println("Choose a car from the list above to delete.");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(allCars, choice, input);
                Car car = allCars.get(choice - 1);
                boolean canBeEdited = true;
                if (car.getDropOffDates() != null){
                    for (LocalDateTime dropOffDate : car.getDropOffDates()) {
                        if (dropOffDate.isAfter(LocalDateTime.now())) {
                            canBeEdited = false;
                            break;
                        }
                    }
                }
                if (!canBeEdited) {
                    System.out.println("Sorry car can not be deleted because it has orders.");
                } else {
                    choice = confirmEditing(choice, car);
                }
                continueEditing = confirmContinue(true, allCars);
            } else {
                System.out.println("There is no cars in the system.");
                continueEditing = false;
            }
        }
    }

    private boolean confirmContinue(boolean continueEditing, List<Car> allCars) {
        System.out.println();
        System.out.println("For continue deleting cars press 'C'?");
        System.out.println("For cancel press 'E'.");
        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue deleting cars.");
                System.out.println("Remaining cars you can delete: " + allCars.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled continue deleting cars.");
                continueEditing = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueEditing;
    }

    private int confirmEditing(int choice, Car car) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("To confirm deleting car press 'YES'.");
        System.out.println("For exit press 'E'.");
        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("Successfully deleted car:");
                carService.deleteCar(car.getId());
                System.out.println(car);
            } else if (input.equals("E")) {
                System.out.println("You canceled deleting car.");
                choice = 0;
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

    public int checkValidInput(List<Car> cars, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > cars.size()) {
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
