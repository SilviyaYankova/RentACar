package cource.project.view;

import cource.project.model.user.User;
import cource.project.service.UserService;

import java.util.Scanner;

public class LoginDialog   {
    public static Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public LoginDialog(UserService userService) {
        this.userService = userService;
    }

    public User input() {
        User user = new User();

        while (user.getUsername() == null) {
            System.out.println("Username:");
//            String username = scanner.nextLine();
//            user.setUsername(username);
//            user.setUsername("silviya");
            user.setUsername("martin");
        }

        while (user.getPassword() == null) {
            System.out.println("Password:");
//            String password = scanner.nextLine();
//            user.setPassword(password);
            user.setPassword("0123456789Ja*");
        }
        String username = user.getUsername();
        String password = user.getPassword();

        return userService.getUserByUsernameAndPassword(username, password);
    }
}
