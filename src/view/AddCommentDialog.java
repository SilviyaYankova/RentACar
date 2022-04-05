package view;

import exeption.InvalidEntityDataException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Comment;
import model.user.User;
import service.CarService;
import service.CommentService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AddCommentDialog {
    Scanner scanner = new Scanner(System.in);
    private UserService userService;
    private CarService carService;
    private final CommentService commentService;

    public AddCommentDialog(UserService userService, CarService carService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.commentService = commentService;
    }

    public Comment init(User LOGGED_IN_USER, Car car) throws InvalidEntityDataException, NoneExistingEntityException {
        Comment comment = new Comment();
        int choice = 0;
        while (comment.getContent() == null){
            System.out.println("Add content:");
            String content = scanner.nextLine();

            int length = content.length();
            if (length < 10 || length > 200) {
                System.out.println("Comment length should be between 10 and 200 characters long.");
            } else {
                comment.setContent(content);
            }
        }

        while (comment.getRating() == 0){
            System.out.println("Add rating from 1 to 5:");
            String rating = scanner.nextLine();

            choice = checkValidInput(choice, rating);

            comment.setRating(choice);

        }

        comment.setUser(LOGGED_IN_USER);
        comment.setCar(car);
        comment.setPostedOn(LocalDateTime.now());

        commentService.addCarComment(comment);

        return comment;
    }

    public int checkValidInput(int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 5) {
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
