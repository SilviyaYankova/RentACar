package cource.project.Controller;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.user.User;
import cource.project.service.UserService;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManageUsersController {
    private final UserService userService;

    public ManageUsersController(UserService userService) {
        this.userService = userService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {


        Menu menu = new Menu("Manage Users", List.of(
                new Option("See all users", () -> {

                    Collection<User> all = userService.getAllUsers();
                    List<User> allUsers = new ArrayList<>(all);
                    if (allUsers.size() > 0) {
                        int count = 0;
                        for (User user : allUsers) {
                            count++;
                            System.out.println(count + ".\t " + user);
                        }
                    } else {
                        System.out.println("There is no users in the system.");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Add user", () -> {
                    User user = new RegisterDialog(userService).input(LOGGED_IN_USER);
                    User created = userService.registerUser(user);
                    if (created == null) {
                        return "Username already exist.";
                    }
                    return "";

                }),
                new Option("Edit user", () -> {
                    EditUserDialog editUserDialog = new EditUserDialog(userService, LOGGED_IN_USER);
                    editUserDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete user", () -> {
                    DeleteUserDialog deleteUserDialog = new DeleteUserDialog(userService);
                    deleteUserDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
