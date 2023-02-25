package cource.project.view.Menu;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class EditSiteManagerDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    User LOGGED_IN_USER;

    public EditSiteManagerDialog(UserService userService, User LOGGED_IN_USER) {
        this.userService = userService;
        this.LOGGED_IN_USER = LOGGED_IN_USER;
    }

    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {
        System.out.println("Site managers you can edit:");
        Collection<User> all = userService.getUsersByRole(Role.SITE_MANAGER);
        if (all.size() > 0) {
            int count = 0;
            for (User user : all) {
                count++;
                System.out.println(count + ".\t " + user);
            }
            System.out.println("Choose a site manager to edit from the list above .");
            String input = scanner.nextLine();
            int choice = 0;
            List<User> allSellers = new ArrayList<>(all);
            choice = checkValidInput(allSellers, choice, input);
            User sellerToEdit = allSellers.get(choice - 1);
            System.out.println("Choose fields to edit: ");
            System.out.println("1. First name");
            System.out.println("2. Last name");
            System.out.println("3. Role");
            input = scanner.nextLine();
            choice = 0;
            choice = checkValidInput(choice, input);
            while (choice == 1 || choice == 2 || choice == 3) {
                if (choice == 1) {
                    sellerToEdit.setFirstName(null);
                    while (sellerToEdit.getFirstName() == null) {
                        System.out.println("Please enter first name.");
                        input = scanner.nextLine();
                        sellerToEdit.setFirstName(input);
                    }
                    choice = confirmEditing(sellerToEdit, choice);
                }
                if (choice == 2) {
                    sellerToEdit.setLastName(null);
                    while (sellerToEdit.getLastName() == null) {
                        System.out.println("Please enter last name.");
                        input = scanner.nextLine();
                        sellerToEdit.setLastName(input);
                    }
                    choice = confirmEditing(sellerToEdit, choice);
                }

                if (choice == 3) {
                    sellerToEdit.setRole(null);
                    while (sellerToEdit.getRole() == null) {
                        Role[] rolesArr = Role.values();
                        count = 0;
                        for (Role r : rolesArr) {
                            count++;
                            System.out.println(count + ".\t " + r);
                        }
                        System.out.println("Please choose role from the list above.");
                        List<Role> roles = List.of(rolesArr);
                        input = scanner.nextLine();
                        choice = 0;
                        choice = checkValidInput(roles, choice, input);

                        Role role = roles.get(choice - 1);
                        sellerToEdit.setRole(role);

                        choice = confirmEditing(sellerToEdit, choice);
                    }
                }
            }
        } else {
            System.out.println("There is no users in the system.");
        }
        System.out.println();
    }

    public int checkValidInput(int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 4) {
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

    private int confirmEditing(User userToEdit, int choice) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("Save field change or continue editing?");
        System.out.println("For saving profile press 'YES' for continue editing press 'C'?");
        System.out.println("For exit press 'E'.");
        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("You finished editing user profile.");
                userService.editUser(userToEdit);
            } else if (input.equals("C")) {
                System.out.println("You choose to continue editing user profile.");

                if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR)) {
                    System.out.println("1. First name");
                    System.out.println("2. Last name");
                    System.out.println("3. Role");
                } else {
                    System.out.println("Choose fields to edit: ");
                    System.out.println("1. First name");
                    System.out.println("2. Last name");
                    System.out.println("3. Phone Number");
                    System.out.println("4. Password");
                }
                input = scanner.nextLine();
                choice = 0;
                choice = checkValidInput(choice, input);
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled editing user profile.");
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
