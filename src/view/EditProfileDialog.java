package view;

import exeption.NoPermissionException;
import exeption.NoneExistingEntityException;
import model.enums.Role;
import model.user.User;
import service.UserService;

import java.util.Scanner;

public class EditProfileDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public EditProfileDialog(UserService userService) {
        this.userService = userService;
    }


    public void init(User LOGGED_IN_USER) throws NoPermissionException, NoneExistingEntityException {
        userService.loadData();
        System.out.println("Your profile:");
        System.out.println(LOGGED_IN_USER);
        System.out.println();

            System.out.println("Choose fields to edit: ");
            System.out.println("1. First name");
            System.out.println("2. Last name");
            System.out.println("3. Phone Number");
            System.out.println("4. Password");

            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            boolean incorrectInput = true;

            while (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                if (choice == 1) {
                    LOGGED_IN_USER.setFirstName(null);
                    while (LOGGED_IN_USER.getFirstName() == null) {
                        System.out.println("Please enter first name.");
                        input = scanner.nextLine();
                        LOGGED_IN_USER.setFirstName(input);
                    }

                    choice = confirmEditing(LOGGED_IN_USER, choice);
                }
                if (choice == 2) {
                    LOGGED_IN_USER.setLastName(null);
                    while (LOGGED_IN_USER.getLastName() == null) {
                        System.out.println("Please enter last name.");
                        input = scanner.nextLine();
                        LOGGED_IN_USER.setLastName(input);
                    }

                    choice = confirmEditing(LOGGED_IN_USER, choice);
                }
                if (choice == 3) {
                    LOGGED_IN_USER.setPhoneNumber(null);
                    while (LOGGED_IN_USER.getPhoneNumber() == null) {
                        System.out.println("Please enter phone number.");
                        input = scanner.nextLine();
                        LOGGED_IN_USER.setPhoneNumber(input);
                    }

                    choice = confirmEditing(LOGGED_IN_USER, choice);
                }
                if (choice == 4) {
                    LOGGED_IN_USER.setPassword(null);
                    LOGGED_IN_USER.setRepeatPassword(null);
                    while (LOGGED_IN_USER.getPassword() == null && LOGGED_IN_USER.getRepeatPassword() == null) {
                        System.out.println("Please enter new password.");
                        System.out.println("Password length must be more than 2 and less then 15 characters long,");
                        System.out.println("contain at least one digit, one capital letter, and one sign different than letter or digit.");
                        input = scanner.nextLine();
                        LOGGED_IN_USER.setPassword(input);
                        System.out.println("Please repeat password.");
                        input = scanner.nextLine();
                        LOGGED_IN_USER.setRepeatPassword(input);
                        if (!LOGGED_IN_USER.getPassword().equals(LOGGED_IN_USER.getRepeatPassword())) {
                            System.out.println("Passwords does not match. Please try again.");
                            LOGGED_IN_USER.setPassword(null);
                            LOGGED_IN_USER.setRepeatPassword(null);
                        }
                    }

                    choice = confirmEditing(LOGGED_IN_USER, choice);
                }
            }

    }

    public int checkValidInput(int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 6) {
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

    private int confirmEditing(User LOGGED_IN_USER, int choice) throws NoneExistingEntityException, NoPermissionException {
        System.out.println();
        System.out.println("Save field change or continue editing?");
        System.out.println("For saving profile press 'YES' for continue editing press 'C'?");
        System.out.println("For exit press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("You finished editing your profile.");
                userService.editUser(LOGGED_IN_USER);
            } else if (input.equals("C")) {
                System.out.println("You choose to continue editing your profile.");
                System.out.println("Choose fields to edit: ");
                System.out.println("1. First name");
                System.out.println("2. Last name");
                System.out.println("3. Phone Number");
                System.out.println("4. Password");
                input = scanner.nextLine();
                choice = 0;
                choice = checkValidInput(choice, input);
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled editing your profile.");
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
}
