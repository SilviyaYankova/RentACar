package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.enums.Role;
import model.user.User;
import service.UserService;
import view.*;
import view.Menu.Menu;
import view.Menu.Option;

import java.util.Collection;
import java.util.List;

public class ManageSellersController {
    private final UserService userService;

    public ManageSellersController(UserService userService) {
        this.userService = userService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {
        userService.loadData();

        Menu menu = new Menu("Manage sellers", List.of(
                new Option("See all sellers", () -> {
                    userService.loadData();
                    Collection<User> allUsers = userService.getUserByRole(Role.SELLER);
                    if (allUsers.size() > 0) {
                        int count = 0;
                        for (User user : allUsers) {
                            count++;
                            System.out.println(count + ".\t " + user);
                        }
                    } else {
                        System.out.println("There is no sellers in the system.");
                    }
                    System.out.println();
                    return "";
                }),
                new Option("Add Seller", () -> {
                    User user = new RegisterDialog(userService).input(LOGGED_IN_USER);
                    User created = userService.registerUser(user);

                    if (created == null) {
                        return "Username already exist.";
                    }
                    return String.format("User ID:%s: '%s' added successfully.%n",
                            created.getId(), created.getUsername());

                }),
                new Option("Edit seller", () -> {
                    EditUserDialog editUserDialog = new EditUserDialog(userService);
                    editUserDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete seller", () -> {
                    DeleteProfileDialog deleteProfileDialog = new DeleteProfileDialog(userService);
                    deleteProfileDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
