package cource.project.view;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Comment;
import cource.project.model.Order;
import cource.project.model.user.User;
import cource.project.service.CarService;
import cource.project.service.CommentService;
import cource.project.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddCommentDialog {
    Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CarService carService;
    private final CommentService commentService;

    public AddCommentDialog(UserService userService, CarService carService, CommentService commentService) {
        this.userService = userService;
        this.carService = carService;
        this.commentService = commentService;
    }

    public void input(User LOGGED_IN_USER) throws InvalidEntityDataException, NoneExistingEntityException {
        List<Car> cars = new ArrayList<>();
        List<Order> orders = LOGGED_IN_USER.getOrders();
        for (Order order : orders) {
            cars.add(order.getCar());
        }

        boolean continueCommenting = true;
        while (continueCommenting) {
            if (cars.size() > 0) {
                System.out.println("Your orders.");

                int count = 0;
                for (Car car : cars) {
                    count++;
                    System.out.println(count + ". \t" + car);
                }

                System.out.println("Choose a car from the list above to add a comment .");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(cars, choice, input);
                Car car = cars.get(choice - 1);

                Comment comment = new Comment();
                while (comment.getContent() == null) {
                    System.out.println("Add content:");
                    String content = scanner.nextLine();

                    int length = content.length();
                    if (length < 10 || length > 200) {
                        System.out.println("Comment length should be between 10 and 200 characters long.");
                    } else {
                        comment.setContent(content);
                    }
                }

                while (comment.getRating() == 0) {
                    System.out.println("Add rating from 1 to 5:");
                    String rating = scanner.nextLine();
                    choice = 0;
                    choice = checkValidInput(choice, rating);

                    comment.setRating(choice);

                }

                comment.setUser(LOGGED_IN_USER);
                comment.setCar(car);
                comment.setPostedOn(LocalDateTime.now());

                commentService.addCarComment(comment);

                System.out.println("Successfully added comment:");
                System.out.println(comment);

                continueCommenting = confirmContinue(true, cars);

            } else {
                System.out.println("You have not booked any cars and you can not add a comment.");
                continueCommenting = false;
            }


        }
    }

    private boolean confirmContinue(boolean continueCommenting, List<Car> cars) {
        System.out.println();
        System.out.println("For continue commenting press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue commenting.");
                System.out.println("Remaining cars you can comment on: " + cars.size());
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled continue commenting.");
                continueCommenting = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'C' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueCommenting;
    }

    public int checkValidInput(List<Car> cars, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > cars.size()) {
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
