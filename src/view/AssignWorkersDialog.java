package view;

import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Worker;
import model.enums.CarStatus;
import model.enums.WorkerStatus;
import model.user.SiteManager;
import model.user.User;
import service.CarService;
import service.UserService;
import service.WorkerService;

import java.util.List;
import java.util.Scanner;

public class AssignWorkersDialog {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;
    private final WorkerService workerService;
    private final UserService userService;

    public AssignWorkersDialog(CarService carService, WorkerService workerService, UserService userService) {
        this.carService = carService;
        this.workerService = workerService;
        this.userService = userService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        carService.loadData();
        workerService.loadData();
        List<Car> carsForCleaning = carService.getAllCarsWithStatus(CarStatus.WAITING_FOR_CLEANING);

        boolean continueCleaning = true;
        while (continueCleaning) {
            if (carsForCleaning.size() > 0) {
             int   count = 0;
                for (Car car : carsForCleaning) {
                    count++;
                    System.out.println(count + ". \t" + car);
                }

                System.out.println("Choose car for cleaning from the list above.");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(carsForCleaning, choice, input);
                Car car = carsForCleaning.get(choice - 1);
                try {
                    Worker worker = workerService.getAllAvailableWorker();
                    car.setCarStatus(CarStatus.CLEANING);
                    car.setWorker(worker);

                    worker.setWorkerStatus(WorkerStatus.BUSY);
                    worker.setCurrentCar(car);
                    worker.getCarHistory().add(car);

                    workerService.editWorker(worker);
                    carService.editCar(car);

                    SiteManager siteManager = (SiteManager) LOGGED_IN_USER;

                    siteManager.getWorkers().add(worker);
                    siteManager.getCarsHistory().add(car);
                    LOGGED_IN_USER = siteManager;
                    userService.editUser(LOGGED_IN_USER);

                    carsForCleaning.remove(car);

                    System.out.println("Worker '" + worker.getCode() + "' is assigned to clean car with ID= " + car.getId());

                    continueCleaning = confirmEditing(true, carsForCleaning);
                } catch (NoneAvailableEntityException | NoPermissionException e) {
                    System.out.println("Sorry there is no available worker.");
                    System.out.println();
                    break;
                }
            } else {
                System.out.println("All cars are appointed to workers to be cleaned.");
                continueCleaning = false;
            }
        }
    }

    private boolean confirmEditing(boolean continueCleaning, List<Car> carsForCleaning) {
        System.out.println();
        System.out.println("For continue assigning workers press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue assigning workers to clean cars.");
                System.out.println("Remaining cars for cleaning: " + carsForCleaning.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled assigning workers.");
                continueCleaning = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'C' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueCleaning;
    }


    public int checkValidInput(List<Car> carsForCleaning, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > carsForCleaning.size()) {
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
