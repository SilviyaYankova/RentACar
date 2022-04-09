package cource.project.Controller;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Order;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.UserService;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

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
                    Collection<User> allUsers = userService.getUserByRole(Role.DRIVER);
                    if (allUsers.size() > 0) {
                        int count = 0;
                        for (User user : allUsers) {
                            count++;
                            System.out.println(count + ".\t " + user);
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
                    EditUserDialog editUserDialog = new EditUserDialog(userService);
                    editUserDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete drivers", () -> {
                    DeleteProfileDialog deleteProfileDialog = new DeleteProfileDialog(userService);
                    deleteProfileDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
