package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.user.User;
import service.UserService;
import view.*;

import java.util.Collection;
import java.util.List;

public class ManageUsersController {
    private final UserService userService;

    public ManageUsersController(UserService userService) {
        this.userService = userService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        userService.loadData();

        Menu menu = new Menu("Manage users", List.of(
                new Option("See all users", () -> {
                    userService.loadData();
                    Collection<User> allUsers = userService.getAllUsers();
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
                    User user = new RegisterDialog(userService).input();
                    User created = userService.registerUser(user);
                    if (created == null) {
                        return "Username already exist.";
                    }
                    return String.format("User ID:%s: '%s' added successfully.%n",
                            created.getId(), created.getUsername());

                }),
                new Option("Edit user", () -> {
                    EditUserDialog editUserDialog = new EditUserDialog(userService);
                    editUserDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete user", () -> {
                    DeleteProfileDialog deleteProfileDialog = new DeleteProfileDialog(userService);
                    deleteProfileDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
