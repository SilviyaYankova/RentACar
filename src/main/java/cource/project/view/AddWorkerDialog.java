package cource.project.view;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Worker;
import cource.project.model.enums.WorkerStatus;
import cource.project.model.user.SiteManager;
import cource.project.model.user.User;
import cource.project.service.UserService;
import cource.project.service.WorkerService;

import java.util.ArrayList;
import java.util.Scanner;

public class AddWorkerDialog {
        Scanner scanner = new Scanner(System.in);
        private final WorkerService workerService;
        private final UserService userService;

    public AddWorkerDialog(WorkerService workerService, UserService userService) {
        this.workerService = workerService;
        this.userService = userService;
    }


    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        Worker worker = new Worker();
        while (worker.getFirstName() == null) {
            System.out.println("First Name:");
            String firstName = scanner.nextLine();

            if (firstName.length() < 2 || firstName.length() > 50) {
                System.out.println("Error: Worker first name should be between 2 and 50 characters long.");
            } else {
                worker.setFirstName(firstName);
            }
        }

        while (worker.getLastName() == null) {
            System.out.println("Last Name:");
            String lastName = scanner.nextLine();

            if (lastName.length() < 2 || lastName.length() > 50) {
                System.out.println("Error: Worker last name should be between 2 and 50 characters long.");
            } else {
                worker.setLastName(lastName);
            }
        }

        while (worker.getCode() == null){
            System.out.println("Code:");
            String code = scanner.nextLine();
            if (code.length() < 2) {
                System.out.println("Worker Code should be at least 2 characters long.");
            } else {
                worker.setCode(code);
            }
        }
        SiteManager siteManager = (SiteManager) LOGGED_IN_USER;
        siteManager.getWorkers().add(worker);
        userService.editUser(siteManager);
        worker.setCarHistory(new ArrayList<>());
        worker.setWorkerStatus(WorkerStatus.AVAILABLE);
        workerService.addWorker(worker);
    }
}
