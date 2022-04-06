package cource.project.Controller;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.UserService;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

import java.util.Collection;
import java.util.List;

public class ManageSiteManagerController {
    private final UserService userService;

    public ManageSiteManagerController(UserService userService) {
        this.userService = userService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {


        Menu menu = new Menu("Manage Site Manager", List.of(
                new Option("See Site Manager", () -> {

                    Collection<User> allUsers = userService.getUserByRole(Role.SITE_MANAGER);
                    if (allUsers.size() > 0) {
                        int count = 0;
                        for (User user : allUsers) {
                            count++;
                            System.out.println(count + ".\t " + user);
                        }
                    } else {
                        System.out.println("There is no site manager in the system.");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Add Site Manager", () -> {
                    User user = new RegisterDialog(userService).input(LOGGED_IN_USER);
                    User created = userService.registerUser(user);

                    if (created == null) {
                        return "Username already exist.";
                    }
                    return String.format("User ID:%s: '%s' added successfully.%n",
                            created.getId(), created.getUsername());

                }),
                new Option("Edit Site Manager", () -> {
                    EditUserDialog editUserDialog = new EditUserDialog(userService);
                    editUserDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete Site Manager", () -> {
                    DeleteProfileDialog deleteProfileDialog = new DeleteProfileDialog(userService);
                    deleteProfileDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
