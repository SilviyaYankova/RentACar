package cource.project.view;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegisterDialog {

    public static Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public RegisterDialog(UserService userService) {
        this.userService = userService;
    }


    public User input(User LOGGED_IN_USER) throws InvalidEntityDataException {
        User user = new User();

        while (user.getFirstName() == null) {
            System.out.println("First Name:");
            String firstName = scanner.nextLine();

            if (firstName.length() < 2 || firstName.length() > 50) {
                System.out.println("Error: User first name should be between 2 and 50 characters long.");
            } else {
                user.setFirstName(firstName);
            }
        }

        while (user.getLastName() == null) {
            System.out.println("Last Name:");
            String lastName = scanner.nextLine();

            if (lastName.length() < 2 || lastName.length() > 50) {
                System.out.println("Error: User last name should be between 2 and 50 characters long.");
            } else {
                user.setLastName(lastName);
            }
        }

        while (user.getEmail() == null) {
            System.out.println("Email:");
            String email = scanner.nextLine();
            Pattern pattern = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
            Matcher matcher = pattern.matcher(email);
            User userByEmail = userService.getUserByEmail(email);

            if (!matcher.find()) {
                System.out.println("Error: Email must be valid.");
            } else if (userByEmail != null) {
                System.out.println("Email already exists. Please change email.");
            } else {
                user.setEmail(email);
            }
        }

        while (user.getPhoneNumber() == null) {
            System.out.println("Phone Number:");
            String phoneNumber = scanner.nextLine();

            if (phoneNumber.length() < 8 || phoneNumber.length() > 10) {
                System.out.println("Error: PhoneNumber should be between 8 and 10 characters long.");
            } else {
                user.setPhoneNumber(phoneNumber);
            }
        }

        while (user.getUsername() == null) {
            System.out.println("Username:");
            String username = scanner.nextLine();
            User userByUsername = userService.getUserByUsername(username);

            if (username.length() < 2 || username.length() > 15) {
                System.out.println("Error: Username should be between 2 and 15 characters long.");
            } else if (userByUsername != null) {
                System.out.println("Username already exists. Please change username.");
            } else {
                user.setUsername(username);
            }
        }


        while (user.getPassword() == null) {
            System.out.println("Password:");
            System.out.println("Password length must be more than 2 and less then 15 characters long,");
            System.out.println("contain at least one digit, one capital letter, and one sign different than letter or digit.");
            String password = scanner.nextLine();

            Pattern passwordPattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$");
            Matcher passwordMatcher = passwordPattern.matcher(password);
            boolean correctPassword = passwordMatcher.find();
            if (!correctPassword) {
                System.out.println("Error: Password length must be more than 2 and less then 15 characters long, " +
                        "contain at least one digit, one capital letter, and one sign different than letter or digit.");
            } else {
                user.setPassword(password);
            }
        }

        while (user.getRepeatPassword() == null) {
            System.out.println("Repeat password:");
            String repeatPassword = scanner.nextLine();

            if (!repeatPassword.equals(user.getPassword())) {
                System.out.println("Error: passwords should match.");
            } else {
                user.setRepeatPassword(repeatPassword);
            }
        }
        user.setRegisteredOn(LocalDateTime.now());

        if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR)) {
            System.out.println("Role:");
            List<Role> roles = Arrays.stream(Role.values()).collect(Collectors.toList());
            int count = 0;
            for (Role role : roles) {
                count++;
                System.out.println(count + ".\t" + role);
            }
            int choice = 0;
            System.out.println("Assign role from list above.");
            String str = scanner.nextLine();
            choice = checkValidInput(choice, str);
            Role r = roles.get(choice - 1);
            user.setRole(r);
        }

        return user;
    }

    public int checkValidInput(int choice, String input) {

        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Error: Numbers only.");
                input = scanner.nextLine();
            }

        }
        return choice;
    }
}
