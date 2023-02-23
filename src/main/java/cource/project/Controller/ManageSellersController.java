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

public class ManageSellersController {
    private final UserService userService;

    public ManageSellersController(UserService userService) {
        this.userService = userService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {


        Menu menu = new Menu("Manage sellers", List.of(
                new Option("See all sellers", () -> {

                    Collection<User> allUsers = userService.getUsersByRole(Role.SELLER);
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
                    EditSellerDialog editSellerDialog2 = new EditSellerDialog(userService, LOGGED_IN_USER);
                    editSellerDialog2.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Delete seller", () -> {
                    DeleteUserDialog deleteUserDialog = new DeleteUserDialog(userService);
                    deleteUserDialog.input(LOGGED_IN_USER);
                    return "";
                })
        ));

        menu.show();
    }
}
