package view;

import exeption.NoPermissionException;
import exeption.NoneExistingEntityException;
import model.Comment;
import model.user.User;
import service.CarService;
import service.CommentService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class DeleteCommentDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final CommentService commentService;

    public DeleteCommentDialog(UserService userService, CarService carService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.commentService = commentService;
    }


    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException, NoPermissionException {
        commentService.loadData();

        List<Comment> allComments = LOGGED_IN_USER.getComments();

        boolean continueCommenting = true;
        while (continueCommenting) {
            if (allComments.size() > 0) {
                System.out.println("Comments you can delete");
                int count = 0;
                for (Comment comment : allComments) {
                    count++;
                    System.out.println(count + ". \t" + comment);
                }

                System.out.println("Choose a comment to delete from the list above .");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(allComments, choice, input);

                Comment comment = allComments.get(choice - 1);
//                LOGGED_IN_USER.getComments().remove(comment);

                choice = confirmEditing(LOGGED_IN_USER, choice, comment);


                continueCommenting = confirmContinue(true, allComments);




            } else {
                System.out.println("You have no comments you can delete.");
                break;
            }


        }
    }

    private boolean confirmContinue(boolean continueCommenting, List<Comment> allComments) {
        System.out.println();
        System.out.println("For continue deleting comments press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue deleting comments.");
                System.out.println("Remaining comments you can delete: " + allComments.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled continue deleting comments.");
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
        System.out.println("To confirm deleting comment press 'YES'.");
        System.out.println("For exit press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("Successfully deleted comment:");
                commentService.deleteComment(comment.getId());
                System.out.println(comment);
            } else if (input.equals("E")) {
                System.out.println("You canceled deleting comment.");
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
}
