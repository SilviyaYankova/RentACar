package view;

import exeption.NoPermissionException;
import exeption.NoneExistingEntityException;
import model.Comment;
import model.user.User;
import service.CarService;
import service.CommentService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class EditCommentDialog {
    Scanner scanner = new Scanner(System.in);
    private UserService userService;
    private CarService carService;
    private final CommentService commentService;

    public EditCommentDialog(UserService userService, CarService carService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.commentService = commentService;
    }


    public void init(User LOGGED_IN_USER) throws NoPermissionException, NoneExistingEntityException {
        List<Comment> allComments = LOGGED_IN_USER.getComments();

        boolean continueCommenting = true;
        while (continueCommenting) {
            if (allComments.size() > 0) {
                System.out.println("Your comments.");
                int count = 0;
                for (Comment comment : allComments) {
                    count++;
                    System.out.println(count + ". \t" + comment);
                }

                System.out.println("Choose a comment to edit from the list above .");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(allComments, choice, input);

                Comment comment = allComments.get(choice - 1);
                LOGGED_IN_USER.getComments().remove(comment);
                comment.setContent(null);
                while (comment.getContent() == null) {
                    System.out.println("Add new content:");
                    String content = scanner.nextLine();

                    int length = content.length();
                    if (length < 10 || length > 200) {
                        System.out.println("Comment length should be between 10 and 200 characters long.");
                    } else {
                        comment.setContent(content);
                    }
                }

                comment.setRating(0);
                while (comment.getRating() == 0) {
                    System.out.println("Add new rating from 1 to 5:");
                    String rating = scanner.nextLine();
                    choice = 0;
                    choice = checkValidInput(choice, rating);

                    comment.setRating(choice);

                }

                comment.setEditedOn(LocalDateTime.now());

                choice = confirmEditing(LOGGED_IN_USER, choice, comment);

                continueCommenting = confirmContinue(true, allComments);
            } else {
                System.out.println("You have no comments.");
                break;
            }
        }


    }

    private boolean confirmContinue(boolean continueCommenting, List<Comment> allComments) {
        System.out.println();
        System.out.println("For continue editing comments press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue commenting.");
                System.out.println("Remaining comments you can edit: " + allComments.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled continue editing comments.");
                continueCommenting = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueCommenting;
    }

    private int confirmEditing(User LOGGED_IN_USER, int choice, Comment comment) throws NoneExistingEntityException, NoPermissionException {
        System.out.println();
        System.out.println("Save comment or continue editing?");
        System.out.println("For saving comment press 'YES'.");
        System.out.println("For exit press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("Successfully edited comment:");
                commentService.editComment(comment);
                System.out.println(comment);
            } else if (input.equals("E")) {
                System.out.println("You canceled editing comment.");
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

    public int checkValidInput(List<Comment> allComments, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > allComments.size()) {
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
