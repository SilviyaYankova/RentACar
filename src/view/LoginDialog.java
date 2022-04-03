package view;

import model.user.User;
import service.UserService;

import java.util.Scanner;

public class LoginDialog implements EntityDialog<User> {
    public static Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public LoginDialog(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User input() {
        User user = new User();

        while (user.getUsername() == null) {
            System.out.println("Username:");
            String username = scanner.nextLine();
            user.setUsername(username);
        }

        while (user.getPassword() == null) {
            System.out.println("Password:");
            String password = scanner.nextLine();
            user.setPassword(password);
        }
        String username = user.getUsername();
        String password = user.getPassword();
        User userByUsernameAndPassword = userService.getUserByUsernameAndPassword(username, password);
        return userByUsernameAndPassword;
    }
}
