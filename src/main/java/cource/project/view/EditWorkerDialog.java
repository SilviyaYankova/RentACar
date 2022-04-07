package cource.project.view;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Worker;
import cource.project.model.enums.WorkerStatus;
import cource.project.model.user.User;
import cource.project.service.WorkerService;

import java.util.*;
import java.util.stream.Collectors;

public class EditWorkerDialog {
    Scanner scanner = new Scanner(System.in);
    private final WorkerService workerService;

    public EditWorkerDialog(WorkerService workerService) {
        this.workerService = workerService;
    }


    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {

        Collection<Worker> all = workerService.getAllWorkers();
        List<Worker> allWorkers = new ArrayList<>(all);

        boolean continueCommenting = true;
        while (continueCommenting) {
            if (allWorkers.size() > 0) {
                System.out.println("Workers:");
                int count = 0;
                for (Worker worker : allWorkers) {
                    count++;
                    System.out.println(count + ". \t" + worker);
                }

                System.out.println("Choose a worker to edit from the list above .");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(allWorkers, choice, input);

                Worker worker = allWorkers.get(choice - 1);

                worker.setFirstName(null);
                while (worker.getFirstName() == null) {
                    System.out.println("First Name:");
                    String firstName = scanner.nextLine();

                    if (firstName.length() < 2 || firstName.length() > 50) {
                        System.out.println("Error: Worker first name should be between 2 and 50 characters long.");
                    } else {
                        worker.setFirstName(firstName);
                    }
                }

                worker.setLastName(null);
                while (worker.getLastName() == null) {
                    System.out.println("Last Name:");
                    String lastName = scanner.nextLine();

                    if (lastName.length() < 2 || lastName.length() > 50) {
                        System.out.println("Error: Worker last name should be between 2 and 50 characters long.");
                    } else {
                        worker.setLastName(lastName);
                    }
                }

                worker.setWorkerStatus(null);
                while (worker.getWorkerStatus() == null) {
                    System.out.println("Worker Status:");
                    List<WorkerStatus> values = Arrays.stream(WorkerStatus.values()).collect(Collectors.toList());
                    count = 0;
                    for (WorkerStatus value : values) {
                        count++;
                        System.out.println(count + "\t" + value);
                    }

                    System.out.println("Choose Status from the list above.");
                    input = scanner.nextLine();
                    choice = 0;
                    choice = checkValidInput(values, choice, input);
                    WorkerStatus workerStatus = values.get(choice - 1);
                    worker.setWorkerStatus(workerStatus);

                }

                choice = confirmEditing(LOGGED_IN_USER, choice, worker);

                continueCommenting = confirmContinue(true, allWorkers);
            } else {
                System.out.println("You have no comments.");
                break;
            }
        }


    }

    private boolean confirmContinue(boolean continueCommenting, List<Worker> allWorkers) {
        System.out.println();
        System.out.println("For continue editing workers press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue editing workers.");
                System.out.println("Remaining workers you can edit: " + allWorkers.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled continue editing workers.");
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
        System.out.println("For saving worker press 'YES'.");
        System.out.println("For exit press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("Successfully edited worker:");
               workerService.editWorker(worker);
                System.out.println(worker);
            } else if (input.equals("E")) {
                System.out.println("You canceled editing worker.");
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

    public <T> int checkValidInput(List<T> allWorkers, int choice, String input) {
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

    public int checkValidInput(int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 5) {
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
