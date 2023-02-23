package cource.project.Controller;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.enums.Role;
import cource.project.model.user.Driver;
import cource.project.model.user.User;
import cource.project.service.UserService;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManageDriverController {
    private final UserService userService;

    public ManageDriverController(UserService userService) {
        this.userService = userService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {


        Menu menu = new Menu("Manage drivers", List.of(
                new Option("See drivers", () -> {
                    Collection<User> all = userService.getUsersByRole(Role.DRIVER);
                    List<Driver> drivers = new ArrayList<>();
                    for (User user : all) {
                        Driver driver = userService.fromUserToDriver(user);
                        drivers.add(driver);
                    }

                    if (drivers.size() > 0) {
                        int count = 0;
                        for (Driver driver : drivers) {
                            count++;
                            System.out.println(count + ".\t " + driver);
                        }
                    } else {
                        System.out.println("There is no drivers in the system.");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Add drivers", () -> {
                    User user = new RegisterDialog(userService).input(LOGGED_IN_USER);
                    User created = userService.registerUser(user);

                    if (created == null) {
                        return "Username already exist.";
                    }
                    return "";
                }),
                new Option("Edit drivers", () -> {
                    EditDriverDialog editDriverDialog = new EditDriverDialog(userService, LOGGED_IN_USER);
                    editDriverDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete drivers", () -> {
                    DeleteDriverDialog deleteDriverDialog = new DeleteDriverDialog(userService);
                    deleteDriverDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
