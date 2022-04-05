package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Comment;
import model.user.User;
import service.CarService;
import service.CommentService;
import service.UserService;
import view.*;

import java.util.Collection;
import java.util.List;

public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final CarService carService;

    public CommentController(CommentService commentService, UserService userService, CarService carService) {
        this.commentService = commentService;
        this.userService = userService;
        this.carService = carService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
//        commentService.loadData();

        Menu menu = new Menu("Comment Menu", List.of(
                new Option("Add comment", () -> {
                    CommentDialog addCommentDialog = new CommentDialog(userService, carService, commentService);
                    addCommentDialog.input(LOGGED_IN_USER);
                    return "";
                }),
                new Option("Edit comment", () -> {

                    return "";
                }),
                new Option("Delete comment", () -> {

                    return "";
                }),
                new Option("Comment history", () -> {
                    Collection<Comment> allComments = commentService.getAllComments();
                    if (allComments.size() > 0) {
                        int count = 0;
                        for (Comment comment : allComments) {
                            count++;
                            System.out.println(count + " \t" + comment);
                        }
                    } else {
                        System.out.println("You have no comments.");
                    }
                    return "";
                })

        ));
        menu.show();
    }

}
