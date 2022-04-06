package cource.project.Controller;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Comment;
import cource.project.model.enums.Role;
import cource.project.model.user.User;
import cource.project.service.CarService;
import cource.project.service.CommentService;
import cource.project.service.UserService;
import cource.project.view.*;
import cource.project.view.Menu.Menu;
import cource.project.view.Menu.Option;

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

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoneExistingEntityException {


        if (LOGGED_IN_USER.getRole().equals(Role.USER)){
            Menu menu = new Menu("Comment Menu", List.of(
                    new Option("Add comment", () -> {
                        AddCommentDialog addCommentDialog = new AddCommentDialog(userService, carService, commentService);
                        addCommentDialog.input(LOGGED_IN_USER);
                        return "";
                    }),
                    new Option("Edit comment", () -> {

                        EditCommentDialog editCommentDialog = new EditCommentDialog(userService, carService, commentService);
                        editCommentDialog.input(LOGGED_IN_USER);
                        return "";
                    }),
                    new Option("Delete comment", () -> {

                        DeleteCommentDialog deleteCommentDialog = new DeleteCommentDialog(userService, carService, commentService);
                        deleteCommentDialog.input(LOGGED_IN_USER);
                        return "";
                    }),
                    new Option("Comment history", () -> {

                        List<Comment> allComments = LOGGED_IN_USER.getComments();

                        if (allComments.size() > 0) {
                            int count = 0;
                            for (Comment comment : allComments) {
                                count++;
                                System.out.println(count + ". \t" + comment);
                            }
                        } else {
                            System.out.println("You have no comments.");
                        }
                        return "";
                    })

            ));
            menu.show();
        }

        if (LOGGED_IN_USER.getRole().equals(Role.ADMINISTRATOR)){
            Menu menu = new Menu("Comment Menu", List.of(
                    new Option("All comments", () -> {

                        Collection<Comment> allComments = commentService.getAllComments();
                        if (allComments.size() > 0) {
                            System.out.println("All comments:");
                            int count = 0;
                            for (Comment comment : allComments) {
                                count++;
                                System.out.println(count + ". \t" + comment);
                            }
                        } else {
                            System.out.println("You have no comments.");
                        }
                        System.out.println();
                        return "";
                    }),
                    new Option("Delete comment", () -> {

                        DeleteCommentDialog deleteCommentDialog = new DeleteCommentDialog(userService, carService, commentService);
                        deleteCommentDialog.input(LOGGED_IN_USER);
                        return "";
                    })


            ));
            menu.show();
        }

    }

}
