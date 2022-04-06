package cource.project.Controller;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Worker;
import cource.project.model.user.User;
import cource.project.service.UserService;
import cource.project.service.WorkerService;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

import java.util.Collection;
import java.util.List;

public class ManageWorkerController {
    private final WorkerService workerService;
    private final UserService userService;

    public ManageWorkerController(WorkerService workerService, UserService userService) {
        this.workerService = workerService;
        this.userService = userService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {


        Menu menu = new Menu("Manage workers", List.of(
                new Option("See workers", () -> {
                    Collection<Worker> allWorkers = workerService.getAllWorkers();
                    if (allWorkers.size() > 0) {
                        int count = 0;
                        for (Worker worker : allWorkers) {
                            count++;
                            System.out.println(count + ".\t " + worker);
                        }
                    } else {
                        System.out.println("There is no workers in the system.");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Add worker", () -> {
                    AddWorkerDialog addWorkerDialog = new AddWorkerDialog(workerService, userService);
                    addWorkerDialog.input(LOGGED_IN_USER);
                    return "";

                }),
                new Option("Edit worker", () -> {
                    EditWorkerDialog editWorkerDialog = new EditWorkerDialog(workerService);
                    editWorkerDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete worker", () -> {
                    DeleteWorkerDialog deleteWorkerDialog = new DeleteWorkerDialog(userService, workerService);
                    deleteWorkerDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
