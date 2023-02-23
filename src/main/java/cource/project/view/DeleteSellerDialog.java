package cource.project.view;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class DeleteSellerDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public DeleteSellerDialog(UserService userService) {
        this.userService = userService;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {


        System.out.println("Sellers you can delete:");
        Collection<User> all = userService.getUsersByRole(Role.SELLER);
        if (all.size() > 0) {
            int count = 0;
            for (User user : all) {
                count++;
                System.out.println(count + ".\t " + user);
            }

            System.out.println("Choose a seller to delete from the list above.");
            String input = scanner.nextLine();
            int choice = 0;
            List<User> allUsers = new ArrayList<>(all);
            choice = checkValidInput(allUsers, choice, input);

            User userToDelete = allUsers.get(choice - 1);

            confirmEditing(userToDelete, choice);
        }
    }

    private int confirmEditing(User userToDelete, int choice) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("To confirm deleting user press 'YES'.");
        System.out.println("For exit press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("Successfully deleted user:");
                userService.deleteUser(userToDelete.getId());
                System.out.println(userToDelete);
            } else if (input.equals("E")) {
                System.out.println("You canceled deleting user.");
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


    public <T> int checkValidInput(List<T> t, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > t.size()) {
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
