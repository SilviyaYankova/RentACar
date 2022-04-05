package view;

import exeption.InvalidEntityDataException;
import model.user.User;
import service.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterDialog implements EntityDialog<User> {

    public static Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public RegisterDialog(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User input() throws InvalidEntityDataException {
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
        return user;
    }
}
