package cource.project.view;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Worker;
import cource.project.model.user.SiteManager;
import cource.project.model.user.User;
import cource.project.service.UserService;
import cource.project.service.WorkerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class DeleteWorkerDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final WorkerService workerService;

    public DeleteWorkerDialog(UserService userService, WorkerService workerService) {
        this.userService = userService;
        this.workerService = workerService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        Collection<Worker> all = workerService.getAllWorkers();
        List<Worker> allWorkers = new ArrayList<>(all);

        boolean continueCommenting = true;
        while (continueCommenting) {
            if (allWorkers.size() > 0) {
                System.out.println("Workers you can delete");
                int count = 0;
                for (Worker worker : allWorkers) {
                    count++;
                    System.out.println(count + ". \t" + worker);
                }

                System.out.println("Choose a worker to delete from the list above .");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(allWorkers, choice, input);

                Worker worker = allWorkers.get(choice - 1);

                choice = confirmEditing(LOGGED_IN_USER, choice, worker);

                continueCommenting = confirmContinue(true, allWorkers);
            } else {
                System.out.println("You have no comments you can delete.");
                break;
            }
        }
    }

    private boolean confirmContinue(boolean continueCommenting, List<Worker> allWorkers) {
        System.out.println();
        System.out.println("For continue deleting workers press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue deleting workers.");
                System.out.println("Remaining workers you can delete: " + allWorkers.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled continue deleting workers.");
                continueCommenting = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueCommenting;
    }

    private int confirmEditing(User LOGGED_IN_USER, int choice, Worker worker) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("To confirm deleting worker press 'YES'.");
        System.out.println("For exit press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("Successfully deleted worker:");
                workerService.deleteWorker(worker.getId());
                SiteManager siteManager = (SiteManager) LOGGED_IN_USER;
                siteManager.getWorkers().remove(worker);
                userService.editUser(siteManager);
                System.out.println(worker);
            } else if (input.equals("E")) {
                System.out.println("You canceled deleting worker.");
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

    public int checkValidInput(List<Worker> allWorkers, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > allWorkers.size()) {
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
